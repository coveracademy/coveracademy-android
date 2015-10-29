package com.coveracademy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.coveracademy.R;

/**
 * Created by sandro on 29/10/15.
 */
public class UserActivity extends AppCompatActivity {

  public static final String USER_ID = "USER_ID";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);
  }
}