package com.example.android.moviedb3.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nugroho on 07/09/17.
 */

public class CheckFirstTimeUtils
{
    private static final String Key = "checkFirstTime";

    public static void SetCheckFirstTime(boolean isFirstTime, Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Key, isFirstTime);
        editor.apply();
    }

    public static boolean IsCheckFirstTime(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(Key, true);
    }
}
