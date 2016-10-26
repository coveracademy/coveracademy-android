package com.coveracademy.app.activity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class SplashActivity extends CoverAcademyActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LoginManager.getInstance();

    Intent intent = new Intent(this, AccessToken.getCurrentAccessToken() != null ? MainActivity.class : WelcomeActivity.class);
    startActivity(intent);
    finish();
  }
}