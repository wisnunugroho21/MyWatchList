package com.example.android.moviedb3.dataManager_desperate.dataCheck;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.IntermedieteData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class SameID_IntermedieteListCheck<Data extends IntermedieteData> implements IDataCheck<Boolean>
{
    public static final int CHECK_FIRST_ID = 587;
    public static final int CHECK_SECOND_ID = 103;

    int checkMode;
    DataDB<Data> dataDB;
    String idData;

    public SameID_IntermedieteListCheck(int checkMode, DataDB<Data> dataDB, String idData) {
        this.checkMode = checkMode;
        this.dataDB = dataDB;
        this.idData = idData;
    }

    @Override
    public Boolean CheckData() {
        ArrayList<Data> dataArrayList = dataDB.getAllData();

        if(dataArrayList != null && !dataArrayList.isEmpty())
        {
            switch (checkMode)
            {
                case CHECK_FIRST_ID :
                    for (Data data : dataArrayList)
                    {
                        if (idData.equals(data.getFirstID()))
                        {
                            return true; //ternyata movieID masih ada di list favorite, top rate, dll
                        }
                    }
                    break;

                case CHECK_SECOND_ID :
                    for (Data data : dataArrayList)
                    {
                        if (idData.equals(data.getSecondID()))
                        {
                            return true;
                        }
                    }
                    break;
            }
        }

        return false;
    }
}
