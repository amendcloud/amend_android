package com.tactlab.cloudamendsample.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {
    private static final String PREF_NAME = "AMEND";

    private SharedPreferences sp;

    public SharedPreference(Context con) {
        sp = con.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public void setValueString(String paramName, String paramValue) {
        Editor et = sp.edit();
        et.putString(paramName, paramValue);
        et.commit();
    }

    public String getValueString(String paramName) {
        return sp.getString(paramName, "");
    }
}
