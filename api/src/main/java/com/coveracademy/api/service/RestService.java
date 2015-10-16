package com.coveracademy.api.service;

import com.coveracademy.api.service.rest.builder.RequestBuilderFactory;

/**
 * Created by wesley on 23/04/15.
 */
public class RestService {

  private RequestBuilderFactory requestBuilderFactory;

  public RestService(String path) {
    requestBuilderFactory = new RequestBuilderFactory(path);
  }

  public RequestBuilderFactory getRequestBuilderFactory() {
    return requestBuilderFactory;
  }

}
