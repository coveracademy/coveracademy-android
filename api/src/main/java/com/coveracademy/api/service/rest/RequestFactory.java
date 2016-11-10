package com.coveracademy.api.service.rest;

import android.content.Context;
import android.net.Uri;

import com.coveracademy.api.config.Configuration;

import java.lang.reflect.Type;

public class RequestFactory {

  private String path;
  private Context context;

  public RequestFactory(Context context, String path) {
    this.context = context;
    this.path = path;
  }

  private String getBaseUrl() {
    return Configuration.API_URL + path;
  }

  public <T> Request<T> get(Type responseType) {
    JsonRequest<T> request = new JsonRequest<>(context, responseType);
    request.setMethod(HttpMethod.GET);
    request.setUrl(getBaseUrl());
    return request;
  }

  public <T> Request<T> post() {
    return post(null);
  }

  public <T> Request<T> post(Object body) {
    return post(body, null);
  }

  public <T> Request<T> post(Object body, Type responseType) {
    JsonRequest<T> request = new JsonRequest<>(context, responseType);
    request.setMethod(HttpMethod.POST);
    request.setUrl(getBaseUrl());
    request.setBody(body);
    return request;
  }

  public <T> Request<T> put() {
    return put(null, null);
  }

  public <T> Request<T> put(Object body) {
    return put(body, null);
  }

  public <T> Request<T> put(Object body, Type responseType) {
    JsonRequest<T> request = new JsonRequest<>(context, responseType);
    request.setMethod(HttpMethod.PUT);
    request.setUrl(getBaseUrl());
    request.setBody(body);
    return request;
  }

  public <T> Request<T> delete() {
    return delete(null);
  }

  public <T> Request<T> delete(Type responseType) {
    JsonRequest<T> request = new JsonRequest<>(context, responseType);
    request.setMethod(HttpMethod.DELETE);
    request.setUrl(getBaseUrl());
    return request;
  }

  public MultipartRequest upload(Uri uri) {
    MultipartRequest request = new MultipartRequest(context, uri);
    request.setMethod(HttpMethod.POST);
    request.setUrl(getBaseUrl());
    return request;
  }
}