package com.coveracademy.util;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.coveracademy.R;
import com.rey.material.widget.ProgressView;

import butterknife.ButterKnife;

/**
 * Created by sandro on 22/10/15.
 */
public class UIUtils {

  public static void defaultToolbar(final Activity activity) {
    defaultToolbar(activity, new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        activity.finish();
      }
    });
  }

  public static void defaultToolbar(Activity activity, View.OnClickListener onClickListener) {
    Toolbar toolbar = ButterKnife.findById(activity, R.id.toolbar);
    if(toolbar != null) {
      if(activity instanceof AppCompatActivity) {
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);
      }
      toolbar.setNavigationIcon(ApplicationUtils.getAttributeResourceId(activity, R.attr.homeAsUpIndicator));
      toolbar.setNavigationOnClickListener(onClickListener);
    }
  }

  public static void showProgressBar(Activity activity) {
    ProgressView progressView = ButterKnife.findById(activity, R.id.progress_view);
    if(progressView != null) {
      progressView.start();
    }
  }

  public static void showProgressBar(View view) {
    ProgressView progressView = ButterKnife.findById(view, R.id.progress_view);
    if(progressView != null) {
      progressView.start();
    }
  }

  public static void hideProgressBar(Activity activity) {
    ProgressView progressView = ButterKnife.findById(activity, R.id.progress_view);
    if(progressView != null) {
      progressView.stop();
    }
  }

  public static void hideProgressBar(View view) {
    ProgressView progressView = ButterKnife.findById(view, R.id.progress_view);
    if(progressView != null) {
      progressView.stop();
    }
  }
}
