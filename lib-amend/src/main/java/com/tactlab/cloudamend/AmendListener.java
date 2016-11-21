package com.tactlab.cloudamend;

/**
 * Created by deepak on 28-09-2016.
 */
public interface AmendListener {
    void onSuccess(int statusCode, int reqCode, String imageId) throws Exception;
    void onError(int statusCode, int reqCode, Exception e);
}
