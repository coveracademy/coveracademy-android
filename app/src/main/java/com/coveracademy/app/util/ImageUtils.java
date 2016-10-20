package com.coveracademy.app.util;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.coveracademy.app.R;
import com.coveracademy.app.adapter.IntentSelectorAdapter;
import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.app.constant.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageUtils {

  private static final String IMAGES_ONLY = "image/*";
  private static final String VIDEOS_ONLY = "video/*";

  private static final String MEDIA_DIRECTORY = "/Cover Academy/media";
  private static final String TEMP_MEDIA_DIRECTORY = MEDIA_DIRECTORY + "/temp";
  private static final String CROP_FILE_NAME = "/cropped";
  private static final String IMAGE_FILE_NAME = "/image";
  private static final String VIDEO_FILE_NAME = "/video";

  private static final String FACEBOOK_PICTURE_URL = "https://graph.facebook.com/v2.2/%s/picture?type=large";
  private static final Set<String> NO_MEDIA_DIRECTORIES = new HashSet<>();

  static {
    NO_MEDIA_DIRECTORIES.add(TEMP_MEDIA_DIRECTORY);
  }

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

  public static void selectImage(Activity activity) {
    File directory = getDirectory(TEMP_MEDIA_DIRECTORY);
    File file = new File(directory, VIDEO_FILE_NAME);
    directory.mkdirs();

    Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    Intent selectImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
    selectImageIntent.setType(VIDEOS_ONLY);

    List<ResolveInfo> resolveInfos = new ArrayList<>();
    List<Intent> intents = new ArrayList<>();
    PackageManager packageManager = activity.getPackageManager();

    List<ResolveInfo> captureImageResolveInfos = packageManager.queryIntentActivities(captureImageIntent, 0);
    if(ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)) {
      for(ResolveInfo captureImageResolveInfo : captureImageResolveInfos) {
        Intent intent = new Intent(captureImageIntent);
        intent.setComponent(new ComponentName(captureImageResolveInfo.activityInfo.packageName, captureImageResolveInfo.activityInfo.name));
        intents.add(intent);
        resolveInfos.add(captureImageResolveInfo);
      }
    }

    List<ResolveInfo> selectImagesResolveInfos = packageManager.queryIntentActivities(selectImageIntent, 0);
    for(ResolveInfo selectImageResolveInfo : selectImagesResolveInfos) {
      Intent intent = new Intent(selectImageIntent);
      intent.setComponent(new ComponentName(selectImageResolveInfo.activityInfo.packageName, selectImageResolveInfo.activityInfo.name));
      intents.add(intent);
      resolveInfos.add(selectImageResolveInfo);
    }

    openImageSelector(activity, intents, resolveInfos);
  }

  private static void openImageSelector(final Activity activity, final List<Intent> intents, List<ResolveInfo> resolveInfos) {
    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
    dialog.setTitle(activity.getResources().getString(R.string.dialog_select_an_option));
    dialog.setAdapter(new IntentSelectorAdapter(activity, resolveInfos), new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int id) {
        Intent intent = intents.get(id);
        activity.startActivityForResult(intent, Constants.REQUEST_SELECT_IMAGE);
      }
    });
    dialog.show();
  }

  private static File getDirectory(String directory) {
    File directoryFile = new File(Environment.getExternalStorageDirectory() + directory);
    directoryFile.mkdirs();
    if(NO_MEDIA_DIRECTORIES.contains(directory)) {
      File nomediaFile = new File(directoryFile, ".nomedia");
      if(!nomediaFile.exists()) {
        try {
          nomediaFile.createNewFile();
        } catch(IOException e) {
          // Ignore if nomedia file can not be created
        }
      }
    }
    return directoryFile;
  }
}