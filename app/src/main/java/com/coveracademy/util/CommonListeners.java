package com.coveracademy.util;

import android.view.View;
import android.widget.CheckBox;

/**
 * Created by sandro on 29/10/15.
 */
public class CommonListeners {

  private CommonListeners() {

  }

  public interface OnClickListener {

    void onClick(View view, int position);

  }

  public interface OnItemClickListener {

    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);

  }

  public interface OnCheckedChangedListener {

    void onCheckedChanged(CheckBox checkBox, int position);

  }
}