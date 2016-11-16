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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.promise.Progress;
import com.coveracademy.app.R;
import com.coveracademy.app.constant.Constants;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.UIUtils;
import com.coveracademy.app.util.component.ContestCountDownTimer;

import net.gotev.uploadservice.UploadNotificationConfig;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import life.knowledge4.videotrimmer.utils.FileUtils;

public class EnterContestActivity extends CoverAcademyActivity {

  private static String TAG = EnterContestActivity.class.getSimpleName();

  private EnterContestActivity instance;
  private List<Contest> contests;
  private Contest selectedContest;
  private Uri selectedVideoUri;

  @BindView(R.id.root) View rootView;
  @BindView(R.id.select_contest) View selectContestView;
  @BindView(R.id.selected_contest) View selectedContestView;
  @BindView(R.id.selected_video) View selectedVideoView;
  @BindView(R.id.uploading_video) View uploadingVideoView;
  @BindView(R.id.contest_name) TextView contestNameView;
  @BindView(R.id.contest_image) ImageView contestImageView;
  @BindView(R.id.video) VideoView videoView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_enter_contest);
    ButterKnife.bind(this);

    contests = new ArrayList<>();
    instance = this;

    setupPermissions();
    setupContests();

    UIUtils.defaultToolbar(this, new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackClick();
      }
    });
    setTitle(getString(R.string.activity_title_enter_contest));
  }

  private void setupPermissions() {
    List<String> permissions = new ArrayList<>();
    if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
      permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    if(!permissions.isEmpty()) {
      ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), 0);
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
          setupContestsView();
        }
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading join contest view", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_enter_contest_alert_error_loading));
        finish();
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

  private void setupContestsView() {
    selectContestView.setVisibility(View.VISIBLE);
    selectedContestView.setVisibility(View.GONE);
    selectedVideoView.setVisibility(View.GONE);
    uploadingVideoView.setVisibility(View.GONE);
  }

  private void setupContestView() {
    videoView.suspend();

    contestNameView.setText(selectedContest.getName());
    MediaUtils.setImage(this, selectedContest, contestImageView);
    new ContestCountDownTimer(this, selectedContest, rootView).start();

    selectContestView.setVisibility(View.GONE);
    selectedContestView.setVisibility(View.VISIBLE);
    selectedVideoView.setVisibility(View.GONE);
    uploadingVideoView.setVisibility(View.GONE);
  }

  private void setupSubmitVideoView() {
    videoView.setVideoPath(selectedVideoUri.getPath());
    videoView.start();
    selectContestView.setVisibility(View.GONE);
    selectedContestView.setVisibility(View.GONE);
    selectedVideoView.setVisibility(View.VISIBLE);
    uploadingVideoView.setVisibility(View.GONE);
  }

  private void setupUploadingVideoView() {
    videoView.suspend();
    selectContestView.setVisibility(View.GONE);
    selectedContestView.setVisibility(View.GONE);
    selectedVideoView.setVisibility(View.GONE);
    uploadingVideoView.setVisibility(View.VISIBLE);
  }

  private void onBackClick() {
    if(selectedVideoView.getVisibility() == View.VISIBLE) {
      setupContestView();
    } else if(selectedContestView.getVisibility() == View.VISIBLE && contests.size() > 1) {
      setupContestsView();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void onBackPressed() {
    onBackClick();
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
            startActivityForResult(intent, Constants.REQUEST_TRIM_VIDEO);
          }
          break;
        case Constants.REQUEST_TRIM_VIDEO:
          uri = data.getData();
          if(uri != null) {
            selectedVideoUri = uri;
            setupSubmitVideoView();
          }
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
    }
  }

  @OnClick(R.id.select_video)
  void onSelectVideoClick() {
    MediaUtils.createDirectories();
    MediaUtils.selectVideo(this);
  }

  @OnClick(R.id.submit_video)
  void onSubmitVideoClick() {
    setupUploadingVideoView();
    UploadNotificationConfig uploadNotificationConfig = new UploadNotificationConfig();
    uploadNotificationConfig.setTitle(getString(R.string.activity_enter_contest_uploading_video));
    uploadNotificationConfig.setCompletedMessage(getString(R.string.activity_enter_contest_video_uploaded));
    remoteService.getVideoService().upload(selectedVideoUri, selectedContest, uploadNotificationConfig).then(new DoneCallback<String>() {
      @Override
      public void onDone(String uploadId) {
        // Do nothing
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error uploading video", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_enter_contest_alert_error_uploading_video));
      }
    });
  }

  @OnClick(R.id.close)
  void onCloseClick() {
    finish();
  }
}