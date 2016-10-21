package com.coveracademy.api.model;

import java.util.Date;

public class Contest {

  private Long id;
  private String name;
  private String image;
  private Progress progress;
  private Date startDate;
  private Date endDate;

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

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public enum Progress {
    waiting, running, finished;
  }
}