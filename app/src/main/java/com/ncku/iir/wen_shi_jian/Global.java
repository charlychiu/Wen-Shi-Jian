package com.ncku.iir.wen_shi_jian;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Global {
    private static SharedPreferences sharedPreferences;

    public static void initPrefInstance(SharedPreferences mPreferences) {
        sharedPreferences = mPreferences;
    }

    public static SharedPreferences getPrefInstance() {
        return sharedPreferences;
    }
}
