package com.coveracademy.app.activity;

import android.content.Intent;
import android.os.Bundle;

import com.coveracademy.app.authentication.AccountManager;

public class SplashActivity extends CoverAcademyActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = new Intent(this, AccountManager.getInstance(this).hasAccount() ? MainActivity.class : WelcomeActivity.class);
    startActivity(intent);
    finish();
  }
}