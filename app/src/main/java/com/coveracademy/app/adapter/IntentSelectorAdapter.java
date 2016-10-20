package com.coveracademy.app.adapter;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.app.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntentSelectorAdapter extends ArrayAdapter<ResolveInfo> {

  @BindView(R.id.icon) ImageView iconView;
  @BindView(R.id.title) TextView titleView;

  public IntentSelectorAdapter(Context context, List<ResolveInfo> resolveInfos) {
    super(context, R.layout.item_intent, R.id.title, resolveInfos);
  }

  @Override
  public View getView(int index, View convertView, ViewGroup parent) {
    View view = super.getView(index, convertView, parent);
    ButterKnife.bind(this, view);
    ResolveInfo resolveInfo = getItem(index);
    iconView.setImageDrawable(resolveInfo.loadIcon(getContext().getPackageManager()));
    titleView.setText(resolveInfo.loadLabel(getContext().getPackageManager()).toString());
    return view;
  }
}
