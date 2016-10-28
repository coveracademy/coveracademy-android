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
  private List<Video> auditions;
  private Set<Long> likedAuditions;
  private Map<Long, Integer> totalLikes;
  private Map<Long, Integer> totalComments;
  private int totalAuditions;

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

  public List<Video> getAuditions() {
    return auditions;
  }

  public void setAuditions(List<Video> auditions) {
    this.auditions = auditions;
  }

  public Set<Long> getLikedAuditions() {
    return likedAuditions;
  }

  public void setLikedAuditions(Set<Long> likedAuditions) {
    this.likedAuditions = likedAuditions;
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

  public int getTotalAuditions() {
    return totalAuditions;
  }

  public void setTotalAuditions(int totalAuditions) {
    this.totalAuditions = totalAuditions;
  }
}