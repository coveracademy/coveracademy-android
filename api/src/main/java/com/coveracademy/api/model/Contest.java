package com.coveracademy.api.model;

/**
 * Created by sandro on 10/20/15.
 */
public class Contest {

  private Long id;
  private String name;
  private String image;
  private Progress progress;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Progress getProgress() {
    return progress;
  }

  public void setProgress(Progress progress) {
    this.progress = progress;
  }

  public enum Progress {
    waiting, running, finished;
  }
}