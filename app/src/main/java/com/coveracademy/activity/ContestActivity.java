package com.coveracademy.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.R;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.view.ContestView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.util.ImageUtils;
import com.coveracademy.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sandro on 11/13/15.
 */
public class ContestActivity extends AppCompatActivity {

  private static final String TAG = ContestActivity.class.getSimpleName();
  public static final String CONTEST_ID = "CONTEST_ID";

  private ContestActivity instance;
  private RemoteService remoteService;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.contest_name) TextView contestNameView;
  @Bind(R.id.contest_image) ImageView contestImageView;
  @Bind(R.id.total_auditions) TextView totalAuditionsView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contest);
    ButterKnife.bind(this);

    instance = this;
    remoteService = RemoteService.getInstance(this);

    setupContestView();

    UIUtils.defaultToolbar(this);
    toolbar.setTitle("");
    toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.transparent));
  }

  private void setupContestView() {
    long contestId = getIntent().getLongExtra(CONTEST_ID, -1L);
    if(contestId == -1L) {
      finish();
      return;
    }
    remoteService.getViewService().contestView(contestId).then(new DoneCallback<ContestView>() {
      @Override
      public void onDone(ContestView contestView) {
        contestNameView.setText(contestView.getContest().getName());
        totalAuditionsView.setText(getString(R.string.total_auditions, contestView.getTotalAuditions()));
        ImageUtils.setImage(instance, contestView.getContest(), contestImageView);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading contests view", e);
        UIUtils.alert(instance, e, getString(R.string.activity_contest_alert_error_loading_contest));
        finish();
      }
    });
  }
}