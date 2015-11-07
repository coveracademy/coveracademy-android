package com.coveracademy.api.service.rest.builder.request.json;

import com.coveracademy.api.model.view.AuditionView;
import com.coveracademy.api.model.view.ContestView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sandro on 5/7/15.
 */
public class Types {

  public static Type listOfAuditionView() {
    return new TypeToken<List<AuditionView>>() {
    }.getType();
  }

  public static Type listOfContestView() {
    return new TypeToken<List<ContestView>>() {
    }.getType();
  }

}