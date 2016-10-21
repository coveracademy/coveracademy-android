package com.coveracademy.api.service.rest.builder.request.multipart;

import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.coveracademy.api.service.rest.builder.request.json.GsonFactory;
import com.coveracademy.api.service.rest.builder.request.json.JsonRequest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.Random;

public class MultipartRequest extends JsonRequest {

  private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private static final String CHARSET = "UTF-8";
  private static final String LINE_FEED = "\r\n";
  private static final String TWO_DASHES = "--";
  private static final String FILENAME = "default.jpg";
  private static final String FILES_KEY = "files";
  private static final String FIELDS_KEY = "fields";
  private static final int DEFAULT_TIMEOUT = 30000;
  private static final int DEFAULT_MAX_RETRIES = 0;

  private ByteArrayOutputStream bodyOutStream;
  private PrintStream bodyPrintStream;
  private String boundary;
  private Bitmap bitmap;
  private Object fields;

  public MultipartRequest(String url, Object fields, Bitmap bitmap, Type responseType, Response.ErrorListener errorListener, Response.Listener<Object> listener) {
    super(url, responseType, Method.POST, listener, errorListener);
    this.fields = fields;
    this.bitmap = bitmap;
    this.boundary = generateBoundary();
    this.bodyOutStream = new ByteArrayOutputStream();
    this.bodyPrintStream = new PrintStream(bodyOutStream);
    setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
  }

  private void appendFieldsEntity() {
    if(fields != null) {
      bodyPrintStream.append(TWO_DASHES).append(boundary).append(LINE_FEED);
      bodyPrintStream.append("Content-Disposition: form-data; name=\"").append(FIELDS_KEY).append("\"").append(LINE_FEED);
      bodyPrintStream.append("Content-Type: application/json; charset=").append(CHARSET).append(LINE_FEED);
      bodyPrintStream.append(LINE_FEED);
      bodyPrintStream.append(GsonFactory.create().toJson(fields));
      bodyPrintStream.append(LINE_FEED);
    }
  }

  private void appendFileEntity() {
    if(bitmap != null) {
      bodyPrintStream.append(TWO_DASHES).append(boundary).append(LINE_FEED);
      bodyPrintStream.append("Content-Disposition: form-data; name=\"").append(FILES_KEY).append("\"; filename=\"").append(FILENAME).append("\"").append(LINE_FEED);
      bodyPrintStream.append("Content-Type: image/jpeg").append(LINE_FEED);
      bodyPrintStream.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
      bodyPrintStream.append(LINE_FEED);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bodyPrintStream);
      bodyPrintStream.append(LINE_FEED);
    }
  }

  private void appendBoundary() {
    bodyPrintStream.append(TWO_DASHES).append(boundary).append(TWO_DASHES).append(LINE_FEED);
  }

  private String generateBoundary() {
    final StringBuilder buffer = new StringBuilder();
    final Random rand = new Random();
    final int count = rand.nextInt(11) + 30;
    for(int i = 0; i < count; i++) {
      buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
    }
    return buffer.toString();
  }

  @Override
  public byte[] getBody() throws AuthFailureError {
    appendFieldsEntity();
    appendFileEntity();
    appendBoundary();
    byte[] body = bodyOutStream.toByteArray();
    bodyPrintStream.close();
    return body;
  }

  @Override
  public String getBodyContentType() {
    return String.format("multipart/form-data; boundary=%s; charset=%s", boundary, CHARSET);
  }

}