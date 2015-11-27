package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;

import java.util.List;
import java.util.Map;

/**
 * Created by sandro on 06/11/15.
 */
public class ContestView {

  private Contest contest;
  private List<Audition> winnerAuditions;
  private List<Audition> auditions;
  private int totalAuditions;
  private Map<Long, Integer> votesByAudition;

  public Contest getContest() {
    return contest;
  }

  public void setContest(Contest contest) {
    this.contest = contest;
  }

  public List<Audition> getWinnerAuditions() {
    return winnerAuditions;
  }

  public void setWinnerAuditions(List<Audition> winnerAuditions) {
    this.winnerAuditions = winnerAuditions;
  }

  public List<Audition> getAuditions() {
    return auditions;
  }

  public void setAuditions(List<Audition> auditions) {
    this.auditions = auditions;
  }

  public int getTotalAuditions() {
    return totalAuditions;
  }

  public void setTotalAuditions(int totalAuditions) {
    this.totalAuditions = totalAuditions;
  }

  public Map<Long, Integer> getVotesByAudition() {
    return votesByAudition;
  }

  public void setVotesByAudition(Map<Long, Integer> votesByAudition) {
    this.votesByAudition = votesByAudition;
  }
}
