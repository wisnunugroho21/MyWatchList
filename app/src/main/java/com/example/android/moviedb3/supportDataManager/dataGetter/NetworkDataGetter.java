package com.example.android.moviedb3.supportDataManager.dataGetter;

import android.support.annotation.NonNull;

/**
 * Created by nugroho on 23/08/17.
 */

public class NetworkDataGetter
{
    public static  <Data> void GetDataAsyncTask(INetworkDataGetterAsyncTask<Data> networkDataGetter, @NonNull String... url)
    {
        if(url.length >= 1)
        {
            networkDataGetter.execute(url);
        }
    }

    public static <Data> Data GetDataDefaultThread(INetworkDataGetterDefaultThread<Data> networkDataGetterDefaultThread, @NonNull String... url)
    {
        if(url.length >= 1)
        {
            return networkDataGetterDefaultThread.getData(url);
        }

        else
        {
            return null;
        }
    }
}
