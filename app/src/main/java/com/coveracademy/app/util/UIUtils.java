package com.coveracademy.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coveracademy.app.R;
import com.coveracademy.api.exception.APIException;
import com.rey.material.widget.ProgressView;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class UIUtils {

  private static final Map<String, AlertDialog> ALERT_DIALOGS = new HashMap<>();

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

  public static void showProgressDialog(String id, Context context, int titleResource) {
    showProgressDialog(id, context, titleResource, null);
  }

  public static void showProgressDialog(String id, Context context, int titleResource, final String cancelationTag) {
    if(!ALERT_DIALOGS.containsKey(id)) {
      View promptView = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
      TextView titleView = ButterKnife.findById(promptView, R.id.title);
      titleView.setText(context.getString(titleResource));
      AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
      dialogBuilder.setView(promptView);
      dialogBuilder.setCancelable(false);
      if(cancelationTag != null) {
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
          }
        });
      }
      AlertDialog dialog = dialogBuilder.create();
      dialog.show();
      ALERT_DIALOGS.put(id, dialog);
    }
  }

  public static void hideProgressDialog(String id) {
    if(ALERT_DIALOGS.containsKey(id)) {
      AlertDialog dialog = ALERT_DIALOGS.get(id);
      dialog.dismiss();
      ALERT_DIALOGS.remove(id);
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
      Handler handler = new Handler(Looper.getMainLooper());
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
