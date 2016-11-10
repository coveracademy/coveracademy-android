package com.coveracademy.api.exception;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Response;

public class RequestException extends Exception {

  public static final int TIMEOUT_ERROR = 1;
  public static final int SERVER_ERROR = 2;
  public static final int AUTH_ERROR = 3;
  public static final int NETWORK_ERROR = 4;
  public static final int UNKNOWN_ERROR = 5;

  private int code;
  private Response response;

  private RequestException(int code, Response response, String message) {
    this(code, response, message, null);
  }

  private RequestException(int code, String message, Throwable cause) {
    this(code, null, message, cause);
  }

  private RequestException(int code, Response response, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
    this.response = response;
  }

  public static RequestException parse(IOException e) {
    if(e instanceof SocketTimeoutException) {
      return new RequestException(TIMEOUT_ERROR, "Socket timeout", e);
    } else {
      return new RequestException(UNKNOWN_ERROR, "Unknown error", e);
    }
  }

  public static RequestException parse(Response response) {
    if(response.code() == 401) {
      return new RequestException(AUTH_ERROR, response, "Unauthorized");
    } else if(response.code() == 403) {
      return new RequestException(AUTH_ERROR, response, "Forbidden");
    } else if(response.code() >= 500 && response.code() <= 599) {
      return new RequestException(SERVER_ERROR, response, "Internal error");
    } else {
      return new RequestException(UNKNOWN_ERROR, response, "Unknown error");
    }
  }

  public int getCode() {
    return code;
  }

  public Response getResponse() {
    return response;
  }
}