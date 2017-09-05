package com.example.android.moviedb3.dataManager_desperate.DataReplace;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public class AllDataReplace<Data extends BaseData> implements IDataReplace
{
    DataDB<Data> dataDB;
    ArrayList<Data> dataArrayList;

    public AllDataReplace(DataDB<Data> dataDB, ArrayList<Data> dataArrayList) {
        this.dataDB = dataDB;
        this.dataArrayList = dataArrayList;
    }

    public void ReplaceData()
    {
        dataDB.removeAllData();

        for (Data data:dataArrayList)
        {
            dataDB.addData(data);
        }
    }
}
