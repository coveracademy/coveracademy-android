package com.coveracademy.api.service.rest.builder.request.json;

import com.coveracademy.api.model.view.AuditionView;
import com.coveracademy.api.model.view.ContestsItemView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Types {

  public static Type mapOfStrings() {
    return new TypeToken<Map<String, String>>() {
    }.getType();
  }

  public static Type listOfAuditionView() {
    return new TypeToken<List<AuditionView>>() {
    }.getType();
  }

  public static Type listOfContestView() {
    return new TypeToken<List<ContestsItemView>>() {
    }.getType();
  }

}