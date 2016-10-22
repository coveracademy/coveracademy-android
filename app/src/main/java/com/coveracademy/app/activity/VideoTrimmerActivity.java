package com.coveracademy.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.coveracademy.app.R;
import com.coveracademy.app.util.MediaUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;


public class VideoTrimmerActivity extends AppCompatActivity implements OnTrimVideoListener {

  public static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
  public static final String EXTRA_TRIM_VIDEO_PATH = "EXTRA_TRIM_VIDEO_PATH";

  @BindView(R.id.video_trimmer) K4LVideoTrimmer videoTrimmer;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_trimmer);
    ButterKnife.bind(this);
    Intent intent = getIntent();
    if(intent != null) {
      String path = intent.getStringExtra(EXTRA_VIDEO_PATH);
      videoTrimmer.setVideoURI(Uri.parse(path));
      videoTrimmer.setMaxDuration(30);
      videoTrimmer.setDestinationPath(MediaUtils.getTemporaryDirectory() + File.separator);
      videoTrimmer.setOnTrimVideoListener(this);
    }
  }

  @Override
  public void getResult(Uri uri) {
    Intent intent = new Intent();
    intent.putExtra(EXTRA_TRIM_VIDEO_PATH, uri);
    setResult(RESULT_OK, intent);
    finish();
  }

  @Override
  public void cancelAction() {
    finish();
  }
}