package com.tactlab.cloudamend;

import android.content.Context;

/**
 * Created by deepak on 26-09-2016.
 */

public class Amend {

    public static final String FIT_XY = "fit_xy";
    public static final String FIT_INSIDE = "fit_inside";
    public static final String FIT_WIDTH = "fit_width";
    public static final String FIT_FILL = "fit_fill";
    public static final String FIT_HEIGHT = "fit_height";

    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String CENTER = "center";

    public static final String NORMAL = "normal";
    public static final String BOLD = "bold";
    public static final String ITALIC = "italic";
    public static final String UNDERLINE = "underline";

    public static final int MAX = 10000;
    public static final String FLIP_X = "flip_x";
    public static final String FLIP_Y = "flip_y";
    public static final String FLIP_XY = "flip_xy";

    static final String IMAGE_NAME = "ImageName";
    static final String MESSAGE = "Message";
    static final String TAG = "Amend";
    static String amendName = null;
    static String accessKey = null;
    static String accessSecret = null;

    public static RequestManager with(Context context) {
        return new RequestManager(context);
    }

    public static void setAmendName(String amendName) {
        Amend.amendName = amendName;
    }

    public static void setCredentials(String accessKey, String accessSecret) {
        Amend.accessKey = accessKey;
        Amend.accessSecret = accessSecret;
    }

}
