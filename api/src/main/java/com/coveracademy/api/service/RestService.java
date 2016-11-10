package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.service.rest.RequestFactory;

class RestService {

  private Context context;
  private RequestFactory requestFactory;

  RestService(Context context, String path) {
    this.context = context;
    this.requestFactory = new RequestFactory(context, path);
  }

  public Context getContext() {
    return context;
  }

  public RequestFactory getRequestFactory() {
    return requestFactory;
  }
}