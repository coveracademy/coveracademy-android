package com.coveracademy.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.TypedValue;

import com.coveracademy.CoverAcademyApplication;

/**
 * Created by sandro on 15/10/15.
 */
public class ApplicationUtils {

  private ApplicationUtils() {

  }

  public static CoverAcademyApplication getApplication(Context context) {
    return (CoverAcademyApplication) context.getApplicationContext();
  }

  public static CoverAcademyApplication getApplication(Fragment fragment) {
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
