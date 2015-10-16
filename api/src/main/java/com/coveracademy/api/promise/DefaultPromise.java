package com.coveracademy.api.promise;

import com.coveracademy.api.exception.APIException;

import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;
import org.jdeferred.Promise.State;
import org.jdeferred.impl.DeferredObject;

/**
 * Created by sandro on 8/4/15.
 */
public class DefaultPromise<D> extends DeferredObject<D, APIException, State> {

  @Override
  public Promise<D, APIException, State> progress(ProgressCallback<State> callback) {
    Promise<D, APIException, State> promise = super.progress(callback);
    if(isPending()) {
      triggerProgress(State.PENDING);
    }
    return promise;
  }

}
