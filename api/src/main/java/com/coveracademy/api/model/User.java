package com.coveracademy.api.model;

import java.util.Date;

/**
 * Created by sandro on 15/10/15.
 */
public class User {

  private Long id;
  private String name;
  private String email;
  private String permission;
  private String gender;
  private String biography;
  private String username;
  private String facebookAccount;
  private String facebookPicture;
  private String twitterAccount;
  private String twitterPicture;
  private String googleAccount;
  private String googlePicture;
  private String youtubeAccount;
  private String soundcloudAccount;
  private PictureType profilePicture;
  private Date registrationDate;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFacebookAccount() {
    return facebookAccount;
  }

  public void setFacebookAccount(String facebookAccount) {
    this.facebookAccount = facebookAccount;
  }

  public String getFacebookPicture() {
    return facebookPicture;
  }

  public void setFacebookPicture(String facebookPicture) {
    this.facebookPicture = facebookPicture;
  }

  public String getTwitterAccount() {
    return twitterAccount;
  }

  public void setTwitterAccount(String twitterAccount) {
    this.twitterAccount = twitterAccount;
  }

  public String getTwitterPicture() {
    return twitterPicture;
  }

  public void setTwitterPicture(String twitterPicture) {
    this.twitterPicture = twitterPicture;
  }

  public String getGoogleAccount() {
    return googleAccount;
  }

  public void setGoogleAccount(String googleAccount) {
    this.googleAccount = googleAccount;
  }

  public String getGooglePicture() {
    return googlePicture;
  }

  public void setGooglePicture(String googlePicture) {
    this.googlePicture = googlePicture;
  }

  public String getYoutubeAccount() {
    return youtubeAccount;
  }

  public void setYoutubeAccount(String youtubeAccount) {
    this.youtubeAccount = youtubeAccount;
  }

  public String getSoundcloudAccount() {
    return soundcloudAccount;
  }

  public void setSoundcloudAccount(String soundcloudAccount) {
    this.soundcloudAccount = soundcloudAccount;
  }

  public PictureType getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(PictureType profilePicture) {
    this.profilePicture = profilePicture;
  }

  public Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }
}
