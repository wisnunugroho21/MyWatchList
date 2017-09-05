package com.example.android.moviedb3.dataManager_desperate.DataCheck;

/**
 * Created by nugroho on 28/08/17.
 */

public interface IDataCheck<Data>
{
    boolean isDataAvailable(String idData);
    Data getDataSame(String idData);
    Data getDataDifferent(String idData);
}
