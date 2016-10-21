package com.coveracademy.api.service.rest.builder;

import android.content.Context;

import com.android.volley.Request;

import java.lang.reflect.Type;

public class GetBuilder extends RequestBuilder<GetBuilder> {

  public GetBuilder(Context context, Type responseType) {
    super(context, Request.Method.GET, responseType);
  }

  @Override
  public GetBuilder getThis() {
    return this;
  }
}