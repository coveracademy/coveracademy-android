package com.coveracademy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.coveracademy.CoverAcademyApplication;
import com.coveracademy.R;
import com.coveracademy.adapter.AuditionsAdapter;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.view.AuditionsView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.util.ApplicationUtils;
import com.coveracademy.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private MainActivity instance;
  private RemoteService remoteService;
  private CoverAcademyApplication application;
  private AuditionsAdapter auditionsAdapter;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.auditions) RecyclerView auditionsView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    instance = this;
    remoteService = RemoteService.getInstance(this);
    application = ApplicationUtils.getApplication(this);

    setupToolbar();
    setupContestView();
    setTitle(getString(R.string.auditions));
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
  }

  private void setupAuditions() {
    auditionsAdapter = new AuditionsAdapter(this);
    auditionsView.setLayoutManager(new LinearLayoutManager(this));
    auditionsView.setAdapter(auditionsAdapter);
  }

  private void setupContestView() {
    remoteService.getViewService().auditionsView().then(new DoneCallback<AuditionsView>() {
      @Override
      public void onDone(AuditionsView auditionsView) {
        application.addContests(auditionsView.getContests());
        application.addUsers(auditionsView.getUsers());
        application.setAuditions(auditionsView.getAuditions());
        setupAuditions();
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading contest view", e);
      }
    }).progress(new ProgressCallback<Promise.State>() {
      @Override
      public void onProgress(Promise.State progress) {
        if(progress.equals(Promise.State.PENDING))  {
          UIUtils.showProgressBar(instance);
        } else {
          UIUtils.hideProgressBar(instance);
        }
      }
    });
  }
}
