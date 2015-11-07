package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;

import java.util.List;

/**
 * Created by sandro on 06/11/15.
 */
public class ContestView {

  private Contest contest;
  private List<Audition> winnerAuditions;
  private int totalAuditions;
  private int totalVotes;

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

  public int getTotalAuditions() {
    return totalAuditions;
  }

  public void setTotalAuditions(int totalAuditions) {
    this.totalAuditions = totalAuditions;
  }

  public int getTotalVotes() {
    return totalVotes;
  }

  public void setTotalVotes(int totalVotes) {
    this.totalVotes = totalVotes;
  }
}
