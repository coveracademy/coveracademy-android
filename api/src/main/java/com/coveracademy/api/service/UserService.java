package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.Request;
import com.coveracademy.api.service.rest.json.Types;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import java.util.Map;

public class UserService extends RestService {

  private Context context;

  UserService(Context context) {
    super(context, "/users");
    this.context = context;
  }

  public DefaultPromise<User> authenticate(String accessToken) {
    final DefaultPromise<User> promise = new DefaultPromise<>();
    Request<Map<String, String>> request = getRequestFactory().get(Types.mapOfStrings());
    request.concatPath("auth");
    request.concatPath("facebook");
    request.addParameter("access_token", accessToken);
    new RequestPromise<>(request).then(new DoneCallback<Map<String, String>>() {
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
    Request<User> request = getRequestFactory().get(User.class);
    request.concatPath("authenticated");
    return new RequestPromise<>(request);
  }

  public DefaultPromise<Void> logout() {
    final DefaultPromise<Void> promise = new DefaultPromise<>();
    Request<Void> request = getRequestFactory().post(null);
    request.concatPath("auth");
    request.concatPath("logout");
    new RequestPromise<>(request).then(new DoneCallback<Void>() {
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

  public DefaultPromise<Void> fan(User user) {
    Request<Void> request = getRequestFactory().post();
    request.concatPath(user.getId());
    request.concatPath("fans");
    return new RequestPromise<>(request);
  }

  public DefaultPromise<Void> unfan(User user) {
    Request<Void> request = getRequestFactory().delete();
    request.concatPath(user.getId());
    request.concatPath("fans");
    return new RequestPromise<>(request);
  }

}