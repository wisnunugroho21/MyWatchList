package com.example.android.moviedb3.dataManager_desperate.dataCheck;

/**
 * Created by nugroho on 28/08/17.
 */

public class DataCheck
{
    public static <Data> Data CheckData(IDataCheck<Data> dataEraser)
    {
         return dataEraser.CheckData();
    }
}
