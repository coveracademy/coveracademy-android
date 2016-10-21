package com.coveracademy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.coveracademy.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

  private static final String API_KEY = "AIzaSyCdzyzpu5uMQFV14w674MgvIGWhOMvbj60";
  private YouTubePlayerView youTubeVw;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_youtube_player);
    youTubeVw = (YouTubePlayerView) findViewById(R.id.youtubevw);
    youTubeVw.initialize(API_KEY, this);
  }


  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
    Toast.makeText(this,"Inicializado",Toast.LENGTH_LONG).show();
    if (!b) {
      youTubePlayer.cueVideo("H9BcbsmLEOY");
    }
  }

  @Override
  public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
    Toast.makeText(this,"Falhou",Toast.LENGTH_LONG).show();
  }
}
