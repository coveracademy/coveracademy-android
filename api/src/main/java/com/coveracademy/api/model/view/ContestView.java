package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.Video;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContestView {

  private Contest contest;
  private List<User> winners;
  private List<Video> videos;
  private Set<Long> likedVideos;
  private Map<Long, Integer> totalLikes;
  private Map<Long, Integer> totalComments;
  private int totalVideos;

  public Contest getContest() {
    return contest;
  }

  public void setContest(Contest contest) {
    this.contest = contest;
  }

  public List<User> getWinners() {
    return winners;
  }

  public void setWinners(List<User> winners) {
    this.winners = winners;
  }

  public List<Video> getVideos() {
    return videos;
  }

  public void setVideos(List<Video> videos) {
    this.videos = videos;
  }

  public Set<Long> getLikedVideos() {
    return likedVideos;
  }

  public void setLikedVideos(Set<Long> likedVideos) {
    this.likedVideos = likedVideos;
  }

  public Map<Long, Integer> getTotalLikes() {
    return totalLikes;
  }

  public void setTotalLikes(Map<Long, Integer> totalLikes) {
    this.totalLikes = totalLikes;
  }

  public Map<Long, Integer> getTotalComments() {
    return totalComments;
  }

  public void setTotalComments(Map<Long, Integer> totalComments) {
    this.totalComments = totalComments;
  }

  public int getTotalVideos() {
    return totalVideos;
  }

  public void setTotalVideos(int totalVideos) {
    this.totalVideos = totalVideos;
  }
}