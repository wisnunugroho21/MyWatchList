package com.example.android.moviedb3.dataManager_desperate.dataCheck;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 30/08/17.
 */

public class SameID_DataListCheck<Data extends BaseData> implements IDataCheck<Boolean> {
    DataDB<Data> dataDB;
    String idData;

    public SameID_DataListCheck(DataDB<Data> dataDB, String idData) {
        this.dataDB = dataDB;
        this.idData = idData;
    }

    @Override
    public Boolean CheckData() {
        ArrayList<Data> dataArrayList = dataDB.getAllData();

        if(dataArrayList != null && !dataArrayList.isEmpty())
        {
            for (Data data : dataArrayList)
            {
                if (idData.equals(data.getId()))
                {
                    return true; //ternyata movieID masih ada di list favorite, top rate, dll
                }
            }
        }

        return false;
    }
}

