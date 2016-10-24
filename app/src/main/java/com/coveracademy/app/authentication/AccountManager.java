//package com.coveracademy.app.auth;
//
//import android.accounts.Account;
//import android.accounts.AccountManagerCallback;
//import android.accounts.AccountManagerFuture;
//import android.accounts.AuthenticatorException;
//import android.accounts.OperationCanceledException;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//
//import org.jdeferred.DoneCallback;
//import org.jdeferred.FailCallback;
//import org.jdeferred.ProgressCallback;
//
//import java.io.IOException;
//
//public class AccountManager {
//
//  private static AccountManager instance;
//
//  private android.accounts.AccountManager accountManager;
//  private ClosicApplication closicApplication;
//  private RemoteService remoteService;
//
//  private boolean authenticating;
//
//  private AccountManager(Context context) {
//    accountManager = android.accounts.AccountManager.get(context);
//    closicApplication = ApplicationUtils.getClosicApplication(context);
//    remoteService = RemoteService.getInstance(context);
//  }
//
//  public static AccountManager getInstance(Context context) {
//    if(instance == null) {
//      instance = new AccountManager(context);
//    }
//    return instance;
//  }
//
//  public static Intent createAccountIntent(String loginType, String login, String password) {
//    Intent intent = new Intent();
//    intent.putExtra(android.accounts.AccountManager.KEY_ACCOUNT_TYPE, AuthConstants.ACCOUNT_TYPE);
//    intent.putExtra(AuthConstants.AUTH_TYPE, loginType);
//    intent.putExtra(android.accounts.AccountManager.KEY_ACCOUNT_NAME, login);
//    intent.putExtra(android.accounts.AccountManager.KEY_PASSWORD, password);
//    return intent;
//  }
//
//  private String getLoginType(Bundle bundle) {
//    return bundle.getString(AuthConstants.AUTH_TYPE);
//  }
//
//  private String getLogin(Bundle bundle) {
//    return bundle.getString(android.accounts.AccountManager.KEY_ACCOUNT_NAME);
//  }
//
//  private String getPassword(Bundle bundle) {
//    return bundle.getString(android.accounts.AccountManager.KEY_PASSWORD);
//  }
//
//  public Account addAccount(String loginType, String login, String password) {
//    Account account = new Account(login, AuthConstants.ACCOUNT_TYPE);
//    accountManager.addAccountExplicitly(account, password, null);
//    accountManager.setUserData(account, AuthConstants.AUTH_TYPE, loginType);
//    return account;
//  }
//
//  public Account getAccount() {
//    Account account = null;
//    Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
//    if(accounts.length > 0) {
//      account = accounts[0];
//    }
//    return account;
//  }
//
//  public String getAccountAuthType() {
//    String authType = null;
//    Account account = getAccount();
//    if(account != null) {
//      authType = accountManager.getUserData(account, AuthConstants.AUTH_TYPE);
//    }
//    return authType;
//  }
//
//  public DefaultPromise<User> authenticate(Context context, String login, String password) {
//    final DefaultPromise<User> promise = new DefaultPromise<>();
//    remoteService.getUserService().authenticate(login, password, ApplicationUtils.getDeviceStatus(context)).then(new DoneCallback<User>() {
//      @Override
//      public void onDone(User user) {
//        closicApplication.setUser(user);
//        promise.resolve(user);
//      }
//    }).fail(new FailCallback<APIException>() {
//      @Override
//      public void onFail(APIException e) {
//        promise.reject(e);
//      }
//    });
//    return promise;
//  }
//
//  public DefaultPromise<User> authenticate(Context context) {
//    final DefaultPromise<User> promise = new DefaultPromise<>();
//    Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
//    if(accounts.length > 0) {
//      Account account = accounts[0];
//      remoteService.getUserService().authenticate(account.name, accountManager.getPassword(account), ApplicationUtils.getDeviceStatus(context)).then(new DoneCallback<User>() {
//        @Override
//        public void onDone(User user) {
//          closicApplication.setUser(user);
//          promise.resolve(user);
//        }
//      }).fail(new FailCallback<APIException>() {
//        @Override
//        public void onFail(APIException e) {
//          promise.reject(e);
//        }
//      }).progress(new ProgressCallback<Progress>() {
//        @Override
//        public void onProgress(Progress progress) {
//          promise.notify(progress);
//        }
//      });
//    } else {
//      APIException exception = new APIException();
//      exception.setKey(Errors.AUTHENTICATION_NO_ACCOUNT);
//      promise.reject(exception);
//    }
//    return promise;
//  }
//
//  public DefaultPromise<User> ensureAuthentication(Activity activity) {
//    final DefaultPromise<User> promise = new DefaultPromise<>();
//    Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
//    if(accounts.length > 0) {
//      return authenticate(activity);
//    } else if(authenticating) {
//      APIException exception = new APIException();
//      exception.setKey(Errors.AUTHENTICATION_WAITING);
//      promise.reject(exception);
//    } else {
//      authenticating = true;
//      accountManager.addAccount(AuthConstants.ACCOUNT_TYPE, AuthConstants.TOKEN_TYPE, null, null, activity, new AccountManagerCallback<Bundle>() {
//        @Override
//        public void run(AccountManagerFuture<Bundle> future) {
//          try {
//            Bundle bundle = future.getResult();
//            addAccount(getLoginType(bundle), getLogin(bundle), getPassword(bundle));
//            promise.resolve(closicApplication.getUser());
//            authenticating = false;
//          } catch(OperationCanceledException e) {
//            APIException exception = new APIException();
//            exception.setKey(Errors.AUTHENTICATION_CANCELED);
//            promise.reject(exception);
//            authenticating = false;
//          } catch(IOException | AuthenticatorException e) {
//            APIException exception = new APIException();
//            exception.setKey(Errors.AUTHENTICATION_ERROR);
//            promise.reject(exception);
//            authenticating = false;
//          }
//        }
//      }, null);
//    }
//    return promise;
//  }
//
//  public DefaultPromise<Boolean> logout(final Activity activity) {
//    final DefaultPromise<Boolean> promise = new DefaultPromise<>();
//    Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
//    if(accounts.length > 0) {
//      Account account = accounts[0];
//      removeAccount(activity, account).then(new DoneCallback<Boolean>() {
//        @Override
//        public void onDone(Boolean accountRemoved) {
//          if(accountRemoved) {
//            remoteService.getUserService().logout();
//            closicApplication.clearAll();
//          }
//          promise.resolve(accountRemoved);
//        }
//      }).fail(new FailCallback<APIException>() {
//        @Override
//        public void onFail(APIException e) {
//          promise.reject(e);
//        }
//      });
//    }
//    return promise;
//  }
//
//  private DefaultPromise<Boolean> removeAccount(Activity activity, Account account) {
//    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
//      return removeAccountOldVersions(account);
//    } else {
//      return removeAccountNewVersions(activity, account);
//    }
//  }
//
//  @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
//  private DefaultPromise<Boolean> removeAccountNewVersions(Activity activity, Account account) {
//    final DefaultPromise<Boolean> promise = new DefaultPromise<>();
//    accountManager.removeAccount(account, activity, new AccountManagerCallback<Bundle>() {
//      @Override
//      public void run(AccountManagerFuture<Bundle> future) {
//        try {
//          Bundle logoutBundle = future.getResult();
//          Boolean logoutSuccess = logoutBundle.getBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT);
//          promise.resolve(logoutSuccess);
//        } catch(OperationCanceledException e) {
//          APIException exception = new APIException();
//          exception.setKey(Errors.LOGOUT_CANCELED);
//          promise.reject(exception);
//        } catch(IOException | AuthenticatorException e) {
//          APIException exception = new APIException();
//          exception.setKey(Errors.LOGOUT_ERROR);
//          promise.reject(exception);
//        }
//      }
//    }, null);
//    return promise;
//  }
//
//  private DefaultPromise<Boolean> removeAccountOldVersions(Account account) {
//    final DefaultPromise<Boolean> promise = new DefaultPromise<>();
//    accountManager.removeAccount(account, new AccountManagerCallback<Boolean>() {
//      @Override
//      public void run(AccountManagerFuture<Boolean> future) {
//        try {
//          Boolean logoutSuccess = future.getResult();
//          promise.resolve(logoutSuccess);
//        } catch(OperationCanceledException e) {
//          APIException exception = new APIException();
//          exception.setKey(Errors.LOGOUT_CANCELED);
//          promise.reject(exception);
//        } catch(IOException | AuthenticatorException e) {
//          APIException exception = new APIException();
//          exception.setKey(Errors.LOGOUT_ERROR);
//          promise.reject(exception);
//        }
//      }
//    }, null);
//    return promise;
//  }
//
//  public void changePassword(String password) {
//    Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
//    if(accounts.length > 0) {
//      Account account = accounts[0];
//      accountManager.setPassword(account, password);
//    }
//  }
//
//  public boolean validPassword(String password) {
//    boolean isValid = false;
//    Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
//    if(accounts.length > 0) {
//      Account account = accounts[0];
//      isValid = accountManager.getPassword(account).equals(password);
//    }
//    return isValid;
//  }
//
//  public DefaultPromise<Boolean> changeLogin(Activity activity, final String login) {
//    return changeLogin(activity, login, false);
//  }
//
//  public DefaultPromise<Boolean> changeLogin(Activity activity, final String login, final boolean switchAuthType) {
//    final DefaultPromise<Boolean> promise = new DefaultPromise<>();
//    final Account account = getAccount();
//    if(account != null) {
//      final String password = accountManager.getPassword(account);
//      String currentAuthType = accountManager.getUserData(account, AuthConstants.AUTH_TYPE);
//      final String authType;
//      if(switchAuthType) {
//        if(currentAuthType.equals(AuthConstants.AUTH_TYPE_PHONE)) {
//          authType = AuthConstants.AUTH_TYPE_EMAIL;
//        } else {
//          authType = AuthConstants.AUTH_TYPE_PHONE;
//        }
//      } else {
//        authType = currentAuthType;
//      }
//      removeAccount(activity, account).then(new DoneCallback<Boolean>() {
//        @Override
//        public void onDone(Boolean accountRemoved) {
//          if(accountRemoved) {
//            addAccount(authType, login, password);
//          }
//          promise.resolve(accountRemoved);
//        }
//      }).fail(new FailCallback<APIException>() {
//        @Override
//        public void onFail(APIException e) {
//          promise.reject(e);
//        }
//      });
//    }
//    return promise;
//  }
//}