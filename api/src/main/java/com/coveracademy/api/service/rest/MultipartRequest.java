package com.coveracademy.api.service.rest;

import android.content.Context;
import android.net.Uri;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Response;

public class MultipartRequest extends Request<Object> {

  private Uri uri;
  private UploadNotificationConfig notificationConfig;
  private String uploadId;

  public MultipartRequest(Context context, Uri uri) {
    super(context);
    this.uri = uri;
  }

  @Override
  MediaType getMediaType() {
    return null;
  }

  @Override
  Object convertResponse(Response response) throws IOException {
    return null;
  }

  public Uri getUri() {
    return uri;
  }

  public void setUri(Uri uri) {
    this.uri = uri;
  }

  public UploadNotificationConfig getNotificationConfig() {
    return notificationConfig;
  }

  public void setNotificationConfig(UploadNotificationConfig notificationConfig) {
    this.notificationConfig = notificationConfig;
  }

  public String getUploadId() {
    return uploadId;
  }

  @Override
  public void execute() {
    setDefaultHeaders();
    try {
      MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(getContext(), getUrl()).addFileToUpload(uri.getPath(), "file");
      multipartUploadRequest.setMethod(getMethod().toString());
      multipartUploadRequest.setNotificationConfig(notificationConfig);
      for(Map.Entry<String, String> header : getHeaders().entrySet()) {
        multipartUploadRequest.addHeader(header.getKey(), header.getValue());
      }
      for(Map.Entry<String, Object> parameter : getParameters().entrySet()) {
        multipartUploadRequest.addParameter(parameter.getKey(), parameter.getValue().toString());
      }
      this.uploadId = multipartUploadRequest.startUpload();
    } catch(MalformedURLException | FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}