package com.coveracademy.api.service.rest.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BooleanSerializer implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

  @Override
  public JsonElement serialize(Boolean value, Type type, JsonSerializationContext context) {
    return new JsonPrimitive(value ? 1 : 0);
  }

  @Override
  public Boolean deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
    String value = element.getAsString();
    boolean deserialized = false;
    if("1".equals(value) || "0".equals(value)) {
      deserialized = "1".equals(value);
    } else if("true".equals(value) || "false".equals(value)) {
      deserialized = "true".equals(value);
    }
    return deserialized;
  }
}