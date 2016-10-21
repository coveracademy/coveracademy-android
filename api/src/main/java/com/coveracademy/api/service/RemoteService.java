package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.service.rest.RequestQueue;

public class RemoteService {

  private static RemoteService instance;
  private UserService userService;
  private ContestService contestService;
  private ViewService viewService;

  private RemoteService(Context context) {
    userService = new UserService(context);
    contestService = new ContestService(context);
    viewService = new ViewService(context);
  }

  public static RemoteService getInstance(Context context) {
    if(instance == null) {
      RequestQueue.useContext(context);
      instance = new RemoteService(context);
    }
    return instance;
  }

  public ContestService getContestService() {
    return contestService;
  }

  public UserService getUserService() {
    return userService;
  }

  public ViewService getViewService() {
    return viewService;
  }
}