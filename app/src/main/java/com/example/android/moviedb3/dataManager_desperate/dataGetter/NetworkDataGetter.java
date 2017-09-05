package com.example.android.moviedb3.dataManager_desperate.dataGetter;

import android.support.annotation.NonNull;

/**
 * Created by nugroho on 23/08/17.
 */

public class NetworkDataGetter
{
    public static  <Data> void GetData(INetworkDataGetter<Data> networkDataGetter, @NonNull String... url)
    {
        if(url.length >= 1)
        {
            networkDataGetter.execute(url);
        }
    }
}
