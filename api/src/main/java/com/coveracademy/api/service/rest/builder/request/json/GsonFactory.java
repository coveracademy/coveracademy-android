package com.coveracademy.api.service.rest.builder.request.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created by wesley on 26/04/15.
 */
public class GsonFactory {

  private static final Exclusions excusions = new Exclusions();

  public static Gson create() {
    return new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanSerializer()).registerTypeAdapter(boolean.class, new BooleanSerializer()).registerTypeAdapter(Date.class, new DateSerializer()).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).addDeserializationExclusionStrategy(excusions).addSerializationExclusionStrategy(excusions).create();
  }

  private static class Exclusions implements ExclusionStrategy {

    @Override
    public boolean shouldSkipClass(Class<?> arg0) {
      return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes field) {
      return field.getDeclaringClass().equals(Throwable.class);
    }
  }

}
