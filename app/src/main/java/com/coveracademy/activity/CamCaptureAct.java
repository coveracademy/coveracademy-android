package com.coveracademy.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.coveracademy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import java.io.File;

public class CamCaptureAct extends AppCompatActivity {

  @Bind(R.id.buttonRec) Button buttonRec;
  @Bind(R.id.videoView1)VideoView videoView;
  private static final int VIDEO_CAPTURE = 101;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cam_capture);
    ButterKnife.bind(this);
    buttonRec.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        recordVideo();
      }
    });
  }

  private void recordVideo() {
    if (hasCamera()){
      Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
      File mediaFile =
          new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/myvideo.mp4");
      Uri videoUri = Uri.fromFile(mediaFile);
      intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
      startActivityForResult(intent, VIDEO_CAPTURE);
    }
  }

  private boolean hasCamera() {
    if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
      return true;
    else
      return false;
  }

  protected void onActivityResult(int requestCode,
                                  int resultCode, Intent data) {

    if (requestCode == VIDEO_CAPTURE) {
      if (resultCode == RESULT_OK) {
        Toast.makeText(this, "Video has been saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
      } else if (resultCode == RESULT_CANCELED) {
        Toast.makeText(this, "Video recording cancelled.",
          Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(this, "Failed to record video",
          Toast.LENGTH_LONG).show();
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_cam_capture, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if(id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
