package com.coveracademy.app.authentication;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.coveracademy.api.enumeration.Progress;
import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.service.RemoteService;
import com.coveracademy.app.constant.Errors;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.io.IOException;

public class AccountManager {

  private static AccountManager instance;

  private android.accounts.AccountManager accountManager;

  private AccountManager(Context context) {
    accountManager = android.accounts.AccountManager.get(context);
  }

  public static AccountManager getInstance(Context context) {
    if(instance == null) {
      instance = new AccountManager(context);
    }
    return instance;
  }

  public boolean hasAccount() {
    Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
    return accounts.length > 0;
  }
}