package com.example.android.moviedb3.supportDataManager.dataDelete;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDataComparision;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class DataListDelete<Data extends BaseData> implements IDataDelete
{
    IDataComparision<Data> dataComparision;
    DataDB<Data> dataDB;
    ArrayList<String> idList;

    String idData;

    public DataListDelete(IDataComparision<Data> dataComparision, DataDB<Data> dataDB, ArrayList<String> idList) {
        this.dataComparision = dataComparision;
        this.dataDB = dataDB;
        this.idList = idList;
    }

    public DataListDelete(IDataComparision<Data> dataComparision, DataDB<Data> dataDB, String idData) {
        this.dataComparision = dataComparision;
        this.dataDB = dataDB;
        this.idData = idData;
    }

    @Override
    public void Delete()
    {
        ArrayList<Data> dataArrayList = dataDB.getAllData();

        if(idList != null)
        {
            if(dataArrayList != null)
            {
                for (Data data:dataArrayList)
                {
                    for (String id:idList)
                    {
                        if(dataComparision.isSame(id, data))
                        {
                            dataDB.removeData(data.getId());
                        }
                    }
                }
            }
        }

        else
        {
            if(idData != null && dataArrayList != null)
            {
                for (Data data:dataArrayList)
                {
                    if(dataComparision.isSame(idData, data))
                    {
                        dataDB.removeData(data.getId());
                    }
                }
            }
        }


    }
}
