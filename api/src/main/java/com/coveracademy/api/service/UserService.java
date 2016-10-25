package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.model.User;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.builder.GetBuilder;

import org.jdeferred.Promise;

import java.util.HashMap;
import java.util.Map;

public class UserService extends RestService {

  UserService(Context context) {
    super(context, "/users");
  }

  public DefaultPromise<User> authenticate(String accessToken) {
    GetBuilder builder = getRequestBuilderFactory().get(null);
    builder.concatPath("/auth/facebook");
    builder.addParam("access_token", accessToken);
    return new RequestPromise<>(builder);
  }
}