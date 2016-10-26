package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.enumeration.Progress;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.AuthorizationManager;
import com.coveracademy.api.service.rest.builder.GetBuilder;
import com.coveracademy.api.service.rest.builder.PostBuilder;
import com.coveracademy.api.service.rest.builder.request.json.Types;

import org.jdeferred.DoneCallback;
import org.jdeferred.DonePipe;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise;

import java.util.HashMap;
import java.util.Map;

public class UserService extends RestService {

  private Context context;

  UserService(Context context) {
    super(context, "/users");
    this.context = context;
  }

  public DefaultPromise<User> authenticate(String accessToken) {
    final DefaultPromise<User> promise = new DefaultPromise<>();
    GetBuilder builder = getRequestBuilderFactory().get(Types.mapOfStrings());
    builder.concatPath("/auth/facebook");
    builder.addParam("access_token", accessToken);
    new RequestPromise<Map<String, String>>(builder).then(new DoneCallback<Map<String, String>>() {
      @Override
      public void onDone(Map<String, String> result) {
        String token = result.get("token");
        AuthorizationManager.getInstance(context).setToken(token);
        promise.resolve(getAuthenticatedUser());
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException exception) {
        promise.reject(exception);
      }
    });
    return promise;
  }

  public RequestPromise<User> getAuthenticatedUser() {
    GetBuilder builder = getRequestBuilderFactory().get(User.class);
    builder.concatPath("/authenticated");
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<Void> logout() {
    final DefaultPromise<Void> promise = new DefaultPromise<>();
    PostBuilder builder = getRequestBuilderFactory().post(null);
    builder.concatPath("/auth/logout");
    new RequestPromise<Void>(builder).then(new DoneCallback<Void>() {
      @Override
      public void onDone(Void result) {
        AuthorizationManager.getInstance(context).setToken(null);
        promise.resolve();
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException exception) {
        promise.reject(exception);
      }
    });
    return promise;
  }
}