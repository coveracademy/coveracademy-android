package com.coveracademy.app;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.coveracademy.api.model.User;

public class CoverAcademyApplication extends MultiDexApplication {

  private static final String TAG = CoverAcademyApplication.class.getSimpleName();

  private User user;

  @Override
  public void onCreate() {
    super.onCreate();
    setup();
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
    user = null;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}