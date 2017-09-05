package com.example.android.moviedb3.dataManager_desperate.DataReplace;

//import com.example.android.moviedb3.dataManager_desperate.DataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.DataCheck.IDataCheck;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class SameDataReplace<Data extends BaseData> implements IDataReplace
{
    DataDB<Data> dataDB;
    ArrayList<Data> dataArrayList;
    IDataCheck<Data> dataCheck;

    public SameDataReplace(DataDB<Data> dataDB, ArrayList<Data> dataArrayList, IDataCheck<Data> dataCheck) {
        this.dataDB = dataDB;
        this.dataArrayList = dataArrayList;
        this.dataCheck = dataCheck;
    }

    public void ReplaceData()
    {
        for (Data data:dataArrayList)
        {
            /*if(DataCheck.CheckData(dataCheck, data.getId()) != null)
            {
                dataDB.removeData(data.getId());
            }*/

            dataDB.addData(data);
        }
    }
}
