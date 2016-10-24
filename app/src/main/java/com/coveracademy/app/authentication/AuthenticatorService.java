package com.coveracademy.app.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticatorService extends Service {

  @Override
  public IBinder onBind(Intent intent) {
    return new AccountAuthenticator(this).getIBinder();
  }
}