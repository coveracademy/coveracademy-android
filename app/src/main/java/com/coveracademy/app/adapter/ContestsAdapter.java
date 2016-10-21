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
import com.coveracademy.api.model.view.ContestView;
import com.coveracademy.app.util.ImageUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContestsAdapter extends BaseAdapter<ContestView, ContestsAdapter.ContestViewHolder> {

  private OnContestClickListener onContestClickListener;

  public ContestsAdapter(Context context) {
    super(context, new ArrayList<ContestView>());
  }

  @Override
  public ContestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_contest, parent, false);
    return new ContestViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ContestViewHolder holder, int position) {
    ContestView contestView = getItem(position);
    Contest contest = contestView.getContest();
    int totalAuditions = contestView.getTotalAuditions();

    holder.contestNameView.setText(contest.getName());
    holder.totalAuditionsView.setText(getContext().getString(R.string.total_auditions, totalAuditions));
    ImageUtils.setImage(getContext(), contest, holder.contestImageView);

    if(contest.getProgress().equals(Contest.Progress.finished)) {
      holder.actionsView.setVisibility(View.GONE);
      holder.winnersView.setVisibility(View.VISIBLE);
      for(int index = 0; index < contestView.getWinnerAuditions().size(); index++) {
        Audition audition = contestView.getWinnerAuditions().get(index);
        if(index == 0) {
          ImageUtils.setPhoto(getContext(), audition.getUser(), holder.firstWinnerAvatarView);
        } else if(index == 1) {
          ImageUtils.setPhoto(getContext(), audition.getUser(), holder.secondWinnerAvatarView);
        } else {
          ImageUtils.setPhoto(getContext(), audition.getUser(), holder.thirdWinnerAvatarView);
        }
      }
    } else {
      holder.actionsView.setVisibility(View.VISIBLE);
      holder.winnersView.setVisibility(View.GONE);
    }
  }

  public void setOnContestClickListener(OnContestClickListener onContestClickListener) {
    this.onContestClickListener = onContestClickListener;
  }

  public class ContestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.contest_name) TextView contestNameView;
    @BindView(R.id.contest_image) ImageView contestImageView;
    @BindView(R.id.total_auditions) TextView totalAuditionsView;
    @BindView(R.id.winners) View winnersView;
    @BindView(R.id.actions) View actionsView;
    @BindView(R.id.first_winner_avatar) ImageView firstWinnerAvatarView;
    @BindView(R.id.second_winner_avatar) ImageView secondWinnerAvatarView;
    @BindView(R.id.third_winner_avatar) ImageView thirdWinnerAvatarView;

    public ContestViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.contest)
    void onContestClick(View view) {
      if(onContestClickListener != null) {
        Contest contest = getItem(getAdapterPosition()).getContest();
        onContestClickListener.onContestClick(contest);
      }
    }
  }

  public interface OnContestClickListener {

    void onContestClick(Contest contest);

    void onWinnerClick(User user);

  }
}