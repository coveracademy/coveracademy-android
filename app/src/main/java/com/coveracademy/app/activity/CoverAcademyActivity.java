package com.coveracademy.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.CoverAcademyApplication;
import com.coveracademy.app.util.ApplicationUtils;
import com.facebook.FacebookSdk;

public class CoverAcademyActivity extends AppCompatActivity {

  protected RemoteService remoteService;
  protected CoverAcademyApplication application;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FacebookSdk.sdkInitialize(this);
    remoteService = RemoteService.getInstance(this);
    application = ApplicationUtils.getCoverAcademyApplication(this);
  }
}