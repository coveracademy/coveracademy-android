package com.coveracademy.app.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.api.model.Video;
import com.coveracademy.api.model.view.VideoView;
import com.coveracademy.api.promise.Promise;
import com.coveracademy.app.R;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.UserView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.adapter.VideosAdapter;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.UIUtils;
import com.rey.material.widget.ProgressView;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends CoverAcademyActivity {

  public static final String USER_ID = "USER_ID";

  private static final String TAG = UserActivity.class.getSimpleName();

  private UserActivity instance;
  private RemoteService remoteService;
  private UserView userView;
  private VideosAdapter videosAdapter;

  @BindView(R.id.root) View rootView;
  @BindView(R.id.picture) ImageView pictureView;
  @BindView(R.id.name) TextView nameView;
  @BindView(R.id.biography) TextView biographyView;
  @BindView(R.id.total_videos) TextView totalVideosView;
  @BindView(R.id.total_fans) TextView totalFansView;
  @BindView(R.id.total_idols) TextView totalIdolsView;
  @BindView(R.id.fan_icon) ImageView fanIconView;
  @BindView(R.id.loading_fan) ProgressView loadingFanProgressView;
  @BindView(R.id.become_fan) Button becomeFanButton;
  @BindView(R.id.remove_fan) Button removeFanButton;
  @BindView(R.id.videos) RecyclerView videosView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);
    ButterKnife.bind(this);

    instance = this;
    remoteService = RemoteService.getInstance(this);

    setupVideosAdapter();
    setupUserView();

    UIUtils.defaultToolbar(this);
    setTitle("");
  }

  private void setupUserView() {
    final long userId = getIntent().getLongExtra(USER_ID, -1L);
    if(userId == -1L) {
      finish();
      return;
    }
    remoteService.getViewService().userView(userId).then(new DoneCallback<UserView>() {
      @Override
      public void onDone(UserView userView) {
        instance.userView = userView;
        setupUser();
        setupVideos();
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading user view", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_user_alert_error_loading_user));
      }
    });
  }

  private void setupVideosAdapter() {
    videosAdapter = new VideosAdapter(this);
    videosAdapter.showFanIcon(false);
    videosView.setLayoutManager(new LinearLayoutManager(this));
    videosView.setAdapter(videosAdapter);
    videosView.setNestedScrollingEnabled(false);
  }

  private void setupUser() {
    User user = userView.getUser();
    setTitle(user.getName());
    MediaUtils.setPicture(instance, user, pictureView);
    nameView.setText(user.getName());
    totalVideosView.setText(String.valueOf(userView.getTotalVideos()));
    totalIdolsView.setText(String.valueOf(userView.getTotalIdols()));
    if(!TextUtils.isEmpty(user.getBiography())) {
      biographyView.setText(user.getBiography());
      biographyView.setVisibility(View.VISIBLE);
    } else {
      biographyView.setText("");
      biographyView.setVisibility(View.GONE);
    }
    setFan();
  }

  private void setupVideos() {
    List<VideoView> videosView = new ArrayList<>();
    for(Video video : userView.getVideos()) {
      video.setUser(userView.getUser());
      VideoView videoView = new VideoView();
      videoView.setVideo(video);
      videoView.setLiked(userView.getLikedVideos().contains(video.getId()));
      if(userView.getTotalLikes().containsKey(video.getId())) {
        videoView.setTotalLikes(userView.getTotalLikes().get(video.getId()));
      }
      if(userView.getTotalComments().containsKey(video.getId())) {
        videoView.setTotalComments(userView.getTotalComments().get(video.getId()));
      }
      videosView.add(videoView);
    }
    videosAdapter.setItems(videosView);
  }

  private void changeFan() {
    userView.setTotalFans(userView.getTotalFans() + (!userView.isFan() ? 1 : -1));
    userView.setFan(!userView.isFan());
    setFan();
  }

  private void setFan() {
    totalFansView.setText(String.valueOf(userView.getTotalFans()));
    if(!userView.isFan()) {
      becomeFanButton.setVisibility(View.VISIBLE);
      removeFanButton.setVisibility(View.GONE);
      fanIconView.setImageResource(R.drawable.no_fan);
    } else {
      becomeFanButton.setVisibility(View.GONE);
      removeFanButton.setVisibility(View.VISIBLE);
      fanIconView.setImageResource(R.drawable.fan);
    }
  }

  private void setFanProgress(Promise.Progress progress) {
    if(Promise.Progress.PENDING.equals(progress)) {
      loadingFanProgressView.start();
      becomeFanButton.setEnabled(false);
      removeFanButton.setEnabled(false);
    } else if(Promise.Progress.PROCESSED.equals(progress)) {
      loadingFanProgressView.stop();
      becomeFanButton.setEnabled(true);
      removeFanButton.setEnabled(true);
    }
  }

  @OnClick(R.id.become_fan)
  void onBecomeFanClick() {
    changeFan();
    remoteService.getUserService().becomeFan(userView.getUser()).then(new DoneCallback<Void>() {
      @Override
      public void onDone(Void result) {
        // Do nothing, method was executed with success.
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error becoming a user fan", e);
        UIUtils.alert(rootView, e, getString(R.string.alert_error_becoming_user_fan));
        changeFan();
      }
    }).progress(new ProgressCallback<Promise.Progress>() {
      @Override
      public void onProgress(Promise.Progress progress) {
        setFanProgress(progress);
      }
    });
  }

  @OnClick(R.id.remove_fan)
  void onRemoveFanClick() {
    changeFan();
    remoteService.getUserService().removeFan(userView.getUser()).then(new DoneCallback<Void>() {
      @Override
      public void onDone(Void result) {
        // Do nothing, method was executed with success.
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error removing user as a fan", e);
        UIUtils.alert(rootView, e, getString(R.string.alert_error_removing_user_fan));
        changeFan();
      }
    }).progress(new ProgressCallback<Promise.Progress>() {
      @Override
      public void onProgress(Promise.Progress progress) {
        setFanProgress(progress);
      }
    });
  }
}