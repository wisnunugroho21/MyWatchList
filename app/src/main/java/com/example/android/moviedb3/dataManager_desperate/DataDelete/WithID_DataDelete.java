package com.example.android.moviedb3.dataManager_desperate.DataDelete;

import com.example.android.moviedb3.dataManager_desperate.DataCheck.IDataCheck;
//import com.example.android.moviedb3.dataManager_desperate.DataCheck.DataCheck;
import com.example.android.moviedb3.localDatabase.DataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public class WithID_DataDelete<Data> implements IDataDelete
{
    DataDB<Data> dataDB;
    IDataCheck<Data> dataCheck;

    ArrayList<String> idList;

    public WithID_DataDelete(DataDB<Data> dataDB, ArrayList<String> idList, IDataCheck<Data> dataCheck) {
        this.dataDB = dataDB;
        this.idList = idList;
        this.dataCheck = dataCheck;
    }

    public void DeleteData()
    {
        for (String id:idList)
        {
            /*if(DataCheck.CheckData(dataCheck, id) != null)
            {
                dataDB.removeData(id);
            }*/
        }
    }
}
