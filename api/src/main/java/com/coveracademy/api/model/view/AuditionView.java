package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Video;

public class AuditionView {

  private Video audition;
  private int totalLikes;
  private int totalComments;
  private boolean liked;

  public Video getAudition() {
    return audition;
  }

  public void setAudition(Video audition) {
    this.audition = audition;
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
}