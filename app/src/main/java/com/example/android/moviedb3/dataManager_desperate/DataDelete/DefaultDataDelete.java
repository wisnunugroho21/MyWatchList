package com.example.android.moviedb3.dataManager_desperate.DataDelete;

//import com.example.android.moviedb3.dataManager_desperate.DataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.DataCheck.IDataCheck;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class DefaultDataDelete<Data extends BaseData> implements IDataDelete
{
    DataDB<Data> dataDB;
    IDataCheck<Data> dataCheck;
    ArrayList<Data> dataArrayList;

    public DefaultDataDelete(DataDB<Data> dataDB, IDataCheck<Data> dataCheck, ArrayList<Data> dataArrayList) {
        this.dataDB = dataDB;
        this.dataCheck = dataCheck;
        this.dataArrayList = dataArrayList;
    }

    public void DeleteData()
    {
        for (Data data:dataArrayList)
        {
            /*if(DataCheck.CheckData(dataCheck, data.getId()) != null)
            {
                dataDB.removeData(data.getId());
            }*/
        }
    }
}
