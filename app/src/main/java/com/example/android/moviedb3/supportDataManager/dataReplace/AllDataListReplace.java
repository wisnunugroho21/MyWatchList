package com.example.android.moviedb3.supportDataManager.dataReplace;

import com.example.android.moviedb3.localDatabase.DataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class AllDataListReplace<Data> implements IDataReplace
{
    DataDB<Data> dataDB;
    ArrayList<Data> dataList;

    public AllDataListReplace(DataDB<Data> dataDB, ArrayList<Data> dataList) {
        this.dataDB = dataDB;
        this.dataList = dataList;
    }

    public void ReplaceData()
    {
        dataDB.removeAllData();

        for (Data data:dataList)
        {
            dataDB.addData(data);
        }
    }
}
