package com.coveracademy.api.service.rest.json;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {

  private static final String TAG = DateSerializer.class.getSimpleName();
  private DateFormat dateFormat;

  public DateSerializer() {
    dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  @Override
  public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(dateFormat.format(src));
  }

  @Override
  public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
    Date deserialized = null;
    try {
      deserialized = dateFormat.parse(json.getAsString());
    } catch(ParseException e) {
      Log.w(TAG, "Error deserializing date.", e);
    }
    return deserialized;
  }
}
