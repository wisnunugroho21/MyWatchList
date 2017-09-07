package com.example.android.moviedb3.supportDataManager.dataReplace;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDataComparision;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class BaseDataListReplace<Data extends BaseData> implements IDataReplace
{
    ArrayList<Data> dataArrayList;
    DataDB<Data> dataDB;
    IDataComparision<Data> dataComparision;

    public BaseDataListReplace(ArrayList<Data> dataArrayList, DataDB<Data> dataDB, IDataComparision<Data> dataComparision) {
        this.dataArrayList = dataArrayList;
        this.dataDB = dataDB;
        this.dataComparision = dataComparision;
    }

    public void ReplaceData()
    {
        ArrayList<Data> databaseDataArrayList = dataDB.getAllData();

        if(databaseDataArrayList != null)
        {
            for (Data data:dataArrayList)
            {
                for (Data databaseData:databaseDataArrayList)
                {
                    if(dataComparision.isSame(data.getId(), databaseData))
                    {
                        dataDB.removeData(databaseData.getId());
                    }
                }
            }

            for (Data data:dataArrayList)
            {
                dataDB.addData(data);
            }
        }

        else
        {
            for (Data data:dataArrayList)
            {
                dataDB.addData(data);
            }
        }
    }
}
