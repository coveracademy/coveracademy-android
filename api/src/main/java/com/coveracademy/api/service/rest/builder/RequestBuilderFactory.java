package com.coveracademy.api.service.rest.builder;

import com.coveracademy.api.config.Configuration;

import java.lang.reflect.Type;


/**
 * Created by wesley on 23/04/15.
 */
public class RequestBuilderFactory {

  private String path;

  public RequestBuilderFactory(String path) {
    this.path = path;
  }

  public GetBuilder get(Type responseType) {
    GetBuilder builder = new GetBuilder(responseType);
    builder.withUrl(fullUrl());
    return builder;
  }

  private String fullUrl() {
    return Configuration.API_URL + path;
  }

  public DeleteBuilder delete() {
    return delete(null);
  }

  public DeleteBuilder delete(Type responseType) {
    DeleteBuilder builder = new DeleteBuilder(responseType);
    builder.withUrl(fullUrl());
    return builder;
  }

  public PostBuilder post(Object body) {
    return post(body, null);
  }

  public PostBuilder post(Object body, Type responseType) {
    PostBuilder builder = new PostBuilder(body, responseType);
    builder.withUrl(fullUrl());
    return builder;
  }

  public PutBuilder put() {
    return put(null);
  }

  public PutBuilder put(Type responseType) {
    return put(null, responseType, null);
  }

  public PutBuilder put(Object body, Type responseType) {
    return put(body, responseType, null);
  }


  public PutBuilder put(Object body, Type responseType, Object requestTag) {
    PutBuilder builder = new PutBuilder(body, responseType, requestTag);
    builder.withUrl(fullUrl());
    return builder;
  }

  public PutBuilder put(Object body) {
    return put(body, null, null);
  }

}
