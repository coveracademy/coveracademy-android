package com.coveracademy.api.service.rest.builder;

import com.android.volley.Request;
import com.coveracademy.api.service.rest.builder.request.json.JsonRequest;

import java.lang.reflect.Type;

/**
 * Created by wesley on 26/04/15.
 */
public class PutBuilder extends RequestBuilder<PutBuilder> {

  private Object body;
  private Object requestTag;

  public PutBuilder(Object body, Type responseType, Object requestTag) {
    super(Request.Method.PUT, responseType);
    this.body = body;
    this.requestTag = requestTag;
  }

  @Override
  public PutBuilder getThis() {
    return this;
  }

  @Override
  protected void fillRequest(JsonRequest request) {
    request.setBody(body);
    request.setRequestTag(requestTag);
  }

}