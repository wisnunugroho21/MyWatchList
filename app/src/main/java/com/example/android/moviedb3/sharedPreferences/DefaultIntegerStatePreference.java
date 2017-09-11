package com.example.android.moviedb3.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;

/**
 * Created by nugroho on 30/07/17.
 */

public class DefaultIntegerStatePreference implements IPreferencesUtils<Integer>
{
    Context context;

    public DefaultIntegerStatePreference(Context context) {
        this.context = context;
    }

    @Override
    public void SetData(Integer dataIn, String key)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, dataIn);
        editor.apply();
    }

    @Override
    public Integer GetData(String key, Integer defaultDataOut) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, defaultDataOut);
    }

}
