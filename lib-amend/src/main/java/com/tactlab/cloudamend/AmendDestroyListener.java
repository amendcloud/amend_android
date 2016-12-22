package com.tactlab.cloudamend;

/**
 * Created by deepak on 28-09-2016.
 */
public interface AmendDestroyListener {
    void onSuccess(int statusCode, int reqCode, String message);

    void onError(int statusCode, int reqCode, Exception e);
}
