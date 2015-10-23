package com.coveracademy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private MainActivity instance;
  private RemoteService remoteService;
  private CoverAcademyApplication application;
  private AuditionsAdapter auditionsAdapter;

  @Bind(R.id.coordinator_layout) View coordinatorLayout;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.auditions) RecyclerView auditionsView;
  @Bind(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    instance = this;
    remoteService = RemoteService.getInstance(this);
    application = ApplicationUtils.getApplication(this);

    setupToolbar();
    setupAuditionsAdapter();
    setupAuditionsView();
    setupRefreshLayout();
    setTitle(getString(R.string.activity_main_title));
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
  }

  private void setupRefreshLayout() {
    UIUtils.defaultSwipeRefreshLayout(refreshLayout);
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        setupAuditionsView();
      }
    });
  }

  private void setupAuditionsAdapter() {
    auditionsAdapter = new AuditionsAdapter(this);
    auditionsView.setLayoutManager(new LinearLayoutManager(this));
    auditionsView.setAdapter(auditionsAdapter);
  }

  private void setupAuditionsView() {
    remoteService.getViewService().auditionsView().then(new DoneCallback<AuditionsView>() {
      @Override
      public void onDone(AuditionsView auditionsView) {
        application.addContests(auditionsView.getContests());
        application.addUsers(auditionsView.getUsers());
        application.setAuditions(auditionsView.getAuditions());
        application.setTotalVotes(auditionsView.getTotalVotes());
        application.setTotalComments(auditionsView.getTotalComments());
        auditionsAdapter.reloadItems();
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading auditions view", e);
        UIUtils.alert(coordinatorLayout, e, getString(R.string.activity_main_alert_error_loading_auditions));
      }
    }).progress(new ProgressCallback<Promise.State>() {
      @Override
      public void onProgress(Promise.State progress) {
        if(progress.equals(Promise.State.PENDING)) {
          UIUtils.showProgressBar(instance);
        } else {
          UIUtils.hideProgressBar(instance);
          refreshLayout.setRefreshing(false);
        }
      }
    });
  }

  @OnClick(R.id.join_contest)
  void onJoinContestClick() {
    Intent joinContestIntent = new Intent(this, JoinContestActivity.class);
    startActivity(joinContestIntent);
  }
}
