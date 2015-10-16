package com.coveracademy.api.model;

import android.graphics.Bitmap;

/**
 * Created by sandro on 15/10/15.
 */
public class Avatar {

  private Bitmap image;
  private String path;

  public Bitmap getImage() {
    return image;
  }

  public void setImage(Bitmap image) {
    this.image = image;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

}
