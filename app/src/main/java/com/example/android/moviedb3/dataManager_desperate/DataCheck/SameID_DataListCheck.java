package com.example.android.moviedb3.dataManager_desperate.DataCheck;

import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 30/08/17.
 */

/*
public class SameID_DataListCheck<Data extends BaseData> implements IDataCheck<Data> {
    private ArrayList<Data> comparisondataArrayList;

    public SameID_DataListCheck(ArrayList<Data> comparisondataArrayList) {
        this.comparisondataArrayList = comparisondataArrayList;
    }

    @Override
    public boolean isDataAvailable(String idData) {
        if(comparisondataArrayList != null && !comparisondataArrayList.isEmpty())
        {
            for (Data data : comparisondataArrayList)
            {
                if (idData.equals(data.getId()))
                {
                    return true; //ternyata movieID masih ada di list favorite, top rate, dll
                }
            }
        }

        return false;
    }

    @Override
    public Data getDataSame(String idData) {
        if(comparisondataArrayList != null && !comparisondataArrayList.isEmpty())
        {
            for (Data data : comparisondataArrayList)
            {
                if (idData.equals(data.getId()))
                {
                    return data; //ternyata movieID masih ada di list favorite, top rate, dll
                }
            }
        }

        return null;
    }
}

*/
