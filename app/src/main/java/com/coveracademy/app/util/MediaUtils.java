package com.coveracademy.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.ImageView;

import com.coveracademy.api.model.Video;
import com.coveracademy.app.R;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.app.constant.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MediaUtils {

  private static final String VIDEOS_ONLY = "video/*";

  private static final String MEDIA_DIRECTORY = "/CoverAcademy/media";
  private static final String TEMP_MEDIA_DIRECTORY = MEDIA_DIRECTORY + "/temp";
  private static final String VIDEOS_MEDIA_DIRECTORY = MEDIA_DIRECTORY + "/videos";
  public static final String VIDEO_FILE_NAME = "video";

  private static final String FACEBOOK_PICTURE_URL = "https://graph.facebook.com/v2.2/%s/picture?type=large";

  private static final Set<String> MEDIA_DIRECTORIES = new HashSet<>();

  static {
    MEDIA_DIRECTORIES.add(TEMP_MEDIA_DIRECTORY);
    MEDIA_DIRECTORIES.add(VIDEOS_MEDIA_DIRECTORY);
    createDirectories();
  }

  private MediaUtils() {

  }

  public static void setPicture(Context context, User user, ImageView imageView) {
    Picasso.with(context).load(String.format(FACEBOOK_PICTURE_URL, user.getFacebookAccount())).placeholder(R.drawable.no_avatar).error(R.drawable.no_avatar).into(imageView);
  }

  public static void setThumbnail(Context context, Video video, ImageView imageView) {
    Picasso.with(context).load(video.getLargeThumbnail()).into(imageView);
  }

  public static void setImage(Context context, Contest contest, ImageView imageView) {
    Picasso.with(context).load(contest.getImage()).into(imageView);
  }

  public static void selectVideo(Activity activity) {
    Intent intent = new Intent();
    intent.setTypeAndNormalize(VIDEOS_ONLY);
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    activity.startActivityForResult(Intent.createChooser(intent, activity.getString(R.string.select_video)), Constants.REQUEST_SELECT_VIDEO);
  }

  public static String getTemporaryDirectory() {
    return Environment.getExternalStorageDirectory() + "/" + TEMP_MEDIA_DIRECTORY;
  }

  public static void createDirectories() {
    for(String directory : MEDIA_DIRECTORIES) {
      File directoryFile = new File(Environment.getExternalStorageDirectory(), directory);
      if(directoryFile.mkdirs()) {
        File nomediaFile = new File(directoryFile, ".nomedia");
        if(!nomediaFile.exists()) {
          try {
            nomediaFile.createNewFile();
          } catch(IOException e) {
            // Ignore if nomedia file can not be created
          }
        }
      }
    }
  }
}