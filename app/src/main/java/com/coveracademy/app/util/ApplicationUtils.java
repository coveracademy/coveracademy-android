package com.coveracademy.app.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.TypedValue;

import com.coveracademy.app.CoverAcademyApplication;

public class ApplicationUtils {

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

}