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

import com.coveracademy.api.promise.Progress;
import com.coveracademy.app.R;
import com.coveracademy.app.activity.ContestActivity;
import com.coveracademy.app.activity.UserActivity;
import com.coveracademy.app.adapter.ContestsAdapter;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.view.ContestsItemView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContestsFragment extends StatefulFragment implements ContestsAdapter.OnContestClickListener {

  private static final String TAG = ContestsFragment.class.getSimpleName();

  private RemoteService remoteService;
  private ContestsAdapter contestsAdapter;

  @BindView(R.id.root) View rootView;
  @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
  @BindView(R.id.contests) RecyclerView contestsView;

  @Override
  public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_contests, container, false);
    ButterKnife.bind(this, view);

    remoteService = RemoteService.getInstance(getContext());

    setupContestsAdapter();
    setupContestsView();
    setupRefreshLayout();

    return view;
  }

  private void setupRefreshLayout() {
    UIUtils.defaultSwipeRefreshLayout(refreshLayout);
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        setupContestsView();
      }
    });
  }

  private void setupContestsAdapter() {
    contestsAdapter = new ContestsAdapter(getContext());
    contestsAdapter.setOnContestClickListener(this);
    contestsView.setLayoutManager(new LinearLayoutManager(getContext()));
    contestsView.setAdapter(contestsAdapter);
  }

  private void setupContestsView() {
    remoteService.getViewService().contestsView().then(new DoneCallback<List<ContestsItemView>>() {
      @Override
      public void onDone(List<ContestsItemView> contestsView) {
        contestsAdapter.setItems(contestsView);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading contests view", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_main_alert_error_loading_contests));
      }
    }).progress(new ProgressCallback<Progress>() {
      @Override
      public void onProgress(Progress progress) {
        if(progress.equals(Progress.PENDING)) {
          UIUtils.showProgressBar(getCreatedView());
        } else {
          UIUtils.hideProgressBar(getCreatedView());
          refreshLayout.setRefreshing(false);
        }
      }
    });
  }

  @Override
  public void onContestClick(Contest contest) {
    Intent intent = new Intent(getContext(), ContestActivity.class);
    intent.putExtra(ContestActivity.CONTEST_ID, contest.getId());
    startActivity(intent);
  }

  @Override
  public void onWinnerClick(User user) {
    Intent intent = new Intent(getContext(), UserActivity.class);
    intent.putExtra(UserActivity.USER_ID, user.getId());
    startActivity(intent);
  }
}