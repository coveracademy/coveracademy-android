package com.coveracademy.api.exception;

import android.util.Log;

import com.coveracademy.api.service.rest.builder.request.json.GsonFactory;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

public class APIException extends RuntimeException {

  private static final String TAG = APIException.class.getSimpleName();

  public static final String NO_INTERNET_CONNECTION = "internet.noConnection";
  public static final String NO_INTERNET_ACCESS = "internet.noAccess";
  public static final String AUTHENTICATION_ERROR = "authentication.error";
  public static final String UNKNOWN_ERROR = "unknown.error";
  public static final String CONNECTION_TIMEOUT = "timeout.error";
  public static final String NETWORK_ERROR = "network.error";

  private Integer status;
  private String key;
  private String error;
  @SerializedName("cause") private String errorCause;

  public APIException() {

  }

  public APIException(Throwable cause) {
    super(cause);
  }

  public static APIException fromJson(String json) {
    try {
      return GsonFactory.create().fromJson(json, APIException.class);
    } catch(JsonParseException e) {
      Log.e(TAG, "Error parsing error from backend", e);
      APIException apiException = new APIException(e);
      apiException.setKey(UNKNOWN_ERROR);
      return apiException;
    }
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getErrorCause() {
    return errorCause;
  }

  public void setErrorCause(String errorCause) {
    this.errorCause = errorCause;
  }
}