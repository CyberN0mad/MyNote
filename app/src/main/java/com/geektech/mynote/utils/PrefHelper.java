package com.geektech.mynote.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper {
    public static final String STORAGE_NAME = "StorageName";

    private static SharedPreferences sharedPreferences = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init(Context cntxt) {
        context = cntxt;
    }

    private static void init() {
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void setOnBoardIsShown() {
        if (sharedPreferences == null) {
            init();
        }
        editor.putBoolean("onboard", true);
        editor.apply();
    }

    public static boolean getOnBoardIsShown() {
        if (sharedPreferences == null) {
            init();
        }
        return sharedPreferences.getBoolean("onboard", false);
    }
}
