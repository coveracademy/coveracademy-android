package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Video;
import com.coveracademy.api.model.User;

import java.util.List;

public class UserView {

  private User user;
  private List<Video> videos;
  private List<User> fans;
  private boolean fan;
  private int totalVideos;
  private int totalFans;
  private int totalIdols;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Video> getVideos() {
    return videos;
  }

  public void setVideos(List<Video> videos) {
    this.videos = videos;
  }

  public List<User> getFans() {
    return fans;
  }

  public void setFans(List<User> fans) {
    this.fans = fans;
  }

  public boolean isFan() {
    return fan;
  }

  public void setFan(boolean fan) {
    this.fan = fan;
  }

  public int getTotalVideos() {
    return totalVideos;
  }

  public void setTotalVideos(int totalVideos) {
    this.totalVideos = totalVideos;
  }

  public int getTotalFans() {
    return totalFans;
  }

  public void setTotalFans(int totalFans) {
    this.totalFans = totalFans;
  }

  public int getTotalIdols() {
    return totalIdols;
  }

  public void setTotalIdols(int totalIdols) {
    this.totalIdols = totalIdols;
  }
}
