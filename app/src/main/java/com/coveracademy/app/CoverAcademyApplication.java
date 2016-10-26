package com.coveracademy.app;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

public class CoverAcademyApplication extends MultiDexApplication {

  private static final String TAG = CoverAcademyApplication.class.getSimpleName();

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

  }
}