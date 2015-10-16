package com.coveracademy.api.service.rest;

/**
 * Created by wesley on 30/04/15.
 */
public class AuthorizationManager {
  public static String AUTHORIZATION_HEADER_KEY = "x-token";

  private static AuthorizationManager authorizationManager;
  private String token;
  private String user;

  private AuthorizationManager() {

  }

  public static AuthorizationManager getInstance() {
    if(authorizationManager == null) {
      authorizationManager = new AuthorizationManager();
    }
    return authorizationManager;
  }

  public String getAuthorizationHeaderKey() {
    return AUTHORIZATION_HEADER_KEY;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}
