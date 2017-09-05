package com.example.android.moviedb3.supportDataManager.dataDelete;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDataComparision;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class IdListDelete implements IDataDelete
{
    IDataComparision<String> dataComparision;
    DataDB<String> dataDB;
    ArrayList<String> idList;

    String idData;

    public IdListDelete(IDataComparision<String> dataComparision, DataDB<String> dataDB, ArrayList<String> idList) {
        this.dataComparision = dataComparision;
        this.dataDB = dataDB;
        this.idList = idList;
    }

    public IdListDelete(IDataComparision<String> dataComparision, DataDB<String> dataDB, String idData) {
        this.dataComparision = dataComparision;
        this.dataDB = dataDB;
        this.idData = idData;
    }

    @Override
    public void Delete()
    {
        ArrayList<String> dataArrayList = dataDB.getAllData();

        if(idList != null)
        {
            for (String data:dataArrayList)
            {
                for (String id:idList)
                {
                    if(dataComparision.isSame(id, data))
                    {
                        dataDB.removeData(data);
                    }
                }
            }
        }

        else
        {
            for (String data:dataArrayList)
            {
                if(dataComparision.isSame(idData, data))
                {
                    dataDB.removeData(data);
                }
            }
        }
    }
}
