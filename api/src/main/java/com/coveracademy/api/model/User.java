package com.coveracademy.api.model;

import java.util.Date;

public class User {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String biography;
  private String username;
  private String facebookAccount;
  private String facebookPicture;
  private Date registrationDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String name) {
    this.firstName = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  public String getName() {
    return firstName + " " + lastName;
  }
}