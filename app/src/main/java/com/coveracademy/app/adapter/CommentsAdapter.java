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

import com.coveracademy.api.model.Comment;
import com.coveracademy.api.model.User;
import com.coveracademy.app.R;
import com.coveracademy.app.activity.UserActivity;
import com.coveracademy.app.util.MediaUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentsAdapter extends BaseAdapter<Comment, CommentsAdapter.CommentViewHolder> {

  public CommentsAdapter(Context context) {
    super(context, new ArrayList<Comment>());
  }

  @Override
  public CommentsAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
    return new CommentViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CommentsAdapter.CommentViewHolder holder, int position) {
    Comment comment = getItem(position);
    User user = comment.getUser();

    MediaUtils.setPicture(getContext(), user, holder.userPictureView);
    holder.userNameView.setText(user.getName());
    holder.messageView.setText(comment.getMessage());
    holder.dateView.setText(DateUtils.getRelativeTimeSpanString(comment.getSendDate().getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
  }

  class CommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.user_picture) ImageView userPictureView;
    @BindView(R.id.user_name) TextView userNameView;
    @BindView(R.id.message) TextView messageView;
    @BindView(R.id.date) TextView dateView;

    CommentViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick({R.id.user_picture, R.id.user_name})
    void onUserClick() {
      User user = getItem(getAdapterPosition()).getUser();
      Intent intent = new Intent(getContext(), UserActivity.class);
      intent.putExtra(UserActivity.USER_ID, user.getId());
      getContext().startActivity(intent);
    }
  }
}
