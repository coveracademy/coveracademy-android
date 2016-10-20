package com.coveracademy.app.util;

import android.content.Context;
import android.util.TypedValue;

public class ApplicationUtils {

  private ApplicationUtils() {

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