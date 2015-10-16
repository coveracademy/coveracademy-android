package com.coveracademy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.CoverAcademyApplication;
import com.coveracademy.R;
import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.User;
import com.coveracademy.util.ApplicationUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by sandro on 15/10/15.
 */
public class AuditionsAdapter extends BaseAdapter<Audition, AuditionsAdapter.AuditionViewHolder> {

  private CoverAcademyApplication application;

  public AuditionsAdapter(Context context) {
    super(context, new ArrayList<Audition>());
    application = ApplicationUtils.getApplication(context);
  }

  @Override
  public AuditionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_audition, parent, false);
    return new AuditionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AuditionViewHolder holder, int position) {
    Audition audition = getItem(position);
    User user = application.getUser(audition.getUserId());
    holder.userNameView.setText(user.getName());
    holder.userAvatarView.setImageResource(R.drawable.no_avatar);
  }

  @Override
  public int getItemCount() {
    return 0;
  }

  public class AuditionViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.user_avatar) ImageView userAvatarView;
    @Bind(R.id.user_name) TextView userNameView;

    public AuditionViewHolder(View itemView) {
      super(itemView);
    }
  }
}
