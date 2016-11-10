package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.model.Comment;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.model.view.VideoView;
import com.coveracademy.api.model.view.ContestsItemView;
import com.coveracademy.api.model.view.ContestView;
import com.coveracademy.api.model.view.UserView;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.Request;
import com.coveracademy.api.service.rest.json.Types;

import java.util.List;

public class ViewService extends RestService {

  ViewService(Context context) {
    super(context, "/views");
  }

  public DefaultPromise<UserView> userView(long userId) {
    Request<UserView> request = getRequestFactory().get(UserView.class);
    request.concatPath("users");
    request.concatPath(userId);
    return new RequestPromise<>(request);
  }

  public DefaultPromise<List<VideoView>> auditionsView() {
    Request<List<VideoView>> request = getRequestFactory().get(Types.listOfVideoView());
    request.concatPath("auditions");
    return new RequestPromise<>(request);
  }

  public DefaultPromise<List<ContestsItemView>> contestsView() {
    Request<List<ContestsItemView>> request = getRequestFactory().get(Types.listOfContestView());
    request.concatPath("contests");
    return new RequestPromise<>(request);
  }

  public DefaultPromise<ContestView> contestView(long contestId) {
    Request<ContestView> request = getRequestFactory().get(ContestView.class);
    request.concatPath("contests");
    request.concatPath(contestId);
    return new RequestPromise<>(request);
  }

  public DefaultPromise<List<Comment>> commentsView(Video video) {
    Request<List<Comment>> request = getRequestFactory().get(Types.listOfComments());
    request.concatPath("videos");
    request.concatPath(video.getId());
    request.concatPath("comments");
    return new RequestPromise<>(request);
  }


  public DefaultPromise<List<Contest>> joinContestView() {
    Request<List<Contest>> request = getRequestFactory().get(Types.listOfContests());
    request.concatPath("contests");
    request.concatPath("join");
    return new RequestPromise<>(request);
  }
}