package com.coveracademy.api.service.rest;

import android.content.Context;

import com.coveracademy.api.BuildConfig;
import com.coveracademy.api.exception.RequestException;
import com.coveracademy.api.service.AuthorizationManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class Request<T> implements Callback {

  private static final Long DEFAULT_TIMEOUT = 30000L;

  private static final String HEADER_VERSION = "X-Version";
  private static final String HEADER_TOKEN = "X-Token";
  private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
  private static final String HEADER_ENCODING_GZIP = "gzip";

  private Context context;
  private HttpMethod method;
  private StringBuilder url;
  private String body;
  private String tag;
  private Long timeout;
  private Map<String, Object> parameters;
  private Map<String, String> headers;
  private ResponseCallback<T> callback;

  private Call call;

  public Request(Context context) {
    this.context = context;
    this.url = new StringBuilder();
    this.parameters = new HashMap<>();
    this.headers = new HashMap<>();
    this.method = HttpMethod.GET;
    this.timeout = DEFAULT_TIMEOUT;
  }

  abstract MediaType getMediaType();

  abstract T convertResponse(Response response) throws IOException;

  void setDefaultHeaders() {
    headers.put(HEADER_ACCEPT_ENCODING, HEADER_ENCODING_GZIP);
    headers.put(HEADER_VERSION, String.valueOf(BuildConfig.VERSION_CODE));
    headers.put(HEADER_TOKEN, AuthorizationManager.getInstance(context).getToken());
  }

  public Context getContext() {
    return context;
  }

  public String getUrl() {
    return url.toString();
  }

  public void setUrl(String url) {
    this.url.append(url);
  }

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Long getTimeout() {
    return timeout;
  }

  public void setTimeout(Long timeout) {
    this.timeout = timeout;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public ResponseCallback<T> getCallback() {
    return callback;
  }

  public void setCallback(ResponseCallback<T> callback) {
    this.callback = callback;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void addParameter(String name, Object value) {
    parameters.put(name, value.toString());
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void addHeader(String name, String value) {
    headers.put(name, value);
  }

  public void concatPath(Object path) {
    concatPath(String.valueOf(path));
  }

  public void concatPath(String path) {
    this.url.append("/").append(path);
  }

  private void encodeParameter(String key, String value) throws UnsupportedEncodingException {
    url.append(URLEncoder.encode(key, "UTF-8"));
    url.append('=');
    url.append(URLEncoder.encode(value, "UTF-8"));
    url.append('&');
  }

  private String getFullUrl() {
    if(!parameters.isEmpty()) {
      url.append("?");
      for(Map.Entry<String, Object> entry : parameters.entrySet()) {
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

  public void execute() {
    setDefaultHeaders();

    okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
    requestBuilder.url(getFullUrl());
    for(Map.Entry<String, String> header : headers.entrySet()) {
      if(header.getKey() != null && header.getValue() != null) {
        requestBuilder.header(header.getKey(), header.getValue());
      }
    }
    if(body == null && (HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method))) {
      body = "";
    }
    requestBuilder.method(method.toString(), body != null ? RequestBody.create(getMediaType(), body) : null);
    requestBuilder.tag(tag);

    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    clientBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
    clientBuilder.readTimeout(timeout, TimeUnit.SECONDS);
    clientBuilder.writeTimeout(timeout, TimeUnit.SECONDS);
    OkHttpClient client = clientBuilder.build();
    call = client.newCall(requestBuilder.build());
    call.enqueue(this);
  }

  public void cancel() {
    if(call != null) {
      call.cancel();
    }
  }

  @Override
  public void onFailure(Call call, IOException e) {
    if(callback != null) {
      callback.onFailure(RequestException.parse(e));
    }
  }

  @Override
  public void onResponse(Call call, Response response) {
    if(callback != null) {
      if(response.isSuccessful()) {
        try {
          callback.onResponse(convertResponse(response));
        } catch(IOException e) {
          callback.onFailure(RequestException.parse(e));
        }
      } else {
        callback.onFailure(RequestException.parse(response));
      }
    }
  }

  public interface ResponseCallback<T> {

    void onFailure(RequestException e);

    void onResponse(T response);

  }
}