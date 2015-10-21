package com.coveracademy.api.model.view;

import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;

import java.util.List;

/**
 * Created by sandro on 10/20/15.
 */
public class AuditionsView {

  private List<Audition> auditions;
  private List<Contest> contests;
  private List<User> users;

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
}
