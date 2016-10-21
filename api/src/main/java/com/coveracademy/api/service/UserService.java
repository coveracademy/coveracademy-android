package com.coveracademy.api.service;

import android.content.Context;

public class UserService extends RestService {

  UserService(Context context) {
    super(context, "/users");
  }
}