package com.coveracademy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.CoverAcademyApplication;
import com.coveracademy.R;
import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.util.ApplicationUtils;
import com.coveracademy.util.ImageUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sandro on 15/10/15.
 */
public class AuditionsAdapter extends BaseAdapter<Audition, AuditionsAdapter.AuditionViewHolder> {

  private CoverAcademyApplication application;
  private OnUserClickListener onUserClickListener;

  public AuditionsAdapter(Context context) {
    super(context, listAuditions(context));
    application = ApplicationUtils.getApplication(context);
  }

  private static List<Audition> listAuditions(Context context) {
    CoverAcademyApplication application = ApplicationUtils.getApplication(context);
    return application.listAuditions();
  }

  @Override
  public void reloadItems() {
    setItems(listAuditions(getContext()));
    super.reloadItems();
  }

  @Override
  public AuditionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_audition, parent, false);
    return new AuditionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AuditionViewHolder holder, int position) {
    Audition audition = getItem(position);
    Contest contest = application.getContest(audition.getContestId());
    User user = application.getUser(audition.getUserId());
    holder.userNameView.setText(user.getName());
    holder.totalVotesView.setText(getContext().getString(R.string.activity_main_total_votes, application.getTotalVotes(audition) != null ? application.getTotalVotes(audition) : 0));
    holder.totalCommentsView.setText(getContext().getString(R.string.activity_main_total_comments, application.getTotalComments(audition) != null ? application.getTotalComments(audition) : 0));
    ImageUtils.setThumbnail(getContext(), audition, holder.auditionThumbnailView);
    ImageUtils.setPicture(getContext(), user, holder.userAvatarView);
  }

  public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
    this.onUserClickListener = onUserClickListener;
  }

  public class AuditionViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.user_avatar) ImageView userAvatarView;
    @Bind(R.id.user_name) TextView userNameView;
    @Bind(R.id.audition_thumbnail) ImageView auditionThumbnailView;
    @Bind(R.id.total_votes) TextView totalVotesView;
    @Bind(R.id.total_comments) TextView totalCommentsView;

    public AuditionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.user_name)
    void onUserClick(View view) {
      if(onUserClickListener != null) {
        Audition audition = getItem(getAdapterPosition());
        onUserClickListener.onUserClick(audition.getUserId());
      }
    }
  }

  public interface OnUserClickListener {

    void onUserClick(Long userId);

  }
}