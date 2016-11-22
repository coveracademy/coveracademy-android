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

import com.coveracademy.api.promise.Promise;
import com.coveracademy.app.R;
import com.coveracademy.app.activity.EnterContestActivity;
import com.coveracademy.app.adapter.VideosAdapter;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.view.VideoView;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuditionsFragment extends StatefulFragment {

  private static final String TAG = AuditionsFragment.class.getSimpleName();

  private RemoteService remoteService;
  private VideosAdapter videosAdapter;

  @BindView(R.id.coordinator_layout) View coordinatorLayout;
  @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
  @BindView(R.id.videos) RecyclerView videosView;

  @Override
  public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_auditions, container, false);
    ButterKnife.bind(this, view);

    remoteService = RemoteService.getInstance(getContext());

    setupVideosAdapter();
    setupVideosView();
    setupRefreshLayout();

    return view;
  }

  private void setupRefreshLayout() {
    UIUtils.defaultSwipeRefreshLayout(refreshLayout);
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        setupVideosView();
      }
    });
  }

  private void setupVideosAdapter() {
    videosAdapter = new VideosAdapter(getContext());
    videosView.setLayoutManager(new LinearLayoutManager(getContext()));
    videosView.setAdapter(videosAdapter);
  }

  private void setupVideosView() {
    remoteService.getViewService().auditionsView().then(new DoneCallback<List<VideoView>>() {
      @Override
      public void onDone(List<VideoView> auditionsView) {
        videosAdapter.setItems(auditionsView);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading auditions view", e);
        UIUtils.alert(coordinatorLayout, e, getString(R.string.activity_main_alert_error_loading_auditions));
      }
    }).progress(new ProgressCallback<Promise.Progress>() {
      @Override
      public void onProgress(Promise.Progress progress) {
        if(progress.equals(Promise.Progress.PENDING)) {
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
    Intent joinContestIntent = new Intent(getContext(), EnterContestActivity.class);
    startActivity(joinContestIntent);
  }
}