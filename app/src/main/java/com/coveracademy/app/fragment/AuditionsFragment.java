package com.coveracademy.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coveracademy.app.R;
import com.coveracademy.app.activity.JoinContestActivity;
import com.coveracademy.app.activity.UserActivity;
import com.coveracademy.app.adapter.AuditionsAdapter;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.AuditionView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuditionsFragment extends StatefulFragment implements AuditionsAdapter.OnUserClickListener {

  private static final String TAG = AuditionsFragment.class.getSimpleName();

  private RemoteService remoteService;
  private AuditionsAdapter auditionsAdapter;

  @BindView(R.id.coordinator_layout) View coordinatorLayout;
  @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
  @BindView(R.id.auditions) RecyclerView auditionsView;

  @Override
  public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_auditions, container, false);
    ButterKnife.bind(this, view);

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
    remoteService.getViewService().auditionsView().then(new DoneCallback<List<AuditionView>>() {
      @Override
      public void onDone(List<AuditionView> auditionsView) {
        auditionsAdapter.setItems(auditionsView);
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
  public void onUserClick(User user) {
    Intent intent = new Intent(getContext(), UserActivity.class);
    intent.putExtra(UserActivity.USER_ID, user.getId());
    startActivity(intent);
  }
}
