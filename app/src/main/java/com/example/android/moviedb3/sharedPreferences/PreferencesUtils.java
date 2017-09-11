package com.example.android.moviedb3.sharedPreferences;

/**
 * Created by nugroho on 10/09/17.
 */

public class PreferencesUtils
{
    public static <Data>void SetData(IPreferencesUtils<Data> preferencesUtils, Data dataIn, String Key)
    {
        preferencesUtils.SetData(dataIn, Key);
    }

    public static <Data>Data GetData(IPreferencesUtils<Data> preferencesUtils, String key, Data defaultDataOut)
    {
        return preferencesUtils.GetData(key, defaultDataOut);
    }
}
