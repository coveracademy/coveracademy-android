package com.coveracademy.api.promise;

import com.coveracademy.api.exception.APIException;

import org.jdeferred.Deferred;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.impl.DeferredObject;

public class Promise<D> extends DeferredObject<D, APIException, Promise.Progress> {

  @Override
  public org.jdeferred.Promise progress(ProgressCallback<Progress> callback) {
    org.jdeferred.Promise promise = super.progress(callback);
    if(isPending()) {
      triggerProgress(Progress.PENDING);
    } else if(isRejected() || isResolved()) {
      triggerProgress(Progress.PROCESSED);
    }
    return promise;
  }

  @Override
  public Deferred<D, APIException, Progress> resolve(D resolve) {
    try {
      triggerProgress(Progress.RESOLVED);
      return super.resolve(resolve);
    } finally {
      triggerProgress(Progress.PROCESSED);
    }
  }

  @Override
  public Deferred<D, APIException, Progress> reject(APIException reject) {
    try {
      triggerProgress(Progress.REJECTED);
      return super.reject(reject);
    } finally {
      triggerProgress(Progress.PROCESSED);
    }
  }

  public void resolve() {
    resolve((D) null);
  }

  public void resolve(Promise<D> promise) {
    promise.then(new DoneCallback<D>() {
      @Override
      public void onDone(D result) {
        resolve(result);
      }
    }).fail(new FailCallback<APIException>() {
      @Override
      public void onFail(APIException result) {
        reject(result);
      }
    });
  }

  public enum Progress {

    PENDING, REJECTED, RESOLVED, PROCESSED;

  }
}