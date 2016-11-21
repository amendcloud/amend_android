package com.tactlab.cloudamend;

import android.graphics.Bitmap;

/**
 * Created by admin on 26/10/16.
 */
public interface RequestListener {
    void onStarted();

    void onSuccess(Bitmap d);

    void onError(Exception e);
}
