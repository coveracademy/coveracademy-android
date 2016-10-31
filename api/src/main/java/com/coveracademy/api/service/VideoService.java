package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.model.Comment;
import com.coveracademy.api.model.Video;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.builder.DeleteBuilder;
import com.coveracademy.api.service.rest.builder.PostBuilder;

import java.util.HashMap;
import java.util.Map;

public class VideoService extends RestService {

  VideoService(Context context) {
    super(context, "/videos");
  }

  public DefaultPromise<Void> like(Video video) {
    PostBuilder builder = getRequestBuilderFactory().post();
    builder.concatPath("/").concatPath(video.getId()).concatPath("/likes");
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<Void> dislike(Video video) {
    DeleteBuilder builder = getRequestBuilderFactory().delete();
    builder.concatPath("/").concatPath(video.getId()).concatPath("/likes");
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<Comment> comment(Video video, String message) {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("message", message);
    PostBuilder builder = getRequestBuilderFactory().post(attributes, Comment.class);
    builder.concatPath("/").concatPath(video.getId()).concatPath("/comments");
    return new RequestPromise<>(builder);
  }
}