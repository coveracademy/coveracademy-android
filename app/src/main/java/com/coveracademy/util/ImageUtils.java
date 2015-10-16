package com.coveracademy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.coveracademy.api.model.Avatar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sandro on 15/10/15.
 */
public class ImageUtils {


  private static final String TAG = ImageUtils.class.getSimpleName();
  public static final String MEDIA_DIRECTORY = "/CoverAcademy/media";
  public static final String USERS_MEDIA_DIRECTORY = MEDIA_DIRECTORY + "/users";
  public static final String AUDITIONS_MEDIA_DIRECTORY = MEDIA_DIRECTORY + "/auditions";

  private ImageUtils() {

  }

  //  public static void storeAvatar(Context context, User user) {
  //    CoverAcademyApplication application = ApplicationUtils.getApplication(context);
  //    Avatar avatar = application.getAvatar(user);
  //    if(avatar != null) {
  //      new StoreAvatarTask(USERS_MEDIA_DIRECTORY, avatar).execute();
  //    }
  //  }
  //
  //  public static void loadAvatar(Context context, User user, AvatarLoadedCallback avatarLoadedCallback) {
  //    CoverAcademyApplication application = ApplicationUtils.getApplication(context);
  //    if(user.getAvatar() != null) {
  //      new LoadAvatarTask(context, USERS_MEDIA_DIRECTORY, Configuration.USERS_MEDIA_URL, user.getAvatar(), avatarLoadedCallback).execute();
  //    } else {
  //      avatarLoadedCallback.onAvatarLoaded(null);
  //    }
  //  }
  //
  //  public static void setAvatar(final User user, final ImageView imageView) {
  //    setAvatar(user, imageView, null);
  //  }
  //
  //  public static void setAvatar(final User user, final ImageView imageView, final AvatarLoadedCallback avatarLoadedCallback) {
  //    final CoverAcademyApplication application = ApplicationUtils.getApplication(imageView.getContext());
  //    final Avatar avatar = application.getAvatar(user);
  //    if(avatar != null && avatar.getPath().equals(user.getAvatar())) {
  //      imageView.setImageBitmap(avatar.getImage());
  //      if(avatarLoadedCallback != null) {
  //        avatarLoadedCallback.onAvatarLoaded(avatar);
  //      }
  //    } else if(avatar != null || user.getAvatar() != null) {
  //      imageView.post(new Runnable() {
  //        @Override
  //        public void run() {
  //          String avatarUrl = user.getAvatar() != null ? user.getAvatar() : avatar.getPath();
  //          imageView.setImageBitmap(LetterTileProvider.getInstance(imageView.getContext()).getLetterTile(user.getFirstName(), imageView));
  //          new LoadAvatarTask(imageView.getContext(), USERS_MEDIA_DIRECTORY, Configuration.USERS_MEDIA_URL, avatarUrl, new AvatarLoadedCallback() {
  //            @Override
  //            public void onAvatarLoaded(Avatar avatar) {
  //              if(avatar != null) {
  //                imageView.setImageBitmap(avatar.getImage());
  //                application.setAvatar(user, avatar.getImage());
  //              }
  //              if(avatarLoadedCallback != null) {
  //                avatarLoadedCallback.onAvatarLoaded(avatar);
  //              }
  //            }
  //          }).execute();
  //        }
  //      });
  //    } else {
  //      imageView.post(new Runnable() {
  //        @Override
  //        public void run() {
  //          imageView.setImageBitmap(LetterTileProvider.getInstance(imageView.getContext()).getLetterTile(user.getFirstName(), imageView));
  //          if(avatarLoadedCallback != null) {
  //            avatarLoadedCallback.onAvatarLoaded(null);
  //          }
  //        }
  //      });
  //    }
  //  }

  public interface AvatarLoadedCallback {

    void onAvatarLoaded(Avatar avatar);
  }

  private static boolean isMediaMounted() {
    String status = Environment.getExternalStorageState();
    return Environment.MEDIA_MOUNTED.equals(status);
  }

  private static File getMediaDirectory(String directory) {
    File directoryFile = new File(Environment.getExternalStorageDirectory() + directory);
    directoryFile.mkdirs();
    return directoryFile;
  }

  private static class LoadAvatarTask extends AsyncTask<Void, Void, Avatar> {

    private Context context;
    private File directory;
    private String url;
    private String filename;
    private AvatarLoadedCallback callback;

    public LoadAvatarTask(Context context, String directory, String url, String filename, AvatarLoadedCallback callback) {
      this.context = context;
      this.directory = getMediaDirectory(directory);
      this.url = url;
      this.filename = filename;
      this.callback = callback;
    }

    @Override
    protected Avatar doInBackground(Void... params) {
      File file = new File(directory, filename);
      Avatar avatar = null;
      if(file.exists()) {
        Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath());
        avatar = new Avatar();
        avatar.setPath(filename);
        avatar.setImage(image);
      } else if(url != null) {
        try {
          Bitmap image = Picasso.with(context).load(url + filename).get();
          avatar = new Avatar();
          avatar.setPath(filename);
          avatar.setImage(image);
        } catch(IOException e) {
          Log.e(TAG, "Error loading avatar from remote location", e);
        }
      }
      return avatar;
    }

    @Override
    protected void onPostExecute(Avatar avatar) {
      if(avatar != null && !new File(directory, filename).exists()) {
        new StoreAvatarTask(directory, avatar).execute();
      }
      callback.onAvatarLoaded(avatar);
    }
  }

  public static class StoreAvatarTask extends AsyncTask<Void, Void, Void> {

    private File directory;
    private Avatar avatar;

    public StoreAvatarTask(File directory, Avatar avatar) {
      this.directory = directory;
      this.avatar = avatar;
    }

    public StoreAvatarTask(String directory, Avatar avatar) {
      this(getMediaDirectory(directory), avatar);
    }

    @Override
    protected Void doInBackground(Void... params) {
      if(avatar.getImage() != null && isMediaMounted()) {
        File file = new File(directory, avatar.getPath());
        FileOutputStream outputStream = null;
        try {
          outputStream = new FileOutputStream(file, !file.exists());
          avatar.getImage().compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } catch(FileNotFoundException e) {
          Log.e(TAG, "Error storing avatar", e);
        } finally {
          IOUtils.closeQuietly(outputStream);
        }
      } else {
        Log.w(TAG, "No available storage to save avatar file");
      }
      return null;
    }
  }
}