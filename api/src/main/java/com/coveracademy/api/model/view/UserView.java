package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.User;

import java.util.List;

public class UserView {

  private User user;
  private List<Audition> auditions;
  private List<User> fans;
  private int totalFans;
  private boolean fan;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Audition> getAuditions() {
    return auditions;
  }

  public void setAuditions(List<Audition> auditions) {
    this.auditions = auditions;
  }

  public List<User> getFans() {
    return fans;
  }

  public void setFans(List<User> fans) {
    this.fans = fans;
  }

  public int getTotalFans() {
    return totalFans;
  }

  public void setTotalFans(int totalFans) {
    this.totalFans = totalFans;
  }

  public boolean isFan() {
    return fan;
  }

  public void setFan(boolean fan) {
    this.fan = fan;
  }
}
