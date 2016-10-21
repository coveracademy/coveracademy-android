package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.service.rest.builder.RequestBuilderFactory;

class RestService {

  private RequestBuilderFactory requestBuilderFactory;

  RestService(Context context, String path) {
    requestBuilderFactory = new RequestBuilderFactory(context, path);
  }

  RequestBuilderFactory getRequestBuilderFactory() {
    return requestBuilderFactory;
  }
}