package com.coveracademy.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.api.model.Video;
import com.coveracademy.app.R;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.AuditionView;
import com.coveracademy.app.activity.ContestActivity;
import com.coveracademy.app.activity.UserActivity;
import com.coveracademy.app.util.MediaUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuditionsAdapter extends BaseAdapter<AuditionView, AuditionsAdapter.AuditionViewHolder> {

  public AuditionsAdapter(Context context) {
    super(context, new ArrayList<AuditionView>());
  }

  @Override
  public AuditionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_audition, parent, false);
    return new AuditionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AuditionViewHolder holder, int position) {
    AuditionView auditionView = getItem(position);
    User user = auditionView.getAudition().getUser();
    Video audition = auditionView.getAudition();
    Contest contest = auditionView.getAudition().getContest();

    MediaUtils.setPicture(getContext(), user, holder.userAvatarView);
    MediaUtils.setThumbnail(getContext(), audition, holder.auditionThumbnailView);

    holder.userNameView.setText(user.getName());
    holder.dateView.setText(DateUtils.getRelativeTimeSpanString(audition.getRegistrationDate().getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
    holder.totalLikesView.setText(getContext().getString(R.string.total_likes, auditionView.getTotalLikes()));
    holder.totalCommentsView.setText(getContext().getString(R.string.total_comments, auditionView.getTotalComments()));

    if(contest != null) {
      holder.contestNameView.setText(contest.getName());
      holder.contestView.setVisibility(View.VISIBLE);
    } else {
      holder.contestView.setVisibility(View.GONE);
    }

    if(!auditionView.isLiked()) {
      holder.likedView.setImageResource(R.drawable.no_like);
    } else {
      holder.likedView.setImageResource(R.drawable.comment);
    }
  }

  class AuditionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.user_avatar) ImageView userAvatarView;
    @BindView(R.id.user_name) TextView userNameView;
    @BindView(R.id.date) TextView dateView;
    @BindView(R.id.audition_thumbnail) ImageView auditionThumbnailView;
    @BindView(R.id.contest) View contestView;
    @BindView(R.id.contest_name) TextView contestNameView;
    @BindView(R.id.liked) ImageView likedView;
    @BindView(R.id.total_likes) TextView totalLikesView;
    @BindView(R.id.total_comments) TextView totalCommentsView;

    AuditionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.user)
    void onUserClick() {
      User user = getItem(getAdapterPosition()).getAudition().getUser();
      Intent intent = new Intent(getContext(), UserActivity.class);
      intent.putExtra(UserActivity.USER_ID, user.getId());
    }

    @OnClick(R.id.contest_name)
    void onContestClick() {
      Contest contest = getItem(getAdapterPosition()).getAudition().getContest();
      Intent intent = new Intent(getContext(), ContestActivity.class);
      intent.putExtra(ContestActivity.CONTEST_ID, contest.getId());
      getContext().startActivity(intent);
    }
  }
}