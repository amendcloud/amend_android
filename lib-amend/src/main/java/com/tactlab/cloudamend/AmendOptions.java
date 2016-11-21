package com.tactlab.cloudamend;

/**
 * Created by deepak on 26-09-2016.
 */

public class AmendOptions {
    private String option = "";


    public AmendOptions effect(Effect _effect) {
        checkOption();
        option += _effect.getOptions();
        return this;
    }

    public AmendOptions flip(String _flip) {
        checkOption();
        option += _flip;
        return this;
    }

    public AmendOptions grayscale(boolean _grayscale) {
        if (_grayscale) {
            checkOption();
            option += "grayscale";
        }
        return this;
    }

    public AmendOptions invert(boolean _invert) {
        if (_invert) {
            checkOption();
            option += "invert";
        }
        return this;
    }

    public AmendOptions quality(int _quality) {
        checkOption();
        option += "quality_" + _quality;
        return this;
    }

    public AmendOptions radius(int _radius) {
        checkOption();
        if (_radius == Amend.MAX) {
            option += "r_max";
        } else {
            option += "r_" + _radius;
        }
        return this;
    }

    public AmendOptions rotate(int _rotate) {
        checkOption();
        option += "rotate_" + _rotate;
        return this;
    }

    public AmendOptions transform(Transform _transform) {
        checkOption();
        option += _transform.getOptions();
        return this;
    }

    public AmendOptions overlay(Overlay _watermark)
    {
        option += _watermark.getOptions();
        return this;
    }

    String getOptions() {
        return option;
    }

    private void checkOption() {
        if (!option.isEmpty()) {
            option += "/";
        }
    }
}
