package com.coveracademy.api.service.rest.builder;

import android.content.Context;

import com.android.volley.Request;
import com.coveracademy.api.service.rest.builder.request.json.JsonRequest;

import java.lang.reflect.Type;

public class DeleteBuilder extends RequestBuilder<DeleteBuilder> {

  private Object requestTag;

  public DeleteBuilder(Context context, Type responseType, Object requestTag) {
    super(context, Request.Method.DELETE, responseType);
    this.requestTag = requestTag;
  }

  @Override
  public DeleteBuilder getThis() {
    return this;
  }

  @Override
  protected void fillRequest(JsonRequest request) {
    request.setRequestTag(requestTag);
  }
}