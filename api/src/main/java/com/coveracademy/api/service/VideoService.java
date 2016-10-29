package com.coveracademy.api.service;

import android.content.Context;

import com.coveracademy.api.model.Video;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.builder.DeleteBuilder;
import com.coveracademy.api.service.rest.builder.PostBuilder;

public class VideoService extends RestService {

  VideoService(Context context) {
    super(context, "/videos");
  }

  public DefaultPromise<Void> like(Video video) {
    PostBuilder builder = getRequestBuilderFactory().post();
    builder.concatPath("/likes/").concatPath(video.getId());
    return new RequestPromise<>(builder);
  }

  public DefaultPromise<Void> dislike(Video video) {
    DeleteBuilder builder = getRequestBuilderFactory().delete();
    builder.concatPath("/likes/").concatPath(video.getId());
    return new RequestPromise<>(builder);
  }
}