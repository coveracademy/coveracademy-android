package com.coveracademy;

import android.support.multidex.MultiDexApplication;

import com.coveracademy.api.model.Avatar;
import com.coveracademy.api.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandro on 15/10/15.
 */
public class CoverAcademyApplication extends MultiDexApplication {

  private User user;
  private Map<Long, User> usersById;
  private Map<Long, Avatar> avatarsByUserId;

  @Override
  public void onCreate() {
    super.onCreate();
    setup();
  }

  private void setup() {
    usersById = new HashMap<>();
    avatarsByUserId = new HashMap<>();
  }

  public User getUser() {
    return user;
  }

  public User getUser(Long id) {
    return usersById.get(id);
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void addUser(User user) {
    usersById.put(user.getId(), user);
  }

  public Avatar getAvatar(User user) {
    return avatarsByUserId.get(user.getId());
  }

  public void setAvatar(User user, Avatar avatar) {
    avatarsByUserId.put(user.getId(), avatar);
  }

}