package com.coveracademy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.R;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.UserView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.util.ImageUtils;
import com.coveracademy.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sandro on 29/10/15.
 */
public class UserActivity extends AppCompatActivity {

  private static final String TAG = UserActivity.class.getSimpleName();
  public static final String USER_ID = "USER_ID";

  private UserActivity instance;
  private RemoteService remoteService;

  private User user;

  @Bind(R.id.root) View rootView;
  @Bind(R.id.user_name) TextView userNameView;
  @Bind(R.id.user_avatar) ImageView userAvatarView;
  @Bind(R.id.total_auditions) TextView totalAuditionsView;
  @Bind(R.id.total_fans) TextView totalFansView;
  @Bind(R.id.total_idols) TextView totalIdolsView;

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
        userNameView.setText(userView.getUser().getName());
        ImageUtils.setPhoto(instance, userView.getUser(), userAvatarView);
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