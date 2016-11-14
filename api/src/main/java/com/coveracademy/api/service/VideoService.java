package com.coveracademy.api.service;

import android.content.Context;
import android.net.Uri;

import com.coveracademy.api.exception.APIException;
import com.coveracademy.api.model.Comment;
import com.coveracademy.api.model.Contest;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.MultipartRequest;
import com.coveracademy.api.service.rest.Request;

import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.HashMap;
import java.util.Map;

public class VideoService extends RestService {

  VideoService(Context context) {
    super(context, "/videos");
  }

  public DefaultPromise<Void> like(Video video) {
    Request<Void> request = getRequestFactory().post();
    request.concatPath(video.getId());
    request.concatPath("likes");
    return new RequestPromise<>(request);
  }

  public DefaultPromise<Void> dislike(Video video) {
    Request<Void> request = getRequestFactory().delete();
    request.concatPath(video.getId());
    request.concatPath("likes");
    return new RequestPromise<>(request);
  }

  public DefaultPromise<Comment> comment(Video video, String message) {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("message", message);
    Request<Comment> request = getRequestFactory().post(attributes, Comment.class);
    request.concatPath(video.getId());
    request.concatPath("comments");
    return new RequestPromise<>(request);
  }

  public DefaultPromise<String> upload(Uri videoUri, Contest contest, UploadNotificationConfig notificationConfig) {
    DefaultPromise<String> promise = new DefaultPromise<>();
    try {
      MultipartRequest request = getRequestFactory().upload(videoUri);
      if(contest != null) {
        request.addParameter("contest", contest.getId());
      }
      request.setNotificationConfig(notificationConfig);
      request.concatPath("uploads");
      request.execute();
      promise.resolve(request.getUploadId());
    } catch(Exception e) {
      promise.reject(new APIException(e));
    }
    return promise;
  }
}