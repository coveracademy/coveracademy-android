package com.coveracademy.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;

import com.coveracademy.app.R;
import com.coveracademy.app.constant.Constants;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import life.knowledge4.videotrimmer.utils.FileUtils;

public class JoinContestActivity extends CoverAcademyActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_join_contest);
    ButterKnife.bind(this);

    UIUtils.defaultToolbar(this);
    setTitle(getString(R.string.activity_title_join_contest));
  }

  @OnClick(R.id.join_contest)
  void onJoinContestClick() {
    List<String> permissions = new ArrayList<>();
    if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
      permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    if(!permissions.isEmpty()) {
      ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), 0);
    } else {
      MediaUtils.createDirectories();
      MediaUtils.selectVideo(this);
    }
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(resultCode == RESULT_OK) {
      switch(requestCode) {
        case Constants.REQUEST_SELECT_VIDEO:
          Uri uri = data.getData();
          if(uri != null) {
            Intent intent = new Intent(this, VideoTrimmerActivity.class);
            intent.putExtra(VideoTrimmerActivity.EXTRA_VIDEO_PATH, FileUtils.getPath(this, uri));
            startActivity(intent);
          }
          break;
        case Constants.REQUEST_TRIM_VIDEO:
          finish();
          break;
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    boolean denied = false;
    if(requestCode == Constants.REQUEST_SELECT_VIDEO) {
      for(int grantResult : grantResults) {
        if(grantResult == PackageManager.PERMISSION_DENIED) {
          denied = true;
          break;
        }
      }
    }
    if(denied) {
      finish();
    } else {
      MediaUtils.createDirectories();
      MediaUtils.selectVideo(this);
    }
  }
}