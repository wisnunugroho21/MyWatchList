package com.example.android.moviedb3.supportDataManager.dataComparision;

/**
 * Created by nugroho on 04/09/17.
 */

public interface IDataComparision<Data>
{
    boolean isSame(String idComparedData, Data data);
}
