package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.api.promise.Promise;
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

  public Promise<User> authenticate(String accessToken) {
    final Promise<User> promise = new Promise<>();
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

  public Promise<Void> logout() {
    final Promise<Void> promise = new Promise<>();
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

  public Promise<Void> becomeFan(User user) {
    Request<Void> request = getRequestFactory().post();
    request.concatPath(user.getId());
    request.concatPath("fans");
    return new RequestPromise<>(request);
  }

  public Promise<Void> removeFan(User user) {
    Request<Void> request = getRequestFactory().delete();
    request.concatPath(user.getId());
    request.concatPath("fans");
    return new RequestPromise<>(request);
  }

}