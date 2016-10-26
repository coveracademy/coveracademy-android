package com.coveracademy.app.activity;

import android.content.Intent;
import android.os.Bundle;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.User;
import com.coveracademy.app.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends CoverAcademyActivity {

  private static String[] FACEBOOK_PERMISSIONS = new String[] {"email"};

  private WelcomeActivity instance;
  private CallbackManager callbackManager;

  @BindView(R.id.login) LoginButton loginButton;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.activity_welcome);
    ButterKnife.bind(this);

    instance = this;

    setupFacebookLogin();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }

  private void setupFacebookLogin() {
    callbackManager = CallbackManager.Factory.create();
    loginButton.setReadPermissions(FACEBOOK_PERMISSIONS);
    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(LoginResult loginResult) {
        remoteService.getUserService().authenticate(loginResult.getAccessToken().getToken()).then(new DoneCallback<User>() {
          @Override
          public void onDone(User user) {
            application.setUser(user);
            Intent intent = new Intent(instance, MainActivity.class);
            startActivity(intent);
            finish();
          }
        }).fail(new FailCallback<APIException>() {
          @Override
          public void onFail(APIException result) {
            LoginManager.getInstance().logOut();
          }
        });
      }

      @Override
      public void onCancel() {

      }

      @Override
      public void onError(FacebookException exception) {

      }
    });
  }
}