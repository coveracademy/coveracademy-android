package com.coveracademy.util;

import android.content.Context;
import android.widget.ImageView;

import com.coveracademy.R;
import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.squareup.picasso.Picasso;

/**
 * Created by sandro on 15/10/15.
 */
public class ImageUtils {

  private static final String FACEBOOK_PICTURE_URL = "https://graph.facebook.com/v2.2/%s/picture?type=large";

  private ImageUtils() {

  }

  public static void setPhoto(Context context, User user, ImageView imageView) {
    switch(user.getProfilePicture()) {
      case facebook:
        Picasso.with(context).load(String.format(FACEBOOK_PICTURE_URL, user.getFacebookAccount())).placeholder(R.drawable.no_avatar).error(R.drawable.no_avatar).into(imageView);
        break;
      case twitter:
        Picasso.with(context).load(user.getTwitterPicture()).placeholder(R.drawable.no_avatar).error(R.drawable.no_avatar).into(imageView);
        break;
      case google:
        Picasso.with(context).load(user.getGooglePicture()).placeholder(R.drawable.no_avatar).error(R.drawable.no_avatar).into(imageView);
        break;
    }
  }

  public static void setThumbnail(Context context, Audition audition, ImageView imageView) {
    Picasso.with(context).load(audition.getLargeThumbnail()).into(imageView);
  }

  public static void setImage(Context context, Contest contest, ImageView imageView) {
    Picasso.with(context).load(contest.getImage()).into(imageView);
  }
}