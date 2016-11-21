package com.tactlab.cloudamend;

/**
 * Created by deepak on 26-09-2016.
 */

public class Effect {

    private String option = "";

    public Effect brightness(int _brightness) {
        checkOption();
        option += "bright_" + _brightness;
        return this;
    }

    public Effect contrast(int _contrast) {
        checkOption();
        option += "contrast_" + _contrast;
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
