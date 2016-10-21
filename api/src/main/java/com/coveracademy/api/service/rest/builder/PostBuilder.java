package com.coveracademy.api.service.rest.builder;

import android.content.Context;

import com.android.volley.Request;
import com.coveracademy.api.service.rest.builder.request.json.JsonRequest;

import java.lang.reflect.Type;

public class PostBuilder extends RequestBuilder<PostBuilder> {

  private Object body;

  public PostBuilder(Context context, Object body, Type responseType) {
    super(context, Request.Method.POST, responseType);
    this.body = body;
  }

  @Override
  public PostBuilder getThis() {
    return this;
  }

  @Override
  protected void fillRequest(JsonRequest request) {
    request.setBody(body);
  }
}