package com.coveracademy.app.activity;

import android.os.Bundle;
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
  @BindView(R.id.user_name) TextView userNameView;
  @BindView(R.id.user_avatar) ImageView userAvatarView;
  @BindView(R.id.total_auditions) TextView totalAuditionsView;
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
        userNameView.setText(userView.getUser().getFirstName());
        MediaUtils.setPicture(instance, userView.getUser(), userAvatarView);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading contests view", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_user_alert_error_loading_user));
      }
    });
  }
}