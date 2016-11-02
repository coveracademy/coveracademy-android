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
import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.promise.Progress;
import com.coveracademy.app.R;
import com.coveracademy.app.constant.Constants;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import life.knowledge4.videotrimmer.utils.FileUtils;

public class JoinContestActivity extends CoverAcademyActivity {

  private JoinContestActivity instance;
  private List<Contest> contests;
  private Contest selectedContest;

  @BindView(R.id.select_contest) View selectContestView;
  @BindView(R.id.selected_contest) View selectedContestView;
  @BindView(R.id.contest_name) TextView contestNameView;
  @BindView(R.id.contest_image) ImageView contestImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_join_contest);
    ButterKnife.bind(this);

    contests = new ArrayList<>();
    instance = this;

    setupContests();

    UIUtils.defaultToolbar(this);
    setTitle(getString(R.string.activity_title_join_contest));
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

  @OnClick(R.id.select_video)
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

  private void setupContests() {
    remoteService.getViewService().joinContestView().then(new DoneCallback<List<Contest>>() {
      @Override
      public void onDone(List<Contest> contests) {
        instance.contests = contests;
        if(contests.isEmpty()) {
          // No contests available
          // Show some view
        } else if(contests.size() == 1) {
          selectedContest = contests.get(0);
          setupContestView();
        } else {
          setupContestsAdapter();
        }
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {

      }
    }).progress(new ProgressCallback<Progress>() {
      @Override
      public void onProgress(Progress progress) {
        if(progress.equals(Progress.PENDING)) {
          UIUtils.showProgressBar(instance);
        } else {
          UIUtils.hideProgressBar(instance);
        }
      }
    });
  }

  private void setupContestView() {
    contestNameView.setText(selectedContest.getName());
    MediaUtils.setImage(this, selectedContest, contestImageView);
    selectContestView.setVisibility(View.GONE);
    selectedContestView.setVisibility(View.VISIBLE);
  }

  private void setupContestsAdapter() {
    selectContestView.setVisibility(View.VISIBLE);
    selectedContestView.setVisibility(View.GONE);
  }
}