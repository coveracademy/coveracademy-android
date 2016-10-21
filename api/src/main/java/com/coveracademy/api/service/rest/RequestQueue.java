package com.coveracademy.api.service.rest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

public class RequestQueue {

  private static RequestQueue instance;
  private static Context context;

  private com.android.volley.RequestQueue queue;

  private RequestQueue() {
    if(context == null) {
      throw new RuntimeException("You need to initialize the context before getting a queue instance");
    }
    queue = Volley.newRequestQueue(context);
  }

  public static void useContext(Context ctx) {
    context = ctx;
  }

  public static RequestQueue getInstance() {
    if(instance == null) {
      synchronized(RequestQueue.class) {
        if(instance == null) {
          instance = new RequestQueue();
        }
      }

    }
    return instance;
  }

  public void push(Request<?> req) {
    queue.add(req);
  }

  public void cancelAll(Object tag) {
    queue.cancelAll(tag);
  }
}
