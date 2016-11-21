package com.tactlab.cloudamend;

import android.util.Log;

/**
 * Created by deepak on 26-09-2016.
 */

public class Transform {

    private String option = "";

    public Transform align(String _align) {
        checkOption();
        option += "align_" + _align;
        return this;
    }

    public Transform color(String _color) {

        checkOption();
        option += "c_" + _color;
        return this;
    }

    public Transform height(int _height) {
        if (_height > 6000) {
            Log.e(Amend.TAG, "Height can not be more than 6000");
            _height = 6000;
        }
        checkOption();
        option += "h_" + _height;
        return this;
    }

    public Transform fit(String _fit) {

        checkOption();
        option += _fit;
        return this;
    }

    public Transform width(int _width) {


        if (_width > 6000) {
            Log.e(Amend.TAG, "Width can not be more than 6000");
            _width = 6000;
        }
        checkOption();
        option += "w_" + _width;

        return this;
    }

    public Transform x(int _x) {

        checkOption();
        option += "x_" + _x;
        return this;
    }

    public Transform y(int _y) {

        checkOption();
        return this;
    }

    String getOptions() {
        return option;
    }

    private void checkOption() {
        if (!option.isEmpty()) {
            option += ",";
        }
    }
}
