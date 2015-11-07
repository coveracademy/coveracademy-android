package com.coveracademy.api.service;

import com.coveracademy.api.model.view.AuditionView;
import com.coveracademy.api.model.view.ContestView;
import com.coveracademy.api.model.view.UserView;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.builder.GetBuilder;
import com.coveracademy.api.service.rest.builder.request.json.Types;

import java.util.List;

/**
 * Created by sandro on 5/28/15.
 */
public class ViewService extends RestService {

  public ViewService() {
    super("/views");
  }

  public DefaultPromise<List<AuditionView>> auditionsView() {
    GetBuilder builder = getRequestBuilderFactory().get(Types.listOfAuditionView());
    builder.concatPath("/auditions");
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<List<ContestView>> contestsView() {
    GetBuilder builder = getRequestBuilderFactory().get(Types.listOfContestView());
    builder.concatPath("/contests");
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<UserView> userView(long userId) {
    GetBuilder builder = getRequestBuilderFactory().get(UserView.class);
    builder.concatPath("/users/").concatPath(userId);
    return new RequestPromise<>(builder);
  }
}