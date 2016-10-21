package com.coveracademy.api.service.rest.builder;

import android.content.Context;

import com.android.volley.Response;
import com.coveracademy.api.BuildConfig;
import com.coveracademy.api.service.rest.AuthorizationManager;
import com.coveracademy.api.service.rest.RequestQueue;
import com.coveracademy.api.service.rest.builder.request.json.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class RequestBuilder<T> {

  private static final String HEADER_VERSION = "X-Version";
  private static final String HEADER_TOKEN = "X-Token";

  protected Context context;
  protected Integer method;
  protected Integer timeout;
  protected StringBuilder url;
  protected Type responseType;
  protected Map<String, Object> params;
  protected Map<String, String> headers;
  protected Response.Listener<Object> listener;
  protected Response.ErrorListener errorListener;

  public RequestBuilder(Context context, int method, Type responseType) {
    this.context = context;
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

  public T addParam(String name, Object value) {
    params.put(name, value);
    return getThis();
  }

  public T addDateParam(String name, Date date) {
    params.put(name, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date));
    return getThis();
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  public T withListener(final Response.Listener<Object> listener) {
    this.listener = new Response.Listener<Object>() {
      @Override
      public void onResponse(Object response) {
        listener.onResponse(response);
      }
    };
    return getThis();
  }

  public T withErrorListener(Response.ErrorListener errorListener) {
    this.errorListener = errorListener;
    return getThis();
  }

  private void encodeParameter(String key, String value) throws UnsupportedEncodingException {
    url.append(URLEncoder.encode(key, "UTF-8"));
    url.append('=');
    url.append(URLEncoder.encode(value, "UTF-8"));
    url.append('&');
  }

  protected String getFullUrl() {
    if(!params.isEmpty()) {
      url.append("?");
      for(Map.Entry<String, Object> entry : params.entrySet()) {
        try {
          String key = entry.getKey();
          Object value = entry.getValue();
          if(value instanceof Collection) {
            for(Object valueItem : (Collection) entry.getValue()) {
              encodeParameter(key, valueItem.toString());
            }
          } else {
            encodeParameter(key, value.toString());
          }
        } catch(UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      }
      url.setLength(url.length() - 1);
    }
    return url.toString();
  }

  protected JsonRequest createRequest(String url) {
    JsonRequest request = new JsonRequest(url, responseType, method, listener, errorListener);
    request.addAllHeaders(headers);
    if(timeout != null) {
      request.setTimeout(timeout);
    }
    return request;
  }

  protected void fillRequest(JsonRequest request) {

  }

  public void execute() {
    String url = getFullUrl();
    JsonRequest request = createRequest(url);
    fillRequest(request);
    AuthorizationManager authorizationManager = AuthorizationManager.getInstance(context);
    String token = authorizationManager.getToken();
    if(token != null) {
      request.addHeader(HEADER_TOKEN, token);
    }
    request.addHeader(HEADER_VERSION, BuildConfig.VERSION_CODE);
    RequestQueue.getInstance().push(request);
  }
}