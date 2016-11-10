package com.coveracademy.api.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthorizationManager {

  private static final String TOKEN = "token";
  private static AuthorizationManager authorizationManager;

  private SharedPreferences preferences;

  private AuthorizationManager(Context context) {
    this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  public static AuthorizationManager getInstance(Context context) {
    if(authorizationManager == null) {
      authorizationManager = new AuthorizationManager(context);
    }
    return authorizationManager;
  }

  public void setToken(String token) {
    if(token != null) {
      preferences.edit().putString(TOKEN, token).apply();
    } else {
      preferences.edit().remove(TOKEN).apply();
    }
  }

  public String getToken() {
    return preferences.getString(TOKEN, null);
  }
}