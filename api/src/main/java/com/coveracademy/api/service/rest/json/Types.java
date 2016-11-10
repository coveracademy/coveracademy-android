package com.coveracademy.api.service.rest.json;

import com.coveracademy.api.model.Comment;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.view.ContestsItemView;
import com.coveracademy.api.model.view.VideoView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Types {

  public static Type mapOfStrings() {
    return new TypeToken<Map<String, String>>() {
    }.getType();
  }

  public static Type listOfVideoView() {
    return new TypeToken<List<VideoView>>() {
    }.getType();
  }

  public static Type listOfContestView() {
    return new TypeToken<List<ContestsItemView>>() {
    }.getType();
  }

  public static Type listOfComments() {
    return new TypeToken<List<Comment>>() {
    }.getType();
  }

  public static Type listOfContests() {
    return new TypeToken<List<Contest>>() {
    }.getType();
  }
}