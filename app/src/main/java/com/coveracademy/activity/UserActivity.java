package com.coveracademy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.CoverAcademyApplication;
import com.coveracademy.R;
import com.coveracademy.api.model.User;
import com.coveracademy.util.ApplicationUtils;
import com.coveracademy.util.ImageUtils;
import com.coveracademy.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sandro on 29/10/15.
 */
public class UserActivity extends AppCompatActivity {

  public static final String USER_ID = "USER_ID";

  private CoverAcademyApplication application;
  private User user;

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

    application = ApplicationUtils.getApplication(this);

    setupUser();
    UIUtils.defaultToolbar(this);
  }

  private void setupUser() {
    Long userId = getIntent().getLongExtra(USER_ID, -1L);
    if(!userId.equals(-1L)) {
      user = application.getUser(userId);
      setTitle(user.getName());
      userNameView.setText(user.getName());
      ImageUtils.setPicture(this, user, userAvatarView);
    }
  }
}