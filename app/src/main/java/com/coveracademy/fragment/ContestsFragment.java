package com.coveracademy.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coveracademy.R;
import com.coveracademy.adapter.ContestsAdapter;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.view.ContestView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sandro on 06/11/15.
 */
public class ContestsFragment extends StatefulFragment {

  private static final String TAG = ContestsFragment.class.getSimpleName();

  private RemoteService remoteService;
  private ContestsAdapter contestsAdapter;

  @Bind(R.id.root) View rootView;
  @Bind(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
  @Bind(R.id.contests) RecyclerView contestsView;

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
    contestsView.setLayoutManager(new LinearLayoutManager(getContext()));
    contestsView.setAdapter(contestsAdapter);
  }

  private void setupContestsView() {
    remoteService.getViewService().contestsView().then(new DoneCallback<List<ContestView>>() {
      @Override
      public void onDone(List<ContestView> contestsView) {
        contestsAdapter.setItems(contestsView);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading contests view", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_main_alert_error_loading_contests));
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
}
