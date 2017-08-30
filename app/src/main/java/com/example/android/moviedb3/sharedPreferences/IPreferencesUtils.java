package com.example.android.moviedb3.sharedPreferences;

import android.content.Context;

/**
 * Created by nugroho on 30/07/17.
 */

public interface IPreferencesUtils<Data>
{
    void SetData(Data dataIn, String Key);
    Data GetData(String key, Data defaultDataOut);
}
