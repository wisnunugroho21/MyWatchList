package com.example.android.moviedb3.dataManager_desperate.dataReplace;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.IntermedieteData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class IntermedieteDataListReplace<Data extends IntermedieteData> implements IReplaceData
{
    public static final int CHECK_FIRST_ID = 587;
    public static final int CHECK_SECOND_ID = 103;

    ArrayList<Data> dataArrayList;
    DataDB<Data> dataDB;
    int replaceMode;

    public IntermedieteDataListReplace(ArrayList<Data> dataArrayList, DataDB<Data> dataDB, int replaceMode) {
        this.dataArrayList = dataArrayList;
        this.dataDB = dataDB;
        this.replaceMode = replaceMode;
    }

    @Override
    public void ReplaceDatabase()
    {
        ArrayList<Data> databaseDataArrayList = dataDB.getAllData();

        if(dataArrayList != null && !dataArrayList.isEmpty())
        {
            if(databaseDataArrayList != null)
            {
                switch (replaceMode)
                {
                    case CHECK_FIRST_ID :
                        for (Data databaseData:databaseDataArrayList)
                        {
                            if(databaseData.getFirstID().equals(dataArrayList.get(0).getFirstID()))
                            {
                                dataDB.removeData(databaseData.getId());
                            }
                        }
                        break;
                    case CHECK_SECOND_ID :
                        for (Data databaseData:databaseDataArrayList)
                        {
                            if(databaseData.getSecondID().equals(dataArrayList.get(0).getSecondID()))
                            {
                                dataDB.removeData(databaseData.getId());
                            }
                        }
                        break;
                }

                for (Data data:dataArrayList)
                {
                    dataDB.addData(data);

                }
            }
        }

    }
}
