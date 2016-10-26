package com.coveracademy.app.util.component;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.coveracademy.app.R;

public class ConfirmDialog implements DialogInterface.OnCancelListener, DialogInterface.OnClickListener {

  private AlertDialog.Builder builder;
  private AlertDialog.OnClickListener onNegativeClickListener;

  public ConfirmDialog(Context context, String title, String message) {
    builder = new AlertDialog.Builder(context);
    builder.setTitle(title);
    builder.setMessage(message);
    builder.setPositiveButton(builder.getContext().getString(R.string.dialog_positive_button), this);
    builder.setNegativeButton(builder.getContext().getString(R.string.dialog_negative_button), this);
    builder.setOnCancelListener(this);
  }

  public void setOnPositiveClickListener(AlertDialog.OnClickListener onPositiveClickListener) {
    builder.setPositiveButton(builder.getContext().getString(R.string.dialog_positive_button), onPositiveClickListener);
  }

  public void setOnPositiveClickListener(String positiveText, AlertDialog.OnClickListener onPositiveClickListener) {
    builder.setPositiveButton(positiveText, onPositiveClickListener);
  }

  public void setOnNegativeClickListener(AlertDialog.OnClickListener onNegativeClickListener) {
    this.onNegativeClickListener = onNegativeClickListener;
    builder.setNegativeButton(builder.getContext().getString(R.string.dialog_negative_button), onNegativeClickListener);
  }

  public void setOnNegativeClickListener(String negativeText, AlertDialog.OnClickListener onNegativeClickListener) {
    this.onNegativeClickListener = onNegativeClickListener;
    builder.setNegativeButton(negativeText, onNegativeClickListener);
  }

  public void show() {
    builder.create().show();
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    dialog.dismiss();
  }

  @Override
  public void onCancel(DialogInterface dialogInterface) {
    if(onNegativeClickListener != null) {
      onNegativeClickListener.onClick(dialogInterface, 0);
    }
  }
}