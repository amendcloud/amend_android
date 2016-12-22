package com.tactlab.cloudamend;

/**
 * Created by deepak on 28-09-2016.
 */
public interface AmendUploadListener {
    void onSuccess(int statusCode, int reqCode, AmendResponse response) throws Exception;

    void onError(int statusCode, int reqCode, Exception e);
}
