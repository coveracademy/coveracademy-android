package com.coveracademy.app;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.coveracademy.api.model.User;

import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.okhttp.OkHttpStack;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class CoverAcademyApplication extends MultiDexApplication {

  private static final String TAG = CoverAcademyApplication.class.getSimpleName();

  private User user;

  @Override
  public void onCreate() {
    super.onCreate();
    setup();

    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.HOURS).writeTimeout(1, TimeUnit.HOURS).readTimeout(1, TimeUnit.HOURS).build();
    UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
    UploadService.HTTP_STACK = new OkHttpStack(okHttpClient);
  }

  @Override
  public void onTrimMemory(int level) {
    super.onTrimMemory(level);
    if(level >= TRIM_MEMORY_MODERATE) {
      Log.i(TAG, "The memory level TRIM_MEMORY_MODERATE was reached. Cleaning all cache data...");
      clearAll();
    }
  }

  private void setup() {

  }

  private void clearAll() {

  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}