package com.coveracademy.api.service.rest.builder.request.json;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class JsonRequest extends Request<Object> {

  private static final String DEFAULT_CHARSET = "UTF-8";
  private static final String DEFAULT_BODY_CONTENT_TYPE = "application/json; charset=UTF-8";
  private static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
  private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
  private static final String HEADER_ENCODING_GZIP = "gzip";
  private static final int DEFAULT_TIMEOUT = 30000;
  private static final int DEFAULT_MAX_RETRIES = 0;

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
    this.headers.put(HEADER_ACCEPT_ENCODING, HEADER_ENCODING_GZIP);
    setTimeout(DEFAULT_TIMEOUT);
  }

  public void setRequestTag(Object requestTag) {
    setTag(requestTag);
  }

  public void setTimeout(int timeout) {
    setRetryPolicy(new DefaultRetryPolicy(timeout, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
  }

  public void addHeader(String key, Object value) {
    headers.put(key, value.toString());
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
      Object result = null;
      if(responseType != null) {
        byte[] responseData = isGzipped(response) ? decompressGzip(response.data) : response.data;
        String json = new String(responseData, DEFAULT_CHARSET);
        result = !TextUtils.isEmpty(json) ? gson.fromJson(json, responseType) : null;
      }
      return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    } catch(UnsupportedEncodingException e) {
      return Response.error(new ParseError(e));
    } catch(JsonSyntaxException e) {
      return Response.error(new ParseError(e));
    }
  }

  private boolean isGzipped(NetworkResponse response) {
    Map<String, String> headers = response.headers;
    return headers != null && !headers.isEmpty() && headers.containsKey(HEADER_CONTENT_ENCODING) && headers.get(HEADER_CONTENT_ENCODING).contains(HEADER_ENCODING_GZIP);
  }

  private byte[] decompressGzip(byte[] compressed) throws UnsupportedEncodingException {
    GZIPInputStream gzipStream = null;
    ByteArrayOutputStream outStream = null;
    try {
      gzipStream = new GZIPInputStream(new ByteArrayInputStream(compressed));
      outStream = new ByteArrayOutputStream();
      int size;
      final int bufferSize = 8192;
      byte[] temporaryBuffer = new byte[bufferSize];
      while((size = gzipStream.read(temporaryBuffer, 0, bufferSize)) != -1) {
        outStream.write(temporaryBuffer, 0, size);
      }
      return outStream.toByteArray();
    } catch(IOException e) {
      throw new UnsupportedEncodingException();
    } finally {
      if(gzipStream != null) {
        try {
          gzipStream.close();
        } catch(IOException e) {
          // Ignore
        }
      }
      if(outStream != null) {
        try {
          outStream.close();
        } catch(IOException e) {
          // Ignore
        }
      }
    }
  }
}