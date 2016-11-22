package com.coveracademy.api.promise;

import android.os.Handler;
import android.os.Looper;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.exception.RequestException;
import com.coveracademy.api.service.rest.Request;

public class RequestPromise<T> extends Promise<T> {

  private static final String DEFAULT_RESPONSE_HEADER = "X-CoverAcademy";

  private Request<T> request;
  private Handler mainHandler;

  public RequestPromise(Request<T> request) {
    this.request = request;
    mainHandler = new Handler(Looper.getMainLooper());
    execute();
  }

  private void execute() {
    Request.ResponseCallback<T> callback = new Request.ResponseCallback<T>() {
      @Override
      public void onFailure(RequestException e) {
        APIException apiException;
        try {
          switch(e.getCode()) {
            case RequestException.SERVER_ERROR:
              apiException = APIException.fromJson(e.getResponse().body().string());
              break;
            case RequestException.AUTH_ERROR:
              apiException = new APIException();
              apiException.setError(e.getMessage());
              if(e.getResponse().headers().get(DEFAULT_RESPONSE_HEADER) != null) {
                apiException.setKey(APIException.AUTHENTICATION_ERROR);
              } else {
                apiException.setKey(APIException.NO_INTERNET_ACCESS);
              }
              break;
            case RequestException.TIMEOUT_ERROR:
              apiException = new APIException();
              apiException.setError(e.getMessage());
              apiException.setKey(APIException.CONNECTION_TIMEOUT);
              break;
            case RequestException.NETWORK_ERROR:
              apiException = new APIException();
              apiException.setError(e.getMessage());
              apiException.setKey(APIException.NETWORK_ERROR);
              break;
            default:
              apiException = new APIException();
              apiException.setError(e.getMessage());
              apiException.setKey(APIException.UNKNOWN_ERROR);
          }
        } catch(Exception ex) {
          apiException = new APIException();
          apiException.setError(e.getMessage());
          apiException.setKey(APIException.UNKNOWN_ERROR);
        }
        final APIException finalException = apiException;
        mainHandler.post(new Runnable() {
          @Override
          public void run() {
            reject(finalException);
          }
        });
      }

      @Override
      public void onResponse(final T response) {
        mainHandler.post(new Runnable() {
          @Override
          public void run() {
            resolve(response);
          }
        });
      }
    };
    request.setCallback(callback);
    request.execute();
  }
}