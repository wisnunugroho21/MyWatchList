package com.example.android.moviedb3.dataManager_desperate.DataReplace;

import com.example.android.moviedb3.localDatabase.DataDB;
//import com.example.android.moviedb3.dataManager_desperate.DataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.DataCheck.IDataCheck;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public class SameIDDataReplace implements IDataReplace
{
    DataDB<String> dataDB;
    ArrayList<String> idList;
    IDataCheck<String> dataCheck;

    public SameIDDataReplace(DataDB<String> dataDB, ArrayList<String> idList, IDataCheck<String> dataCheck) {
        this.dataDB = dataDB;
        this.idList = idList;
        this.dataCheck = dataCheck;
    }

    public void ReplaceData()
    {
        for (String id:idList)
        {
            /*if(DataCheck.CheckData(dataCheck, id) != null)
            {
                dataDB.removeData(id);
            }*/

            dataDB.addData(id);
        }
    }
}

