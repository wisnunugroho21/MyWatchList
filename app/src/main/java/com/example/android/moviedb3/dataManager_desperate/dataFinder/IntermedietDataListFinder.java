package com.example.android.moviedb3.dataManager_desperate.dataFinder;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.IntermedieteData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class IntermedietDataListFinder<Data extends IntermedieteData> implements IDataFind<ArrayList<Data>>
{
    public static final int CHECK_FIRST_ID = 587;
    public static final int CHECK_SECOND_ID = 103;

    String idData;
    int checkMode;
    DataDB<Data> dataDB;

    public IntermedietDataListFinder(String idData, int checkMode, DataDB<Data> dataDB) {
        this.idData = idData;
        this.checkMode = checkMode;
        this.dataDB = dataDB;
    }

    @Override
    public ArrayList<Data> FindData() {

        ArrayList<Data> dataDBArrayList = dataDB.getAllData();
        ArrayList<Data> sameDataArrayList = new ArrayList<>();

        if(dataDBArrayList != null && !dataDBArrayList.isEmpty())
        {
            switch (checkMode)
            {
                case CHECK_FIRST_ID :
                    for (Data data : dataDBArrayList)
                    {
                        if (data.getFirstID().equals(idData))
                        {
                            sameDataArrayList.add(data);
                        }
                    }
                    break;

                case CHECK_SECOND_ID :
                    for (Data data : dataDBArrayList)
                    {
                        if (data.getSecondID().equals(idData))
                        {
                            sameDataArrayList.add(data);
                        }
                    }
                    break;
            }
        }

        return sameDataArrayList;
    }
}
