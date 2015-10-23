package com.coveracademy;

import android.support.multidex.MultiDexApplication;

import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandro on 15/10/15.
 */
public class CoverAcademyApplication extends MultiDexApplication {

  private User user;
  private Map<Long, User> usersById;
  private Map<Long, Contest> contestsById;
  private List<Audition> auditions;
  private Map<Long, Long> totalVotes;
  private Map<Long, Long> totalComments;

  @Override
  public void onCreate() {
    super.onCreate();
    setup();
  }

  private void setup() {
    usersById = new HashMap<>();
    contestsById = new HashMap<>();
    totalVotes = new HashMap<>();
    totalComments = new HashMap<>();

    auditions = new ArrayList<>();
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void addUsers(List<User> users) {
    for(User user : users) {
      usersById.put(user.getId(), user);
    }
  }

  public User getUser(Long id) {
    return usersById.get(id);
  }

  public void addContests(List<Contest> contests) {
    for(Contest contest : contests) {
      contestsById.put(contest.getId(), contest);
    }
  }

  public Contest getContest(Long id) {
    return contestsById.get(id);
  }

  public void setAuditions(List<Audition> auditions) {
    this.auditions = auditions;
  }

  public List<Audition> listAuditions() {
    return auditions;
  }

  public void setTotalVotes(Map<Long, Long> totalVotes) {
    this.totalVotes = totalVotes;
  }

  public Long getTotalVotes(Audition audition) {
    return totalVotes.get(audition.getId());
  }

  public void setTotalComments(Map<Long, Long> totalComments) {
    this.totalComments = totalComments;
  }

  public Long getTotalComments(Audition audition) {
    return totalComments.get(audition.getId());
  }
}