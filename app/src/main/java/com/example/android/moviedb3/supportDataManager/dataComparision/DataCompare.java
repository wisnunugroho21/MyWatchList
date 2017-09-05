package com.example.android.moviedb3.supportDataManager.dataComparision;

/**
 * Created by nugroho on 05/09/17.
 */

public class DataCompare
{
    public static <Data>boolean isSame(IDataComparision<Data> dataComparision, String idComparedData, Data data)
    {
        return dataComparision.isSame(idComparedData, data);
    }
}
