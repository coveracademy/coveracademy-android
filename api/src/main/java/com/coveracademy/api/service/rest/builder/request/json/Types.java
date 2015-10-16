package com.coveracademy.api.service.rest.builder.request.json;

import com.coveracademy.api.model.Audition;
import com.coveracademy.api.model.User;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sandro on 5/7/15.
 */
public class Types {

  public static Type listOfUsers() {
    return new TypeToken<List<User>>() {
    }.getType();
  }

  public static Type listOfAuditions() {
    return new TypeToken<List<Audition>>() {
    }.getType();
  }

}