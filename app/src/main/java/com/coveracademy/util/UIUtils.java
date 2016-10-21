package com.coveracademy.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.coveracademy.R;
import com.coveracademy.api.exception.APIException;
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

  public static void defaultSwipeRefreshLayout(SwipeRefreshLayout refreshLayout) {
    refreshLayout.setColorSchemeResources(R.color.swipe_refresh_first_color, R.color.swipe_refresh_second_color, R.color.swipe_refresh_third_color);
  }

  public static void alert(Context context, String title) {
    Toast.makeText(context, title, Toast.LENGTH_LONG).show();
  }

  public static void alert(Context context, APIException apiException) {
    String message = MessageUtils.getMessage(context, apiException);
    if(message != null) {
      alert(context, message);
    }
  }

  public static void alert(Context context, APIException apiException, String alternativeTitle) {
    String message = MessageUtils.getMessage(context, apiException);
    alert(context, message != null ? message : alternativeTitle);
  }

  public static void alert(View view, String title, String actionTitle, View.OnClickListener onActionClickListener) {
    final Snackbar snackbar = Snackbar.make(view, title, Snackbar.LENGTH_INDEFINITE);
    if(actionTitle != null && onActionClickListener != null) {
      snackbar.setAction(actionTitle, onActionClickListener);
    } else {
      int interval = 5000;
      Handler handler = new Handler();
      Runnable runnable = new Runnable() {
        public void run() {
          snackbar.dismiss();
        }
      };
      handler.postDelayed(runnable, interval);
    }
    snackbar.show();
  }

  public static void alert(View view, String title) {
    alert(view, title, null, null);
  }

  public static void alert(View view, APIException apiException) {
    String message = MessageUtils.getMessage(view.getContext(), apiException);
    if(message != null) {
      alert(view, message);
    }
  }

  public static void alert(View view, APIException apiException, String alternativeTitle) {
    String message = MessageUtils.getMessage(view.getContext(), apiException);
    alert(view, message != null ? message : alternativeTitle);
  }
}
