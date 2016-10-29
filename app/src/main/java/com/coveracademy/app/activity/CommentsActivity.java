package com.coveracademy.app.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.Comment;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.promise.Progress;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.R;
import com.coveracademy.app.adapter.CommentsAdapter;
import com.coveracademy.app.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsActivity extends CoverAcademyActivity {

  public static final String VIDEO_ID = "VIDEO_ID";

  private static final String TAG = ContestActivity.class.getSimpleName();

  private CommentsActivity instance;
  private RemoteService remoteService;
  private CommentsAdapter commentsAdapter;

  @BindView(R.id.comments) RecyclerView commentsView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_comments);
    ButterKnife.bind(this);

    instance = this;
    remoteService = RemoteService.getInstance(this);

    setupCommentsAdapter();
    setupCommentsView();

    UIUtils.defaultToolbar(this);
    setTitle(getString(R.string.activity_title_comments));
  }

  private void setupCommentsAdapter() {
    commentsAdapter = new CommentsAdapter(this);
    commentsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
    commentsView.setAdapter(commentsAdapter);
  }

  private void setupCommentsView() {
    long videoId = getIntent().getLongExtra(VIDEO_ID, -1L);
    if(videoId == -1L) {
      finish();
      return;
    }
    Video video = new Video();
    video.setId(videoId);
    remoteService.getViewService().commentsView(video).then(new DoneCallback<List<Comment>>() {
      @Override
      public void onDone(List<Comment> comments) {
        commentsAdapter.setItems(comments);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException result) {

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
}
