package com.coveracademy.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.api.service.VideoService;
import com.coveracademy.app.R;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.VideoView;
import com.coveracademy.app.activity.CommentsActivity;
import com.coveracademy.app.activity.ContestActivity;
import com.coveracademy.app.activity.UserActivity;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideosAdapter extends BaseAdapter<VideoView, VideosAdapter.VideoViewHolder> {

  private static String TAG = VideosAdapter.class.getSimpleName();

  public VideosAdapter(Context context) {
    super(context, new ArrayList<VideoView>());
  }

  @Override
  public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_video, parent, false);
    return new VideoViewHolder(view);
  }

  @Override
  public void onBindViewHolder(VideoViewHolder holder, int position) {
    VideoView videoView = getItem(position);
    User user = videoView.getVideo().getUser();
    Video video = videoView.getVideo();
    Contest contest = videoView.getVideo().getContest();

    MediaUtils.setPicture(getContext(), user, holder.userPictureView);
    MediaUtils.setThumbnail(getContext(), video, holder.thumbnailView);

    holder.thumbnailView.setVisibility(View.VISIBLE);
    holder.playIconView.setVisibility(View.VISIBLE);
    holder.streamView.setVisibility(View.GONE);

    holder.userNameView.setText(user.getName());
    holder.dateView.setText(DateUtils.getRelativeTimeSpanString(video.getRegistrationDate().getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
    holder.totalLikesView.setText(getContext().getString(R.string.total_likes, videoView.getTotalLikes()));
    holder.totalCommentsView.setText(getContext().getString(R.string.total_comments, videoView.getTotalComments()));

    if(contest != null) {
      holder.contestNameView.setText(contest.getName());
      holder.contestView.setVisibility(View.VISIBLE);
    } else {
      holder.contestView.setVisibility(View.GONE);
    }

    if(!videoView.isLiked()) {
      holder.likeView.setImageResource(R.drawable.no_like);
    } else {
      holder.likeView.setImageResource(R.drawable.like);
    }
  }

  class VideoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.root) View rootView;
    @BindView(R.id.user_picture) ImageView userPictureView;
    @BindView(R.id.user_name) TextView userNameView;
    @BindView(R.id.date) TextView dateView;
    @BindView(R.id.thumbnail) ImageView thumbnailView;
    @BindView(R.id.play_icon) ImageView playIconView;
    @BindView(R.id.stream) android.widget.VideoView streamView;
    @BindView(R.id.contest) View contestView;
    @BindView(R.id.contest_name) TextView contestNameView;
    @BindView(R.id.like) ImageView likeView;
    @BindView(R.id.total_likes) TextView totalLikesView;
    @BindView(R.id.total_comments) TextView totalCommentsView;

    private VideoViewHolder instance;
    private MediaPlayer mediaPlayer;

    VideoViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      instance = this;
    }

    @OnClick(R.id.user)
    void onUserClick() {
      User user = getItem(getAdapterPosition()).getVideo().getUser();
      Intent intent = new Intent(getContext(), UserActivity.class);
      intent.putExtra(UserActivity.USER_ID, user.getId());
      getContext().startActivity(intent);
    }

    @OnClick(R.id.contest_name)
    void onContestClick() {
      Contest contest = getItem(getAdapterPosition()).getVideo().getContest();
      Intent intent = new Intent(getContext(), ContestActivity.class);
      intent.putExtra(ContestActivity.CONTEST_ID, contest.getId());
      getContext().startActivity(intent);
    }

    @OnClick({R.id.comment, R.id.status})
    void onCommentOrStatusClick() {
      Video video = getItem(getAdapterPosition()).getVideo();
      Intent intent = new Intent(getContext(), CommentsActivity.class);
      intent.putExtra(CommentsActivity.VIDEO_ID, video.getId());
      getContext().startActivity(intent);
    }

    @OnClick(R.id.like)
    void onLikeClick() {
      final VideoView videoView = getItem(getAdapterPosition());
      Video video = videoView.getVideo();
      VideoService videoService = RemoteService.getInstance(getContext()).getVideoService();
      DefaultPromise<Void> promise = !videoView.isLiked() ? videoService.like(video) : videoService.dislike(video);
      promise.then(new DoneCallback<Void>() {
        @Override
        public void onDone(Void result) {
          videoView.setTotalLikes(videoView.getTotalLikes() + (!videoView.isLiked() ? 1 : -1));
          videoView.setLiked(!videoView.isLiked());
          reloadItem(getAdapterPosition());
        }
      }).fail(new FailCallback<APIException>() {
        @Override
        public void onFail(APIException e) {
          Log.e(TAG, "Error liking video", e);
          UIUtils.alert(rootView, e, getContext().getString(R.string.alert_error_liking_video));
        }
      });
    }

    @OnClick(R.id.video)
    void onVideoClick() {
      if(thumbnailView.getVisibility() == View.VISIBLE) {
        streamView.getLayoutParams().width = thumbnailView.getWidth();
        streamView.getLayoutParams().height = thumbnailView.getHeight();

        thumbnailView.setVisibility(View.GONE);
        playIconView.setVisibility(View.GONE);
        streamView.setVisibility(View.VISIBLE);

        VideoView videoView = getItem(getAdapterPosition());
        Video video = videoView.getVideo();

        streamView.setVideoURI(Uri.parse(MediaUtils.getVideoUrl(video)));
        streamView.start();
        streamView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          @Override
          public void onPrepared(MediaPlayer mediaPlayer) {
            instance.mediaPlayer = mediaPlayer;
          }
        });
      } else if(mediaPlayer != null) {
        if(mediaPlayer.isPlaying()) {
          mediaPlayer.pause();
        } else {
          mediaPlayer.start();
        }
      } else {
        thumbnailView.setVisibility(View.VISIBLE);
        playIconView.setVisibility(View.VISIBLE);
        streamView.setVisibility(View.GONE);
      }
    }
  }
}