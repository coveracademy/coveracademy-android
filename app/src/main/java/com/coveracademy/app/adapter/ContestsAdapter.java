package com.coveracademy.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.api.enumeration.Progress;
import com.coveracademy.app.R;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.ContestsItemView;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.component.ContestCountDownTimer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContestsAdapter extends BaseAdapter<ContestsItemView, ContestsAdapter.ContestViewHolder> {

  private OnContestClickListener onContestClickListener;

  public ContestsAdapter(Context context) {
    super(context, new ArrayList<ContestsItemView>());
  }

  @Override
  public ContestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_contest, parent, false);
    return new ContestViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ContestViewHolder holder, int position) {
    ContestsItemView contestsItemView = getItem(position);
    Contest contest = contestsItemView.getContest();

    holder.contestNameView.setText(contest.getName());
    MediaUtils.setImage(getContext(), contest, holder.contestImageView);

    if(Progress.RUNNING.equals(contest.getProgress())) {
      holder.winnersView.setVisibility(View.GONE);
      holder.contestRunningView.setVisibility(View.VISIBLE);
      new ContestCountDownTimer(getContext(), contest, holder.itemView).start();
    } else if(Progress.FINISHED.equals(contest.getProgress())) {
      holder.winnersView.setVisibility(View.VISIBLE);
      holder.contestRunningView.setVisibility(View.GONE);
      for(int index = 0; index < contestsItemView.getWinners().size(); index++) {
        User user = contestsItemView.getWinners().get(index);
        if(index == 0) {
          MediaUtils.setPicture(getContext(), user, holder.firstWinnerPictureView);
        } else if(index == 1) {
          MediaUtils.setPicture(getContext(), user, holder.secondWinnerPictureView);
        } else {
          MediaUtils.setPicture(getContext(), user, holder.thirdWinnerPictureView);
        }
      }
    } else {
      holder.winnersView.setVisibility(View.GONE);
      holder.contestRunningView.setVisibility(View.GONE);
    }
  }

  public void setOnContestClickListener(OnContestClickListener onContestClickListener) {
    this.onContestClickListener = onContestClickListener;
  }

  class ContestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.contest_name) TextView contestNameView;
    @BindView(R.id.contest_image) ImageView contestImageView;
    @BindView(R.id.contest_running) View contestRunningView;
    @BindView(R.id.days_remaining) TextView daysRemainingView;
    @BindView(R.id.hours_remaining) TextView hoursRemainingView;
    @BindView(R.id.minutes_remaining) TextView minutesRemainingView;
    @BindView(R.id.seconds_remaining) TextView secondsRemainingView;
    @BindView(R.id.winners) View winnersView;
    @BindView(R.id.first_winner_picture) ImageView firstWinnerPictureView;
    @BindView(R.id.second_winner_picture) ImageView secondWinnerPictureView;
    @BindView(R.id.third_winner_picture) ImageView thirdWinnerPictureView;

    ContestViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.contest)
    void onContestClick() {
      if(onContestClickListener != null) {
        Contest contest = getItem(getAdapterPosition()).getContest();
        onContestClickListener.onContestClick(contest);
      }
    }

    @OnClick(R.id.first_winner_picture)
    void onFirstWinnerPictureClick() {
      if(onContestClickListener != null) {
        User user = getItem(getAdapterPosition()).getWinners().get(0);
        onContestClickListener.onWinnerClick(user);
      }
    }

    @OnClick(R.id.second_winner_picture)
    void onSecondWinnerPictureClick() {
      if(onContestClickListener != null) {
        User user = getItem(getAdapterPosition()).getWinners().get(1);
        onContestClickListener.onWinnerClick(user);
      }
    }

    @OnClick(R.id.third_winner_picture)
    void onThirdWinnerPictureClick() {
      if(onContestClickListener != null) {
        User user = getItem(getAdapterPosition()).getWinners().get(2);
        onContestClickListener.onWinnerClick(user);
      }
    }
  }

  public interface OnContestClickListener {

    void onContestClick(Contest contest);

    void onWinnerClick(User user);

  }
}