package com.coveracademy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.R;
import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.view.ContestView;
import com.coveracademy.util.ImageUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sandro on 06/11/15.
 */
public class ContestsAdapter extends BaseAdapter<ContestView, ContestsAdapter.ContestViewHolder> {

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
    holder.totalAuditionsView.setText(getContext().getString(R.string.activity_main_total_auditions, totalAuditions));
    ImageUtils.setImage(getContext(), contest, holder.contestImageView);

    if(contest.getProgress().equals(Contest.Progress.finished)) {
      holder.winnersView.setVisibility(View.VISIBLE);
      for(int index = 0; index < contestView.getWinnerAuditions().size(); index++) {
        Audition audition = contestView.getWinnerAuditions().get(index);
        if(index == 0) {
          ImageUtils.setPicture(getContext(), audition.getUser(), holder.firstWinnerAvatarView);
        } else if(index == 1) {
          ImageUtils.setPicture(getContext(), audition.getUser(), holder.secondWinnerAvatarView);
        } else {
          ImageUtils.setPicture(getContext(), audition.getUser(), holder.thirdWinnerAvatarView);
        }
      }
    } else {
      holder.winnersView.setVisibility(View.GONE);
    }
  }

  public class ContestViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.contest_name) TextView contestNameView;
    @Bind(R.id.contest_image) ImageView contestImageView;
    @Bind(R.id.total_auditions) TextView totalAuditionsView;
    @Bind(R.id.winners) View winnersView;
    @Bind(R.id.first_winner_avatar) ImageView firstWinnerAvatarView;
    @Bind(R.id.second_winner_avatar) ImageView secondWinnerAvatarView;
    @Bind(R.id.third_winner_avatar) ImageView thirdWinnerAvatarView;

    public ContestViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}