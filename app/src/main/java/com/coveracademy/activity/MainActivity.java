package com.coveracademy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coveracademy.R;
import com.coveracademy.adapter.AuditionsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  private AuditionsAdapter auditionsAdapter;

  @Bind(R.id.auditions) RecyclerView auditionsView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setupAuditions();
  }

  private void setupAuditions() {
    auditionsAdapter = new AuditionsAdapter(this);
    auditionsView.setLayoutManager(new LinearLayoutManager(this));
    auditionsView.setAdapter(auditionsAdapter);
  }
}
