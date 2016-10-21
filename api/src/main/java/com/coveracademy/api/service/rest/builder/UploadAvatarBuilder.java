package com.coveracademy.api.service.rest.builder;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.coveracademy.api.service.rest.builder.request.json.JsonRequest;
import com.coveracademy.api.service.rest.builder.request.multipart.MultipartRequest;

import java.lang.reflect.Type;

public class UploadAvatarBuilder extends RequestBuilder<UploadAvatarBuilder> {

  private Object fields;
  private Bitmap bitmap;
  private Object requestTag;

  public UploadAvatarBuilder(Context context, Object fields, Bitmap bitmap, Type responseType, Object requestTag) {
    super(context, Request.Method.POST, responseType);
    this.fields = fields;
    this.bitmap = bitmap;
    this.requestTag = requestTag;
  }

  @Override
  public UploadAvatarBuilder getThis() {
    return this;
  }

  @Override
  public JsonRequest createRequest(String url) {
    MultipartRequest request = new MultipartRequest(url, fields, bitmap, responseType, errorListener, listener);
    request.addAllHeaders(headers);
    return request;
  }

  @Override
  protected void fillRequest(JsonRequest request) {
    super.fillRequest(request);
    request.setRequestTag(requestTag);
  }
}