package com.example.android.moviedb3.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nugroho on 10/09/17.
 */

public class DefaultBooleanStatePreference implements IPreferencesUtils<Boolean>
{
    Context context;

    public DefaultBooleanStatePreference(Context context) {
        this.context = context;
    }

    @Override
    public void SetData(Boolean dataIn, String Key)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Key, dataIn);
        editor.apply();
    }

    @Override
    public Boolean GetData(String key, Boolean defaultDataOut)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, defaultDataOut);
    }
}
