package com.coveracademy.app.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.coveracademy.api.config.Configuration;
import com.coveracademy.api.model.Video;
import com.coveracademy.app.CoverAcademyApplication;
import com.coveracademy.app.constant.Constants;

import java.util.List;

public class ApplicationUtils {

  private static final String GOOGLE_PLAY_URI = "https://play.google.com/store/apps/details?id=";
  private static final String GOOGLE_PLAY_PACKAGE_NAME = "com.android.vending";

  private ApplicationUtils() {

  }

  public static CoverAcademyApplication getCoverAcademyApplication(Context context) {
    return (CoverAcademyApplication) context.getApplicationContext();
  }

  public static CoverAcademyApplication getCoverAcademyApplication(Fragment fragment) {
    return (CoverAcademyApplication) fragment.getActivity().getApplicationContext();
  }

  public static int getAttributeResourceId(Context context, int attribute) {
    if(attribute == 0) {
      return 0;
    }
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(attribute, typedValue, true);
    return typedValue.resourceId;
  }

  public static String getGooglePlayUri(Context context) {
    return GOOGLE_PLAY_URI + context.getPackageName();
  }

  public static Intent createGooglePlayIntent(Context context) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getGooglePlayUri(context)));
    PackageManager packageManager = context.getPackageManager();
    List<ApplicationInfo> packages = packageManager.getInstalledApplications(0);
    for(ApplicationInfo packageInfo : packages) {
      if(GOOGLE_PLAY_PACKAGE_NAME.equals(packageInfo.packageName)) {
        intent.setPackage(GOOGLE_PLAY_PACKAGE_NAME);
      }
    }
    return intent;
  }

  public static String getVideoUrl(Video video) {
    return Constants.WEBSITE_URL + "/videos/" + video.getId();
  }
}