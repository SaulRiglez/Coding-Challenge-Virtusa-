package com.yoprogramo.isspasses.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.yoprogramo.isspasses.constants.Commons;

public class Prefs {

    private static final String TAG = Prefs.class.getSimpleName();

    private static Prefs instance;

    @SuppressWarnings("WeakerAccess")
    protected SharedPreferences sharedPrefs;

    public Prefs(final Context context) {
        this.sharedPrefs = context.getSharedPreferences(Commons.MAIN_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void init(final Context context) {
        if (context != null) {
            instance = new Prefs(context);
        }
    }

    public static Prefs getInstance() {
        if (instance == null) {
            Log.e(TAG, "Prefs instance is null, did you forget to call init?");
        }

        return instance;
    }

    public void setShowLocationSettings(final boolean openSetting) {
        setBooleanPref(Commons.OPEN_SETTINGS_LOCATION, openSetting);
    }

    public boolean getShowLocationSettings() {
        return getBooleanPref(Commons.OPEN_SETTINGS_LOCATION, false);
    }


    public void setBooleanPref(final String name, final boolean value) {
        sharedPrefs.edit().putBoolean(name, value).apply();
    }

    public boolean getBooleanPref(final String name, final boolean defaultValue) {
        return sharedPrefs.getBoolean(name, defaultValue);
    }
}
