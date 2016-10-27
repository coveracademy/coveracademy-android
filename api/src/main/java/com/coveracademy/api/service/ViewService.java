package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.model.view.AuditionView;
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

  public DefaultPromise<List<AuditionView>> auditionsView() {
    GetBuilder builder = getRequestBuilderFactory().get(Types.listOfAuditionView());
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
}