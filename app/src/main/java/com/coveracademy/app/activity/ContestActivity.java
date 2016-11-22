package com.coveracademy.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.coveracademy.api.enumeration.Progress;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.view.VideoView;
import com.coveracademy.api.model.view.ContestView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.R;
import com.coveracademy.app.adapter.VideosAdapter;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.UIUtils;
import com.coveracademy.app.util.component.ContestCountDownTimer;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContestActivity extends CoverAcademyActivity {

  public static final String CONTEST_ID = "CONTEST_ID";

  private static final String TAG = ContestActivity.class.getSimpleName();

  private ContestActivity instance;
  private RemoteService remoteService;
  private VideosAdapter videosAdapter;
  private ContestView contestView;

  @BindView(R.id.root) View rootView;
  @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
  @BindView(R.id.contest_image) ImageView contestImageView;
  @BindView(R.id.contest_running) View contestRunningView;
  @BindView(R.id.contest_finished) View contestFinishedView;
  @BindView(R.id.winners) View winnersView;
  @BindView(R.id.first_winner_picture) ImageView firstWinnerPictureView;
  @BindView(R.id.second_winner_picture) ImageView secondWinnerPictureView;
  @BindView(R.id.third_winner_picture) ImageView thirdWinnerPictureView;
  @BindView(R.id.videos) RecyclerView videosView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contest);
    ButterKnife.bind(this);

    instance = this;
    remoteService = RemoteService.getInstance(this);

    setupVideosAdapter();
    setupContestView();

    UIUtils.defaultToolbar(this);
    collapsingToolbar.setTitle(" ");
  }

  private void setupVideosAdapter() {
    videosAdapter = new VideosAdapter(this);
    videosView.setLayoutManager(new LinearLayoutManager(this));
    videosView.setAdapter(videosAdapter);
    videosView.setNestedScrollingEnabled(false);
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
        instance.contestView = contestView;
        setupContest();
        setupVideos();
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading contests view", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_contest_alert_error_loading_contest));
        finish();
      }
    });
  }

  private void setupContest() {
    Contest contest = contestView.getContest();
    collapsingToolbar.setTitle(contest.getName());
    MediaUtils.setImage(instance, contestView.getContest(), contestImageView);
    if(contest.getProgress().equals(Progress.RUNNING)) {
      contestRunningView.setVisibility(View.VISIBLE);
      contestFinishedView.setVisibility(View.GONE);
      new ContestCountDownTimer(this, contest, rootView).start();
    } else {
      contestRunningView.setVisibility(View.GONE);
      contestFinishedView.setVisibility(View.VISIBLE);
      winnersView.setVisibility(View.VISIBLE);
      for(int index = 0; index < contestView.getWinners().size(); index++) {
        User user = contestView.getWinners().get(index);
        if(index == 0) {
          MediaUtils.setPicture(this, user, firstWinnerPictureView);
        } else if(index == 1) {
          MediaUtils.setPicture(this, user, secondWinnerPictureView);
        } else {
          MediaUtils.setPicture(this, user, thirdWinnerPictureView);
        }
      }
    }
  }

  private void setupVideos() {
    List<VideoView> videosView = new ArrayList<>();
    for(Video video : contestView.getVideos()) {
      VideoView videoView = new VideoView();
      videoView.setVideo(video);
      videoView.setLiked(contestView.getLikedVideos().contains(video.getId()));
      videoView.setFan(contestView.getIdols().contains(video.getUserId()));
      if(contestView.getTotalLikes().containsKey(video.getId())) {
        videoView.setTotalLikes(contestView.getTotalLikes().get(video.getId()));
      }
      if(contestView.getTotalComments().containsKey(video.getId())) {
        videoView.setTotalComments(contestView.getTotalComments().get(video.getId()));
      }
      videosView.add(videoView);
    }
    videosAdapter.setItems(videosView);
  }

  @OnClick(R.id.first_winner_picture)
  void onFirstWinnerPictureClick() {
    User user = contestView.getWinners().get(0);
    onWinnerClick(user);
  }

  @OnClick(R.id.second_winner_picture)
  void onSecondWinnerPictureClick() {
    User user = contestView.getWinners().get(1);
    onWinnerClick(user);
  }

  @OnClick(R.id.third_winner_picture)
  void onThirdWinnerPictureClick() {
    User user = contestView.getWinners().get(2);
    onWinnerClick(user);
  }

  private void onWinnerClick(User user) {
    Intent intent = new Intent(this, UserActivity.class);
    intent.putExtra(UserActivity.USER_ID, user.getId());
    startActivity(intent);
  }
}