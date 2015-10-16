package com.coveracademy.api.service.rest.builder;

import com.android.volley.Request;

import java.lang.reflect.Type;


/**
 * Created by wesley on 23/04/15.
 */
public class GetBuilder extends RequestBuilder<GetBuilder> {

  public GetBuilder(Type responseType) {
    super(Request.Method.GET, responseType);
  }

  @Override
  public GetBuilder getThis() {
    return this;
  }

}
