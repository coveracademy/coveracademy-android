package com.coveracademy.api.service.rest.builder;

import com.android.volley.Response;
import com.coveracademy.api.service.rest.AuthorizationManager;
import com.coveracademy.api.service.rest.RequestQueue;
import com.coveracademy.api.service.rest.builder.request.json.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by wesley on 23/04/15.
 */
public abstract class RequestBuilder<T> {

  protected StringBuilder url;
  protected Integer method;
  protected Type responseType;
  protected Map<String, String> params;
  protected Map<String, String> headers;

  protected Response.Listener<Object> listener;
  protected Response.ErrorListener errorListener;

  public RequestBuilder(int method, Type responseType) {
    this.method = method;
    this.responseType = responseType;
    this.url = new StringBuilder();
    this.params = new HashMap<>();
    this.headers = new HashMap<>();
  }

  public abstract T getThis();

  public T withUrl(String url) {
    this.url.append(url);
    return getThis();
  }

  public T concatPath(Object path) {
    return concatPath(String.valueOf(path));
  }

  public T concatPath(String path) {
    this.url.append(path);
    return getThis();
  }

  public T addParam(String name, String value) {
    params.put(name, value);
    return getThis();
  }

  public T addHeader(String name, String value) {
    headers.put(name, value);
    return getThis();
  }

  public <E> T withListener(final Response.Listener<E> listener) {
    this.listener = new Response.Listener<Object>() {
      @Override
      public void onResponse(Object response) {
        listener.onResponse((E) response);
      }
    };
    return getThis();
  }

  public T withErrorListener(Response.ErrorListener errorListener) {
    this.errorListener = errorListener;
    return getThis();
  }

  protected String getFullUrl() {
    if(!params.isEmpty()) {
      url.append("?");
      try {
        for(Map.Entry<String, String> entry : params.entrySet()) {
          url.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
          url.append('=');
          url.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
          url.append('&');
        }
        url.setLength(url.length() - 1);
      } catch(UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return url.toString();
  }

  protected JsonRequest createRequest(String url) {
    JsonRequest request = new JsonRequest(url, responseType, method, listener, errorListener);
    request.addAllHeaders(headers);
    return request;
  }

  protected void fillRequest(JsonRequest request) {

  }

  public void execute() {
    String url = getFullUrl();
    JsonRequest request = createRequest(url);
    fillRequest(request);
    AuthorizationManager authorizationManager = AuthorizationManager.getInstance();
    String token = authorizationManager.getToken();
    if(token != null) {
      request.addHeader(authorizationManager.getAuthorizationHeaderKey(), token);
    }
    RequestQueue.getInstance().push(request);
  }

}
