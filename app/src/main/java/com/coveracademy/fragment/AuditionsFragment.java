package com.coveracademy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coveracademy.CoverAcademyApplication;
import com.coveracademy.R;
import com.coveracademy.activity.JoinContestActivity;
import com.coveracademy.activity.UserActivity;
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

/**
 * Created by sandro on 29/10/15.
 */
public class AuditionsFragment extends StatefulFragment implements AuditionsAdapter.OnUserClickListener {

  private static final String TAG = AuditionsFragment.class.getSimpleName();

  private RemoteService remoteService;
  private CoverAcademyApplication application;
  private AuditionsAdapter auditionsAdapter;

  @Bind(R.id.coordinator_layout) View coordinatorLayout;
  @Bind(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
  @Bind(R.id.auditions) RecyclerView auditionsView;

  @Override
  public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_auditions, container, false);
    ButterKnife.bind(this, view);

    application = ApplicationUtils.getApplication(this);
    remoteService = RemoteService.getInstance(getContext());

    setupAuditionsAdapter();
    setupAuditionsView();
    setupRefreshLayout();

    return view;
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
    auditionsAdapter = new AuditionsAdapter(getContext());
    auditionsAdapter.setOnUserClickListener(this);
    auditionsView.setLayoutManager(new LinearLayoutManager(getContext()));
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
          UIUtils.showProgressBar(getCreatedView());
        } else {
          UIUtils.hideProgressBar(getCreatedView());
          refreshLayout.setRefreshing(false);
        }
      }
    });
  }

  @OnClick(R.id.join_contest)
  void onJoinContestClick() {
    Intent joinContestIntent = new Intent(getContext(), JoinContestActivity.class);
    startActivity(joinContestIntent);
  }

  @Override
  public void onUserClick(Long userId) {
    Intent intent = new Intent(getContext(), UserActivity.class);
    intent.putExtra(UserActivity.USER_ID, userId);
    startActivity(intent);
  }
}
