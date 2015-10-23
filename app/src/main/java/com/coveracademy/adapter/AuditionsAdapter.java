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
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.util.ApplicationUtils;
import com.coveracademy.util.ImageUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sandro on 15/10/15.
 */
public class AuditionsAdapter extends BaseAdapter<Audition, AuditionsAdapter.AuditionViewHolder> {

  private CoverAcademyApplication application;

  public AuditionsAdapter(Context context) {
    super(context, listAuditions(context));
    application = ApplicationUtils.getApplication(context);
  }

  private static List<Audition> listAuditions(Context context) {
    CoverAcademyApplication application = ApplicationUtils.getApplication(context);
    return application.listAuditions();
  }

  @Override
  public AuditionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_audition, parent, false);
    return new AuditionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AuditionViewHolder holder, int position) {
    Audition audition = getItem(position);
    Contest contest = application.getContest(audition.getContestId());
    User user = application.getUser(audition.getUserId());
    holder.userNameView.setText(user.getName());
    ImageUtils.setThumbnail(getContext(), audition, holder.auditionThumbnailView);
    ImageUtils.setPicture(getContext(), user, holder.userAvatarView);
  }

  public class AuditionViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.user_avatar) ImageView userAvatarView;
    @Bind(R.id.user_name) TextView userNameView;
    @Bind(R.id.audition_thumbnail) ImageView auditionThumbnailView;

    public AuditionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}