package com.example.android.moviedb3.supportDataManager.dataAvailable;

/**
 * Created by nugroho on 05/09/17.
 */

public class DataAvailableCheck
{
    public static boolean isDataAvailable(IDataAvailableCheck dataAvailableCheck)
    {
        return dataAvailableCheck.isDataAvailable();
    }
}
