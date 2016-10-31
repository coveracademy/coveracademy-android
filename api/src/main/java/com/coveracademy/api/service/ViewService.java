package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.model.Comment;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.model.view.VideoView;
import com.coveracademy.api.model.view.ContestsItemView;
import com.coveracademy.api.model.view.ContestView;
import com.coveracademy.api.model.view.UserView;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.builder.GetBuilder;
import com.coveracademy.api.service.rest.builder.request.json.Types;

import java.util.List;

public class ViewService extends RestService {

  ViewService(Context context) {
    super(context, "/views");
  }

  public DefaultPromise<UserView> userView(long userId) {
    GetBuilder builder = getRequestBuilderFactory().get(UserView.class);
    builder.concatPath("/users/").concatPath(userId);
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<List<VideoView>> auditionsView() {
    GetBuilder builder = getRequestBuilderFactory().get(Types.listOfVideoView());
    builder.concatPath("/auditions");
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<List<ContestsItemView>> contestsView() {
    GetBuilder builder = getRequestBuilderFactory().get(Types.listOfContestView());
    builder.concatPath("/contests");
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<ContestView> contestView(long contestId) {
    GetBuilder builder = getRequestBuilderFactory().get(ContestView.class);
    builder.concatPath("/contests/").concatPath(contestId);
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<List<Comment>> commentsView(Video video) {
    GetBuilder builder = getRequestBuilderFactory().get(Types.listOfComments());
    builder.concatPath("/videos/").concatPath(video.getId()).concatPath("/comments");
    return new RequestPromise<>(builder);
  }
}