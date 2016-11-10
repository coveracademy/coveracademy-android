package com.coveracademy.api.service.rest;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.coveracademy.api.service.rest.json.GsonFactory;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.Response;

public class JsonRequest<T> extends Request<T> {

  private Gson gson;
  private Type responseType;

  public JsonRequest(Context context, Type responseType) {
    super(context);
    this.gson = GsonFactory.create();
    this.responseType = responseType;
  }

  @Override
  MediaType getMediaType() {
    return MediaType.parse("application/json; charset=UTF-8");
  }

  @Override
  T convertResponse(Response response) throws IOException {
    String responseValue = response.body().string();
    return responseType != null && !TextUtils.isEmpty(responseValue) ? (T) gson.fromJson(responseValue, responseType) : null;
  }

  public void setBody(Object body) {
    if(body != null) {
      setBody(gson.toJson(body));
    }
  }

  public Type getResponseType() {
    return responseType;
  }
}