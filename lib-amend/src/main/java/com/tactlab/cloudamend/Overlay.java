package com.tactlab.cloudamend;

/**
 * Created by deepak on 26-09-2016.
 */

public class Overlay {


    private String option = "";

    public Overlay color(String _color) {

        checkOption();
        option += "c_" + _color;
        return this;
    }

    public Overlay image(String _image) {
        checkOption();
        option += "oi-" + _image;
        return this;
    }


    public Overlay size(int _size) {
        checkOption();
        option += "size_" + _size;
        return this;
    }

    public Overlay style(String _style) {
        checkOption();
        option += "style_" + _style;
        return this;
    }

    public Overlay text(String _text) {
        checkOption();
        option += "ot-" + _text;
        return this;
    }

    public Overlay x(int _x) {
        checkOption();
        option += "x_" + _x;
        return this;
    }

    public Overlay y(int _y) {
        checkOption();
        option += "y_" + _y;
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
