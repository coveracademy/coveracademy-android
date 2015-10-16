package com.coveracademy.api.service.rest.builder.request.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by wesley on 26/04/15.
 */
public class BooleanSerializer implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

  @Override
  public JsonElement serialize(Boolean value, Type type, JsonSerializationContext context) {
    return new JsonPrimitive(value ? 1 : 0);
  }

  @Override
  public Boolean deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
    return element.getAsInt() == 1;
  }

}
