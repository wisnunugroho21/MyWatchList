package com.example.android.moviedb3.supportDataManager.dataComparision;

import com.example.android.moviedb3.movieDB.IntermedieteData;

/**
 * Created by nugroho on 04/09/17.
 */

public class IntermedieteDataCompare<Data extends IntermedieteData> implements IDataComparision<Data>
{
    public static final int CHECK_FIRST_ID = 587;
    public static final int CHECK_SECOND_ID = 103;

    int checkMode = CHECK_FIRST_ID;

    public IntermedieteDataCompare(int checkMode) {
        this.checkMode = checkMode;
    }

    @Override
    public boolean isSame(String idComparedData, Data data)
    {
        switch (checkMode)
        {
            case CHECK_FIRST_ID : return idComparedData.equals(data.getFirstID());
            case CHECK_SECOND_ID : return idComparedData.equals(data.getSecondID());
            default: return false;
        }
    }
}
