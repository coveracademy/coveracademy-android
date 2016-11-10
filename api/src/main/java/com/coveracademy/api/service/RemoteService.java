package com.coveracademy.api.service;

import android.content.Context;

public class RemoteService {

  private static RemoteService instance;
  private ViewService viewService;
  private UserService userService;
  private VideoService videoService;
  private ContestService contestService;

  private RemoteService(Context context) {
    viewService = new ViewService(context);
    userService = new UserService(context);
    videoService = new VideoService(context);
    contestService = new ContestService(context);
  }

  public static RemoteService getInstance(Context context) {
    if(instance == null) {
      instance = new RemoteService(context);
    }
    return instance;
  }

  public ViewService getViewService() {
    return viewService;
  }

  public UserService getUserService() {
    return userService;
  }

  public VideoService getVideoService() {
    return videoService;
  }

  public ContestService getContestService() {
    return contestService;
  }
}