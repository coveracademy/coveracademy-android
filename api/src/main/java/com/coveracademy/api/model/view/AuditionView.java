package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;

public class AuditionView {

  private Audition audition;
  private Contest contest;
  private User user;
  private int totalVotes;
  private int totalComments;

  public Audition getAudition() {
    return audition;
  }

  public void setAudition(Audition audition) {
    this.audition = audition;
  }

  public Contest getContest() {
    return contest;
  }

  public void setContest(Contest contest) {
    this.contest = contest;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getTotalVotes() {
    return totalVotes;
  }

  public void setTotalVotes(Integer totalVotes) {
    this.totalVotes = totalVotes;
  }

  public Integer getTotalComments() {
    return totalComments;
  }

  public void setTotalComments(Integer totalComments) {
    this.totalComments = totalComments;
  }
}