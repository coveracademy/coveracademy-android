package com.coveracademy.api.model;

import java.util.Date;

public class Video {

  private Long id;
  private Long userId;
  private Long contestId;
  private String title;
  private String description;
  private String slug;
  private String url;
  private String smallThumbnail;
  private String mediumThumbnail;
  private String largeThumbnail;
  private Date registrationDate;

  private User user;
  private Contest contest;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getContestId() {
    return contestId;
  }

  public void setContestId(Long contestId) {
    this.contestId = contestId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSmallThumbnail() {
    return smallThumbnail;
  }

  public void setSmallThumbnail(String smallThumbnail) {
    this.smallThumbnail = smallThumbnail;
  }

  public String getMediumThumbnail() {
    return mediumThumbnail;
  }

  public void setMediumThumbnail(String mediumThumbnail) {
    this.mediumThumbnail = mediumThumbnail;
  }

  public String getLargeThumbnail() {
    return largeThumbnail;
  }

  public void setLargeThumbnail(String largeThumbnail) {
    this.largeThumbnail = largeThumbnail;
  }

  public Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Contest getContest() {
    return contest;
  }

  public void setContest(Contest contest) {
    this.contest = contest;
  }
}