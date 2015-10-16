package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.service.rest.RequestQueue;

/**
 * Created by wesley on 08/05/15.
 */
public class RemoteService {

  private static RemoteService instance;
  private UserService userService;
  private ContestService contestService;
  private ViewService viewService;

  private RemoteService() {
    userService = new UserService();
    contestService = new ContestService();
    viewService = new ViewService();
  }

  public static RemoteService getInstance(Context context) {
    if(instance == null) {
      RequestQueue.useContext(context);
      instance = new RemoteService();
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