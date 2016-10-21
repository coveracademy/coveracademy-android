package com.coveracademy.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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