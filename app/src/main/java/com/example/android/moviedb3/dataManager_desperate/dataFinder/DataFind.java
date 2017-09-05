package com.example.android.moviedb3.dataManager_desperate.dataFinder;

/**
 * Created by nugroho on 03/09/17.
 */

public class DataFind
{
    public static <Data> Data FindData(IDataFind<Data> dataFind)
    {
        return dataFind.FindData();
    }
}
