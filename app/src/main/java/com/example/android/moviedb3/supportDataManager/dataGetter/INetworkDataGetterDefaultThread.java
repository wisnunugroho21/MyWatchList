package com.example.android.moviedb3.supportDataManager.dataGetter;

/**
 * Created by nugroho on 07/09/17.
 */

public interface INetworkDataGetterDefaultThread<Data>
{
    Data getData(String... url);
}
