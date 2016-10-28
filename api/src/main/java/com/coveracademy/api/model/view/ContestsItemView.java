package com.coveracademy.api.model.view;

import com.coveracademy.api.model.User;
import com.coveracademy.api.model.Contest;

import java.util.List;

public class ContestsItemView {

  private Contest contest;
  private List<User> winners;
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

  public int getTotalVideos() {
    return totalVideos;
  }

  public void setTotalVideos(int totalVideos) {
    this.totalVideos = totalVideos;
  }
}