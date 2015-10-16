package com.coveracademy.api.service.rest.builder;

import com.android.volley.Request;
import com.coveracademy.api.service.rest.builder.request.json.JsonRequest;

import java.lang.reflect.Type;

/**
 * Created by wesley on 23/04/15.
 */
public class PostBuilder extends RequestBuilder<PostBuilder> {

  private Object body;

  public PostBuilder(Object body, Type responseType) {
    super(Request.Method.POST, responseType);
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
