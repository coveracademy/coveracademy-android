package com.coveracademy.app.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.api.enumeration.Progress;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.view.AuditionView;
import com.coveracademy.api.model.view.ContestView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.R;
import com.coveracademy.app.adapter.AuditionsAdapter;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContestActivity extends CoverAcademyActivity {

  public static final String CONTEST_ID = "CONTEST_ID";

  private static final String TAG = ContestActivity.class.getSimpleName();

  private ContestActivity instance;
  private RemoteService remoteService;
  private AuditionsAdapter auditionsAdapter;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.contest_name) TextView contestNameView;
  @BindView(R.id.contest_image) ImageView contestImageView;
  @BindView(R.id.contest_countdown) View countdownView;
  @BindView(R.id.contest_finished) View contestFinishedView;
  @BindView(R.id.days_remaining) TextView daysRemainingView;
  @BindView(R.id.hours_remaining) TextView hoursRemainingView;
  @BindView(R.id.minutes_remaining) TextView minutesRemainingView;
  @BindView(R.id.seconds_remaining) TextView secondsRemainingView;
  @BindView(R.id.videos) RecyclerView videosView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contest);
    ButterKnife.bind(this);

    instance = this;
    remoteService = RemoteService.getInstance(this);

    setupAuditionsAdapter();
    setupContestView();

    UIUtils.defaultToolbar(this);
    toolbar.setTitle("");
    toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.transparent));
  }

  private void setupAuditionsAdapter() {
    auditionsAdapter = new AuditionsAdapter(this);
    videosView.setLayoutManager(new LinearLayoutManager(this));
    videosView.setAdapter(auditionsAdapter);
  }

  private void setupContestView() {
    long contestId = getIntent().getLongExtra(CONTEST_ID, -1L);
    if(contestId == -1L) {
      finish();
      return;
    }
    remoteService.getViewService().contestView(contestId).then(new DoneCallback<ContestView>() {
      @Override
      public void onDone(ContestView contestView) {
        setupContestInformation(contestView);
        setupAuditions(contestView);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading contests view", e);
        UIUtils.alert(instance, e, getString(R.string.activity_contest_alert_error_loading_contest));
        finish();
      }
    });
  }

  private void setupContestInformation(ContestView contestView) {
    Contest contest = contestView.getContest();
    contestNameView.setText(contestView.getContest().getName());
    MediaUtils.setImage(instance, contestView.getContest(), contestImageView);
    long millisInFuture = contest.getEndDate().getTime() - System.currentTimeMillis();
    if(millisInFuture > 0 && contest.getProgress().equals(Progress.RUNNING)) {
      countdownView.setVisibility(View.VISIBLE);
      new CountDownTimer(millisInFuture, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
          int days = (int) ((millisUntilFinished / 1000) / 86400);
          int hours = (int) (((millisUntilFinished / 1000) - (days * 86400)) / 3600);
          int minutes = (int) (((millisUntilFinished / 1000) - (days * 86400) - (hours * 3600)) / 60);
          int seconds = (int) ((millisUntilFinished / 1000) % 60);
          daysRemainingView.setText(getString(R.string.activity_contest_days_remaining, days));
          hoursRemainingView.setText(getString(R.string.activity_contest_hours_remaining, hours));
          minutesRemainingView.setText(getString(R.string.activity_contest_minutes_remaining, minutes));
          secondsRemainingView.setText(getString(R.string.activity_contest_seconds_remaining, seconds));
        }

        @Override
        public void onFinish() {

        }
      }.start();
    } else {
      contestFinishedView.setVisibility(View.VISIBLE);
    }
  }

  private void setupAuditions(ContestView contestView) {
    List<AuditionView> auditionsViews = new ArrayList<>();
    for(Video audition : contestView.getAuditions()) {
      AuditionView auditionView = new AuditionView();
      auditionView.setAudition(audition);
      auditionView.setLiked(contestView.getLikedAuditions().contains(audition.getId()));
      if(contestView.getTotalLikes().containsKey(audition.getId())) {
        auditionView.setTotalLikes(contestView.getTotalLikes().get(audition.getId()));
      }
      if(contestView.getTotalComments().containsKey(audition.getId())) {
        auditionView.setTotalComments(contestView.getTotalComments().get(audition.getId()));
      }
      auditionsViews.add(auditionView);
    }
    auditionsAdapter.setItems(auditionsViews);
  }
}