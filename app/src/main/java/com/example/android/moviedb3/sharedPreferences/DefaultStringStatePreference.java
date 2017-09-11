package com.example.android.moviedb3.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nugroho on 10/09/17.
 */

public class DefaultStringStatePreference implements IPreferencesUtils<String>
{
    Context context;

    public DefaultStringStatePreference(Context context) {
        this.context = context;
    }

    @Override
    public void SetData(String dataIn, String Key)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Key, dataIn);
        editor.apply();
    }

    @Override
    public String GetData(String key, String defaultDataOut)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, defaultDataOut);
    }
}
