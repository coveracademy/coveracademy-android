package com.coveracademy.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.api.enumeration.Progress;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.promise.Promise;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.api.service.UserService;
import com.coveracademy.api.service.VideoService;
import com.coveracademy.app.R;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.VideoView;
import com.coveracademy.app.activity.CommentsActivity;
import com.coveracademy.app.activity.ContestActivity;
import com.coveracademy.app.activity.UserActivity;
import com.coveracademy.app.util.ApplicationUtils;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.UIUtils;
import com.rey.material.widget.ProgressView;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideosAdapter extends BaseAdapter<VideoView, VideosAdapter.VideoViewHolder> {

  private static String TAG = VideosAdapter.class.getSimpleName();

  private boolean showFanIcon = true;

  public VideosAdapter(Context context) {
    super(context, new ArrayList<VideoView>());
  }

  public void showFanIcon(boolean showFanIcon) {
    this.showFanIcon = showFanIcon;
  }

  @Override
  public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_video, parent, false);
    return new VideoViewHolder(view);
  }

  @Override
  public void onBindViewHolder(VideoViewHolder holder, int position) {
    VideoView videoView = getItem(position);
    Video video = videoView.getVideo();
    User user = video.getUser();
    Contest contest = video.getContest();

    setUser(holder, user);
    setContest(holder, contest);
    setVideo(holder, video);
    setVideoDate(holder, video);
    setDescription(holder, video);
    setComments(holder, videoView);
    setLikes(holder, videoView);
    setFan(holder, videoView);
  }

  private void setUser(VideoViewHolder holder, User user) {
    if(user != null) {
      holder.userPictureView.setVisibility(View.VISIBLE);
      holder.userNameView.setVisibility(View.VISIBLE);

      MediaUtils.setPicture(getContext(), user, holder.userPictureView);
      holder.userNameView.setText(user.getName());
    } else {
      holder.userPictureView.setVisibility(View.GONE);
      holder.userNameView.setVisibility(View.GONE);
    }
  }

  private void setContest(VideoViewHolder holder, Contest contest) {
    if(contest != null && contest.getProgress().equals(Progress.RUNNING)) {
      holder.contestView.setVisibility(View.VISIBLE);
      holder.contestSeparatorView.setVisibility(View.VISIBLE);
    } else {
      holder.contestView.setVisibility(View.GONE);
      holder.contestSeparatorView.setVisibility(View.GONE);
    }
  }

  private void setVideo(VideoViewHolder holder, Video video) {
    holder.thumbnailView.setVisibility(View.VISIBLE);
    holder.playIconView.setVisibility(View.VISIBLE);
    holder.streamView.setVisibility(View.GONE);
    MediaUtils.setThumbnail(getContext(), video, holder.thumbnailView);
  }

  private void setVideoDate(VideoViewHolder holder, Video video) {
    holder.dateView.setText(DateUtils.getRelativeTimeSpanString(video.getRegistrationDate().getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
  }

  private void setDescription(VideoViewHolder holder, Video video) {
    if(!TextUtils.isEmpty(video.getDescription())) {
      holder.descriptionView.setText(video.getDescription());
      holder.descriptionView.setVisibility(View.VISIBLE);
    } else {
      holder.descriptionView.setText("");
      holder.descriptionView.setVisibility(View.GONE);
    }
  }

  private void setComments(VideoViewHolder holder, VideoView videoView) {
    holder.totalCommentsView.setText(getContext().getString(R.string.total_comments, videoView.getTotalComments()));
  }

  private void setLikes(VideoViewHolder holder, VideoView videoView) {
    if(!videoView.isLiked()) {
      holder.likeIconView.setImageResource(R.drawable.no_like);
    } else {
      holder.likeIconView.setImageResource(R.drawable.like);
    }
    holder.totalLikesView.setText(getContext().getString(R.string.total_likes, videoView.getTotalLikes()));
  }

  private void setFan(VideoViewHolder holder, VideoView videoView) {
    if(showFanIcon && videoView.getVideo().getUser() != null) {
      if(!videoView.isFan()) {
        holder.fanIconView.setImageResource(R.drawable.no_fan);
      } else {
        holder.fanIconView.setImageResource(R.drawable.fan);
      }
      holder.fanIconView.setVisibility(View.VISIBLE);
    } else {
      holder.fanIconView.setVisibility(View.GONE);
    }
  }

  class VideoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.root) View rootView;
    @BindView(R.id.user_picture) ImageView userPictureView;
    @BindView(R.id.user_name) TextView userNameView;

    @BindView(R.id.contest) TextView contestView;
    @BindView(R.id.contest_separator) TextView contestSeparatorView;
    @BindView(R.id.date) TextView dateView;

    @BindView(R.id.description) TextView descriptionView;
    @BindView(R.id.stream) android.widget.VideoView streamView;
    @BindView(R.id.thumbnail) ImageView thumbnailView;
    @BindView(R.id.play_icon) ImageView playIconView;
    @BindView(R.id.fan_icon) ImageView fanIconView;
    @BindView(R.id.like_icon) ImageView likeIconView;
    @BindView(R.id.total_likes) TextView totalLikesView;
    @BindView(R.id.total_comments) TextView totalCommentsView;
    @BindView(R.id.loading_video) ProgressView loadingVideoProgressView;
    @BindView(R.id.loading_like) ProgressView loadingLikeProgressView;
    @BindView(R.id.loading_fan) ProgressView loadingFanProgressView;

    private VideoViewHolder instance;
    private MediaPlayer mediaPlayer;

    VideoViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      instance = this;
    }

    private void changeLikes() {
      VideoView videoView = getItem(getAdapterPosition());
      videoView.setTotalLikes(videoView.getTotalLikes() + (!videoView.isLiked() ? 1 : -1));
      videoView.setLiked(!videoView.isLiked());
      setLikes(instance, videoView);
    }

    private void changeFan(VideoView videoView) {
      videoView.setFan(!videoView.isFan());
      setFan(this, videoView);
    }

    @OnClick({R.id.user_name, R.id.user_picture})
    void onUserClick() {
      User user = getItem(getAdapterPosition()).getVideo().getUser();
      Intent intent = new Intent(getContext(), UserActivity.class);
      intent.putExtra(UserActivity.USER_ID, user.getId());
      getContext().startActivity(intent);
    }

    @OnClick(R.id.contest)
    void onContestClick() {
      Contest contest = getItem(getAdapterPosition()).getVideo().getContest();
      Intent intent = new Intent(getContext(), ContestActivity.class);
      intent.putExtra(ContestActivity.CONTEST_ID, contest.getId());
      getContext().startActivity(intent);
    }

    @OnClick({R.id.comment_icon, R.id.status})
    void onCommentIconOrStatusClick() {
      Video video = getItem(getAdapterPosition()).getVideo();
      Intent intent = new Intent(getContext(), CommentsActivity.class);
      intent.putExtra(CommentsActivity.VIDEO_ID, video.getId());
      getContext().startActivity(intent);
    }

    @OnClick(R.id.share_icon)
    void onShareIconClick() {
      Video video = getItem(getAdapterPosition()).getVideo();
      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setType("text/plain");
      intent.putExtra(Intent.EXTRA_TEXT, getContext().getString(R.string.share_video_description, ApplicationUtils.getVideoUrl(video)));
      getContext().startActivity(Intent.createChooser(intent, getContext().getString(R.string.share_video_title)));
    }

    @OnClick(R.id.like_icon)
    void onLikeIconClick() {
      VideoView videoView = getItem(getAdapterPosition());
      Video video = videoView.getVideo();
      VideoService videoService = RemoteService.getInstance(getContext()).getVideoService();

      Promise<Void> promise = !videoView.isLiked() ? videoService.like(video) : videoService.dislike(video);
      changeLikes();
      promise.then(new DoneCallback<Void>() {
        @Override
        public void onDone(Void result) {
          // Do nothing, method was executed with success.
        }
      }).fail(new FailCallback<APIException>() {
        @Override
        public void onFail(APIException e) {
          Log.e(TAG, "Error liking video", e);
          UIUtils.alert(rootView, e, getContext().getString(R.string.alert_error_liking_video));
          changeLikes();
        }
      }).progress(new ProgressCallback<Promise.Progress>() {
        @Override
        public void onProgress(Promise.Progress progress) {
          if(Promise.Progress.PENDING.equals(progress)) {
            loadingLikeProgressView.start();
            likeIconView.setEnabled(false);
          } else if(Promise.Progress.PROCESSED.equals(progress)) {
            loadingLikeProgressView.stop();
            likeIconView.setEnabled(true);
          }
        }
      });
    }

    @OnClick(R.id.fan_icon)
    void onFanIconClick() {
      final VideoView videoView = getItem(getAdapterPosition());
      UserService userService = RemoteService.getInstance(getContext()).getUserService();

      Promise<Void> promise = !videoView.isFan() ? userService.becomeFan(videoView.getVideo().getUser()) : userService.removeFan(videoView.getVideo().getUser());
      changeFan(videoView);
      promise.then(new DoneCallback<Void>() {
        @Override
        public void onDone(Void result) {
          // Do nothing, method was executed with success.
        }
      }).fail(new FailCallback<APIException>() {
        @Override
        public void onFail(APIException e) {
          Log.e(TAG, "Error becoming a user fan", e);
          UIUtils.alert(rootView, e, getContext().getString(R.string.alert_error_becoming_user_fan));
          changeFan(videoView);
        }
      }).progress(new ProgressCallback<Promise.Progress>() {
        @Override
        public void onProgress(Promise.Progress progress) {
          if(Promise.Progress.PENDING.equals(progress)) {
            loadingFanProgressView.start();
            fanIconView.setEnabled(false);
          } else if(Promise.Progress.PROCESSED.equals(progress)) {
            loadingFanProgressView.stop();
            fanIconView.setEnabled(true);
          }
        }
      });
    }

    @OnClick(R.id.video)
    void onVideoClick() {
      if(thumbnailView.getVisibility() == View.VISIBLE) {
        loadingVideoProgressView.start();
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
            loadingVideoProgressView.stop();
          }
        });
        streamView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
          @Override
          public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
          }
        });
      } else if(mediaPlayer != null) {
        if(mediaPlayer.isPlaying()) {
          mediaPlayer.pause();
          playIconView.setVisibility(View.VISIBLE);
        } else {
          mediaPlayer.start();
          playIconView.setVisibility(View.GONE);
        }
      } else {
        thumbnailView.setVisibility(View.VISIBLE);
        playIconView.setVisibility(View.VISIBLE);
        streamView.setVisibility(View.GONE);
      }
    }
  }
}