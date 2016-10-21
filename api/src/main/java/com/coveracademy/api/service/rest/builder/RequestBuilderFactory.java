package com.coveracademy.api.service.rest.builder;

import android.content.Context;
import android.graphics.Bitmap;

import com.coveracademy.api.config.Configuration;

import java.lang.reflect.Type;

public class RequestBuilderFactory {

  private String path;
  private Context context;

  public RequestBuilderFactory(Context context, String path) {
    this.context = context;
    this.path = path;
  }

  public GetBuilder get(Type responseType) {
    GetBuilder builder = new GetBuilder(context, responseType);
    builder.withUrl(fullUrl());
    return builder;
  }

  private String fullUrl() {
    return Configuration.API_URL + path;
  }

  public PostBuilder post() {
    return post(null);
  }

  public PostBuilder post(Object body) {
    return post(body, null);
  }

  public PostBuilder post(Object body, Type responseType) {
    PostBuilder builder = new PostBuilder(context, body, responseType);
    builder.withUrl(fullUrl());
    return builder;
  }

  public UploadAvatarBuilder post(Bitmap image, Type responseType, Object requestTag) {
    return post(null, image, responseType, requestTag);
  }

  public UploadAvatarBuilder post(Object fields, Bitmap image, Type responseType, Object requestTag) {
    UploadAvatarBuilder builder = new UploadAvatarBuilder(context, fields, image, responseType, requestTag);
    builder.withUrl(fullUrl());
    return builder;
  }

  public PutBuilder put() {
    return put(null);
  }

  public PutBuilder put(Type responseType) {
    return put(null, responseType, null);
  }

  public PutBuilder put(Object body) {
    return put(body, null, null);
  }

  public PutBuilder put(Object body, Type responseType) {
    return put(body, responseType, null);
  }

  public PutBuilder put(Object body, Type responseType, Object requestTag) {
    PutBuilder builder = new PutBuilder(context, body, responseType, requestTag);
    builder.withUrl(fullUrl());
    return builder;
  }

  public DeleteBuilder delete() {
    return delete(null);
  }

  public DeleteBuilder delete(Type responseType) {
    return delete(responseType, null);
  }

  public DeleteBuilder delete(Type responseType, Object requestTag) {
    DeleteBuilder builder = new DeleteBuilder(context, responseType, requestTag);
    builder.withUrl(fullUrl());
    return builder;
  }
}