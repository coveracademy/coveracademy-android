package com.coveracademy.api.service;

import android.content.Context;

public class ContestService extends RestService {

  ContestService(Context context) {
    super(context, "/contests");
  }
}