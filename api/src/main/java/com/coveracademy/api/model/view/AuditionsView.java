package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by sandro on 10/20/15.
 */
public class AuditionsView {

  private List<Audition> auditions;
  private List<Contest> contests;
  private List<User> users;
  private Map<Long, Long> totalVotes;
  private Map<Long, Long> totalComments;

  public List<Audition> getAuditions() {
    return auditions;
  }

  public void setAuditions(List<Audition> auditions) {
    this.auditions = auditions;
  }

  public List<Contest> getContests() {
    return contests;
  }

  public void setContests(List<Contest> contests) {
    this.contests = contests;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public Map<Long, Long> getTotalVotes() {
    return totalVotes;
  }

  public void setTotalVotes(Map<Long, Long> totalVotes) {
    this.totalVotes = totalVotes;
  }

  public Map<Long, Long> getTotalComments() {
    return totalComments;
  }

  public void setTotalComments(Map<Long, Long> totalComments) {
    this.totalComments = totalComments;
  }
}
