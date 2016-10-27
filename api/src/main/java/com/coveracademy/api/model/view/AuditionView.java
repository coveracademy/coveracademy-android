package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Video;

public class AuditionView {

  private Video audition;
  private int totalLikes;
  private int totalComments;

  public Video getAudition() {
    return audition;
  }

  public void setAudition(Video audition) {
    this.audition = audition;
  }

  public Integer getTotalLikes() {
    return totalLikes;
  }

  public void setTotalLikes(Integer totalLikes) {
    this.totalLikes = totalLikes;
  }

  public Integer getTotalComments() {
    return totalComments;
  }

  public void setTotalComments(Integer totalComments) {
    this.totalComments = totalComments;
  }
}