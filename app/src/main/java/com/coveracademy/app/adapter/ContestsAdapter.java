package com.coveracademy.app.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.coveracademy.app.activity.ContestActivity;
import com.coveracademy.app.activity.UserActivity;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.component.ContestCountDownTimer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContestsAdapter extends BaseAdapter<ContestsItemView, ContestsAdapter.ContestViewHolder> {

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
      holder.contestRunningView.setVisibility(View.VISIBLE);
      holder.contestFinishedView.setVisibility(View.GONE);
      holder.winnersView.setVisibility(View.GONE);
      new ContestCountDownTimer(getContext(), contest, holder.itemView).start();
    } else if(Progress.FINISHED.equals(contest.getProgress())) {
      holder.contestRunningView.setVisibility(View.GONE);
      holder.contestFinishedView.setVisibility(View.VISIBLE);
      holder.winnersView.setVisibility(View.VISIBLE);
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

  class ContestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.contest_name) TextView contestNameView;
    @BindView(R.id.contest_image) ImageView contestImageView;
    @BindView(R.id.contest_running) View contestRunningView;
    @BindView(R.id.contest_finished) View contestFinishedView;
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
      Contest contest = getItem(getAdapterPosition()).getContest();
      Intent intent = new Intent(getContext(), ContestActivity.class);
      intent.putExtra(ContestActivity.CONTEST_ID, contest.getId());
      getContext().startActivity(intent);
    }

    @OnClick(R.id.first_winner_picture)
    void onFirstWinnerPictureClick() {
      User user = getItem(getAdapterPosition()).getWinners().get(0);
      onWinnerClick(user);
    }

    @OnClick(R.id.second_winner_picture)
    void onSecondWinnerPictureClick() {
      User user = getItem(getAdapterPosition()).getWinners().get(1);
      onWinnerClick(user);
    }

    @OnClick(R.id.third_winner_picture)
    void onThirdWinnerPictureClick() {
      User user = getItem(getAdapterPosition()).getWinners().get(2);
      onWinnerClick(user);
    }

    private void onWinnerClick(User user) {
      Intent intent = new Intent(getContext(), UserActivity.class);
      intent.putExtra(UserActivity.USER_ID, user.getId());
      getContext().startActivity(intent);
    }
  }
}