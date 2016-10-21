package com.coveracademy.api.model;

import java.util.Date;

public class Audition {

  private Long Id;
  private Long contestId;
  private Long userId;
  private String slug;
  private String url;
  private String embedUrl;
  private String videoId;
  private String title;
  private String description;
  private String smallThumbnail;
  private String mediumThumbnail;
  private String largeThumbnail;
  private Integer place;
  private boolean approved;
  private Date registrationDate;

  private User user;

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    Id = id;
  }

  public Long getContestId() {
    return contestId;
  }

  public void setContestId(Long contestId) {
    this.contestId = contestId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

  public String getEmbedUrl() {
    return embedUrl;
  }

  public void setEmbedUrl(String embedUrl) {
    this.embedUrl = embedUrl;
  }

  public String getVideoId() {
    return videoId;
  }

  public void setVideoId(String videoId) {
    this.videoId = videoId;
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

  public Integer getPlace() {
    return place;
  }

  public void setPlace(Integer place) {
    this.place = place;
  }

  public boolean isApproved() {
    return approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
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
}
