package com.coveracademy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.coveracademy.R;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.Bind;
import butterknife.OnClick;

public class FaceConnect extends AppCompatActivity {

  private CallbackManager callbackManager;
  LoginButton btFaceConnect;
  @Bind(R.id.textView1) TextView textView1;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FacebookSdk.sdkInitialize(getApplicationContext());
    setContentView(R.layout.activity_face_connect);

      callbackManager = CallbackManager.Factory.create();
      btFaceConnect = (LoginButton) findViewById(R.id.btFaceConnect);
      btFaceConnect.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
          Profile profile = Profile.getCurrentProfile();
          Toast.makeText(getBaseContext(), "Connected!", Toast.LENGTH_LONG);
          textView1.append(profile.getName());
          textView1.append("-" + profile.getFirstName());

        }

        @Override
        public void onCancel() {
          Toast.makeText(getBaseContext(), "Login Canceled!", Toast.LENGTH_LONG);
        }

        @Override
        public void onError(FacebookException e) {
          Toast.makeText(getBaseContext(), "Login attempt failed!", Toast.LENGTH_LONG);
        }
      });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_face_connect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }

}
