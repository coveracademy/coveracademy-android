package com.coveracademy.app.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.Comment;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.promise.Progress;
import com.coveracademy.app.R;
import com.coveracademy.app.adapter.CommentsAdapter;
import com.coveracademy.app.util.UIUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CommentsActivity extends CoverAcademyActivity {

  public static final String VIDEO_ID = "VIDEO_ID";

  private static final String TAG = CommentsActivity.class.getSimpleName();

  private Video video;
  private CommentsActivity instance;
  private CommentsAdapter commentsAdapter;

  @BindView(R.id.root) View rootView;
  @BindView(R.id.comments) RecyclerView commentsView;
  @BindView(R.id.send_message) View sendMessageView;
  @BindView(R.id.message) TextView messageInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_comments);
    ButterKnife.bind(this);

    instance = this;

    setupVideo();
    setupCommentsAdapter();
    setupCommentsView();

    UIUtils.defaultToolbar(this);
    setTitle(getString(R.string.activity_title_comments));
  }

  private void setupVideo() {
    video = new Video();
    video.setId(getIntent().getLongExtra(VIDEO_ID, -1L));
    if(video.getId() == -1L) {
      finish();
    }
  }

  private void setupCommentsAdapter() {
    commentsAdapter = new CommentsAdapter(this);
    commentsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
    commentsView.setAdapter(commentsAdapter);
  }

  private void setupCommentsView() {
    sendMessageView.setEnabled(false);
    remoteService.getViewService().commentsView(video).then(new DoneCallback<List<Comment>>() {
      @Override
      public void onDone(List<Comment> comments) {
        commentsAdapter.setItems(comments);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error loading comments", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_comments_alert_error_loading_comments));
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

  @OnTextChanged(R.id.message)
  void onMessageChanged(CharSequence message) {
    if(message.toString().trim().isEmpty()) {
      sendMessageView.setEnabled(false);
    } else {
      sendMessageView.setEnabled(true);
    }
  }

  @OnClick(R.id.send_message)
  void onSendMessageClick() {
    final Comment comment = new Comment();
    comment.setVideoId(video.getId());
    comment.setMessage(messageInput.getText().toString());
    comment.setStatus(Comment.Status.SENDING);
    comment.setSendDate(new Date());
    comment.setUser(application.getUser());

    messageInput.setText("");
    commentsView.scrollToPosition(0);
    commentsAdapter.addItem(0, comment);
    remoteService.getVideoService().comment(video, comment.getMessage()).then(new DoneCallback<Comment>() {
      @Override
      public void onDone(Comment sentComment) {
        comment.setId(sentComment.getId());
        comment.setSendDate(sentComment.getSendDate());
        comment.setStatus(Comment.Status.SENT);
        commentsAdapter.reloadItem(0);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException e) {
        Log.e(TAG, "Error sending comment", e);
        UIUtils.alert(rootView, e, getString(R.string.activity_comments_alert_error_sending_comment));
        comment.setStatus(Comment.Status.ERROR_SENDING);
        commentsAdapter.reloadItem(0);
      }
    });
  }
}