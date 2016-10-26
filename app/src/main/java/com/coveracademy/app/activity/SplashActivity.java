package com.coveracademy.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.AccessToken;

public class SplashActivity extends CoverAcademyActivity {

  private static final int SPLASH_DELAY = 300;

  private SplashActivity instance;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    instance = this;

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent intent = new Intent(instance, AccessToken.getCurrentAccessToken() != null ? MainActivity.class : WelcomeActivity.class);
        startActivity(intent);
        finish();
      }
    }, SPLASH_DELAY);
  }
}