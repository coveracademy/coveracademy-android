package com.coveracademy.api.promise;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.service.rest.builder.RequestBuilder;

public class RequestPromise<D> extends DefaultPromise<D> {

  private static final String COVERACADEMY_RESPONSE_HEADER = "X-CoverAcademy";

  private RequestBuilder builder;

  public RequestPromise(RequestBuilder<?> builder) {
    this.builder = builder;
    execute();
  }

  private void execute() {
    final Response.Listener<D> listener = new Response.Listener<D>() {
      @Override
      public void onResponse(D response) {
        resolve(response);
      }
    };
    final Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        APIException apiException;
        try {
          if(error instanceof ServerError) {
            ServerError serverError = (ServerError) error;
            String response = new String(serverError.networkResponse.data);
            if(!response.isEmpty()) {
              apiException = APIException.fromJson(new String(serverError.networkResponse.data));
            } else {
              apiException = new APIException();
              apiException.setKey(APIException.UNKNOWN_ERROR);
            }
          } else {
            apiException = new APIException();
            apiException.setError(error.getMessage());
            if(error instanceof AuthFailureError) {
              if(error.networkResponse.headers.containsKey(COVERACADEMY_RESPONSE_HEADER)) {
                apiException.setKey(APIException.AUTHENTICATION_ERROR);
              } else {
                apiException.setKey(APIException.NO_INTERNET_ACCESS);
              }
            } else if(error instanceof ParseError) {
              apiException.setKey(APIException.UNKNOWN_ERROR);
            } else if(error instanceof NoConnectionError) {
              apiException.setKey(APIException.NO_INTERNET_CONNECTION);
            } else if(error instanceof TimeoutError) {
              apiException.setKey(APIException.CONNECTION_TIMEOUT);
            } else if(error instanceof NetworkError) {
              apiException.setKey(APIException.NETWORK_ERROR);
            }
          }
        } catch(Exception e) {
          apiException = new APIException();
          apiException.setKey(APIException.UNKNOWN_ERROR);
        }
        reject(apiException);
      }
    };
    builder.withListener(listener);
    builder.withErrorListener(errorListener);
    builder.execute();
  }
}