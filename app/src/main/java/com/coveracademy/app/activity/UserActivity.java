package com.coveracademy.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.app.R;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.UserView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends CoverAcademyActivity {

  public static final String USER_ID = "USER_ID";

  private static final String TAG = UserActivity.class.getSimpleName();

  private UserActivity instance;
  private RemoteService remoteService;

  private User user;

  @BindView(R.id.root) View rootView;
  @BindView(R.id.picture) ImageView pictureView;
  @BindView(R.id.name) TextView nameView;
  @BindView(R.id.biography) TextView biographyView;
  @BindView(R.id.total_videos) TextView totalVideosView;
  @BindView(R.id.total_fans) TextView totalFansView;
  @BindView(R.id.total_idols) TextView totalIdolsView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);
    ButterKnife.bind(this);

    instance = this;
    remoteService = RemoteService.getInstance(this);

    setupUserView();
    setupRefreshLayout();

    UIUtils.defaultToolbar(this);
    setTitle("");
  }

  private void setupRefreshLayout() {

  }

  private void setupUserView() {
    long userId = getIntent().getLongExtra(USER_ID, -1L);
    if(userId == -1L) {
      finish();
      return;
    }
    remoteService.getViewService().userView(userId).then(new DoneCallback<UserView>() {
      @Override
      public void onDone(UserView userView) {
        setupUser(userView);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading user view", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_user_alert_error_loading_user));
      }
    });
  }

  private void setupUser(UserView userView) {
    User user = userView.getUser();
    setTitle(user.getName());
    MediaUtils.setPicture(instance, user, pictureView);
    nameView.setText(user.getName());
    totalVideosView.setText(String.valueOf(userView.getTotalVideos()));
    totalFansView.setText(String.valueOf(userView.getTotalFans()));
    totalIdolsView.setText(String.valueOf(userView.getTotalIdols()));
    if(!TextUtils.isEmpty(user.getBiography())) {
      biographyView.setText(user.getBiography());
      biographyView.setVisibility(View.VISIBLE);
    } else {
      biographyView.setText("");
      biographyView.setVisibility(View.GONE);
    }
  }
}