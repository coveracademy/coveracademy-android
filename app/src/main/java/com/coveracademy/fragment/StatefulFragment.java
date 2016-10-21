package com.coveracademy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sandro on 29/10/15.
 */
public abstract class StatefulFragment extends Fragment {

  private View createdView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if(createdView == null) {
      createdView = createView(inflater, container, savedInstanceState);
    }
    return createdView;
  }

  public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

  public View getCreatedView() {
    return createdView;
  }
}