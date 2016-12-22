package com.tactlab.cloudamend;

/**
 * Created by deepak on 28-09-2016.
 */
public interface AmendRenameListener {
    void onSuccess(int statusCode, int reqCode, String NewImageName);

    void onError(int statusCode, int reqCode, Exception e);
}
