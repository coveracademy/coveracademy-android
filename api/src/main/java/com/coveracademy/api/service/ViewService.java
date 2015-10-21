package com.coveracademy.api.service;

import com.coveracademy.api.model.view.AuditionsView;
import com.coveracademy.api.promise.DefaultPromise;
import com.coveracademy.api.promise.RequestPromise;
import com.coveracademy.api.service.rest.builder.GetBuilder;

/**
 * Created by sandro on 5/28/15.
 */
public class ViewService extends RestService {

  public ViewService() {
    super("/views");
  }

  public DefaultPromise<AuditionsView> auditionsView() {
    GetBuilder builder = getRequestBuilderFactory().get(AuditionsView.class);
    builder.concatPath("/auditions");
    return new RequestPromise<>(builder);
  }

}