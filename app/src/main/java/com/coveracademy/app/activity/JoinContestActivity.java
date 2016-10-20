package com.coveracademy.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.coveracademy.app.R;
import com.coveracademy.app.util.ImageUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinContestActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_join_contest);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.join_contest)
  void onJoinContestClick() {
    ImageUtils.selectImage(this);
  }
}