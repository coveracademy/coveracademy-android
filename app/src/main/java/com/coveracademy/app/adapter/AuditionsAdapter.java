package com.coveracademy.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.app.R;
import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.AuditionView;
import com.coveracademy.app.util.ImageUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuditionsAdapter extends BaseAdapter<AuditionView, AuditionsAdapter.AuditionViewHolder> {

  private OnUserClickListener onUserClickListener;

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
    Audition audition = auditionView.getAudition();
    Contest contest = auditionView.getContest();
    User user = auditionView.getUser();
    int totalVotes = auditionView.getTotalVotes();
    int totalComments = auditionView.getTotalComments();

    holder.userNameView.setText(user.getName());
    holder.totalVotesView.setText(getContext().getString(R.string.activity_main_total_votes, totalVotes));
    holder.totalCommentsView.setText(getContext().getString(R.string.activity_main_total_comments, totalComments));
    ImageUtils.setThumbnail(getContext(), audition, holder.auditionThumbnailView);
    ImageUtils.setPhoto(getContext(), user, holder.userAvatarView);
  }

  public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
    this.onUserClickListener = onUserClickListener;
  }

  public class AuditionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.user_avatar) ImageView userAvatarView;
    @BindView(R.id.user_name) TextView userNameView;
    @BindView(R.id.audition_thumbnail) ImageView auditionThumbnailView;
    @BindView(R.id.total_votes) TextView totalVotesView;
    @BindView(R.id.total_comments) TextView totalCommentsView;

    public AuditionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.user)
    void onUserClick() {
      if(onUserClickListener != null) {
        User user = getItem(getAdapterPosition()).getUser();
        onUserClickListener.onUserClick(user);
      }
    }
  }

  public interface OnUserClickListener {

    void onUserClick(User user);

  }
}