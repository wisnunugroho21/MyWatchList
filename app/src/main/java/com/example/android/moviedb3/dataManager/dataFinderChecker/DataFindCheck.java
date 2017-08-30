package com.example.android.moviedb3.dataManager.dataFinderChecker;

/**
 * Created by nugroho on 28/08/17.
 */

public class DataFindCheck
{
    public static <Data> Data CheckData(IDataFindCheck<Data> dataEraser)
    {
         return dataEraser.CheckData();
    }
}
