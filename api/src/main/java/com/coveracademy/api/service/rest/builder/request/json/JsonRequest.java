package com.coveracademy.api.service.rest.builder.request.json;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wesley on 23/04/15.
 */
public class JsonRequest extends Request<Object> {

  private static final String DEFAULT_CHARSET = "UTF-8";
  private static final String DEFAULT_BODY_CONTENT_TYPE = "application/json; charset=UTF-8";
  private static final int DEFAULT_TIMEOUT = 10000;

  private final Type responseType;
  private Map<String, String> headers;
  private Response.Listener<Object> listener;
  private Gson gson;
  private byte[] body;

  public JsonRequest(String url, Type responseType, int method, Response.Listener<Object> listener, Response.ErrorListener errorListener) {
    super(method, url, errorListener);
    this.responseType = responseType;
    this.listener = listener;
    this.gson = GsonFactory.create();
    this.headers = new HashMap<>();
    setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
  }

  public void setRequestTag(Object requestTag) {
    setTag(requestTag);
  }

  public void addHeader(String key, String value) {
    headers.put(key, value);
  }

  public void addAllHeaders(Map<String, String> headers) {
    this.headers.putAll(headers);
  }

  @Override
  public byte[] getBody() throws AuthFailureError {
    return body;
  }

  public void setBody(Object body) {
    if(body != null) {
      this.body = gson.toJson(body).getBytes();
    }
  }

  @Override
  public String getBodyContentType() {
    return DEFAULT_BODY_CONTENT_TYPE;
  }

  @Override
  public Map<String, String> getHeaders() {
    return headers;
  }

  @Override
  protected void deliverResponse(Object response) {
    listener.onResponse(response);
  }

  @Override
  protected Response<Object> parseNetworkResponse(NetworkResponse response) {
    try {
      String json = new String(response.data, DEFAULT_CHARSET);
      Object result = responseType != null ? gson.fromJson(json, responseType) : null;
      return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    } catch(UnsupportedEncodingException e) {
      return Response.error(new ParseError(e));
    } catch(JsonSyntaxException e) {
      return Response.error(new ParseError(e));
    }
  }
}