package com.example.android.moviedb3.supportDataManager.dataAvailable;

import com.example.android.moviedb3.supportDataManager.dataComparision.IDataComparision;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public class DefaultDataAvailableCheck<Data> implements IDataAvailableCheck
{
    IDataComparision<Data> dataComparision;
    ArrayList<Data> dataArrayList;
    String idData;

    public DefaultDataAvailableCheck(IDataComparision<Data> dataComparision, ArrayList<Data> dataArrayList, String idData) {
        this.dataComparision = dataComparision;
        this.dataArrayList = dataArrayList;
        this.idData = idData;
    }

    @Override
    public boolean isDataAvailable()
    {
        if(dataArrayList != null && idData != null)
        {
            for (Data data:dataArrayList)
            {
                if(dataComparision.isSame(idData, data))
                {
                    return true;
                }
            }
        }



        return false;
    }
}
