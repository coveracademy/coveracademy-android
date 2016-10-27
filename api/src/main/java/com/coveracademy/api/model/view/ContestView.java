package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;
import com.coveracademy.api.model.Video;

import java.util.List;
import java.util.Map;

public class ContestView {

  private Contest contest;
  private List<User> winners;
  private List<Video> auditions;
  private Integer totalAuditions;
  private Map<Long, Integer> totalLikes;
  private Map<Long, Integer> totalComments;

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

  public Integer getTotalAuditions() {
    return totalAuditions;
  }

  public void setTotalAuditions(Integer totalAuditions) {
    this.totalAuditions = totalAuditions;
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
}