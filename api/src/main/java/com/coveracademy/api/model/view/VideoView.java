package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Video;

public class VideoView {

  private Video video;
  private int totalLikes;
  private int totalComments;
  private boolean liked;
  private boolean fan;

  public Video getVideo() {
    return video;
  }

  public void setVideo(Video video) {
    this.video = video;
  }

  public int getTotalLikes() {
    return totalLikes;
  }

  public void setTotalLikes(int totalLikes) {
    this.totalLikes = totalLikes;
  }

  public int getTotalComments() {
    return totalComments;
  }

  public void setTotalComments(int totalComments) {
    this.totalComments = totalComments;
  }

  public boolean isLiked() {
    return liked;
  }

  public void setLiked(boolean liked) {
    this.liked = liked;
  }

  public boolean isFan() {
    return fan;
  }

  public void setFan(boolean fan) {
    this.fan = fan;
  }
}