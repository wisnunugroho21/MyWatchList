package com.example.android.moviedb3.supportDataManager.dataComparision;

import com.example.android.moviedb3.movieDB.DependencyData;

/**
 * Created by nugroho on 04/09/17.
 */

public class DepedencyDataCompare<Data extends DependencyData> implements IDataComparision<Data>
{
    @Override
    public boolean isSame(String idComparedData, Data data)
    {
        return idComparedData.equals(data.getIDDependent());
    }
}
