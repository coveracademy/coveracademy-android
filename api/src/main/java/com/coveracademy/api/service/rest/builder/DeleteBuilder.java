package com.coveracademy.api.service.rest.builder;

import com.android.volley.Request;

import java.lang.reflect.Type;

/**
 * Created by sandro on 5/3/15.
 */
public class DeleteBuilder extends RequestBuilder<DeleteBuilder> {

  public DeleteBuilder(Type responseType) {
    super(Request.Method.DELETE, responseType);
  }

  @Override
  public DeleteBuilder getThis() {
    return this;
  }

}
