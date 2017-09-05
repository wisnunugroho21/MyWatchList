package com.example.android.moviedb3.supportDataManager.dataComparision;

/**
 * Created by nugroho on 04/09/17.
 */

public class IDCompare implements IDataComparision<String>
{
    @Override
    public boolean isSame(String idComparedData, String s)
    {
        return idComparedData.equals(s);
    }
}
