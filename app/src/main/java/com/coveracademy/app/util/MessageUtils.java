package com.coveracademy.app.util;

import android.content.Context;

import com.coveracademy.app.R;
import com.coveracademy.api.exception.APIException;

import java.util.HashMap;
import java.util.Map;

public class MessageUtils {

  private static final Map<String, Integer> messageResources = new HashMap<>();

  static {
    messageResources.put(APIException.NO_INTERNET_CONNECTION, R.string.message_no_internet_connection);
    messageResources.put(APIException.NO_INTERNET_ACCESS, R.string.message_no_internet_access);
    messageResources.put(APIException.CONNECTION_TIMEOUT, R.string.message_connection_timeout);
    messageResources.put(APIException.AUTHENTICATION_ERROR, R.string.message_authentication_error);
  }

  private MessageUtils() {

  }

  public static String getMessage(Context context, APIException apiException) {
    String message = null;
    Integer messageResource = messageResources.get(apiException.getKey());
    if(messageResource != null) {
      message = context.getString(messageResource);
    }
    return message;
  }
}
