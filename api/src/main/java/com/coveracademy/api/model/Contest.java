package com.coveracademy.api.model;

import com.coveracademy.api.enumeration.Progress;

import java.util.Date;

public class Contest {

  private Long id;
  private String name;
  private String description;
  private String image;
  private String slug;
  private Date startDate;
  private Date endDate;
  private Date registrationDate;
  private Integer minimumContestants;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
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

  public Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  public Integer getMinimumContestants() {
    return minimumContestants;
  }

  public void setMinimumContestants(Integer minimumContestants) {
    this.minimumContestants = minimumContestants;
  }

  public Progress getProgress() {
    Date now = new Date();
    Progress progress;
    if(startDate == null || endDate == null || now.before(startDate)) {
      progress = Progress.WAITING;
    } else if(now.after(endDate)) {
      progress = Progress.FINISHED;
    } else {
      progress = Progress.RUNNING;
    }
    return progress;
  }
}