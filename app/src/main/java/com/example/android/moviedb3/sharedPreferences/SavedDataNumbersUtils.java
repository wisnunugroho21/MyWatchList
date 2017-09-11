package com.example.android.moviedb3.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nugroho on 08/09/17.
 */

public class SavedDataNumbersUtils
{
    private static final String Key = "savedDataNumber";

    public static void SetNumbersDataHasBeenObtained(int numbersDataHasBeenObtained, Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Key, numbersDataHasBeenObtained);
        editor.apply();
    }

    public static int GetNumbersDataHasBeenObtained(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(Key, 0);
    }
}
