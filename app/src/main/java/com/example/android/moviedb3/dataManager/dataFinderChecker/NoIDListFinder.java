package com.example.android.moviedb3.dataManager.dataFinderChecker;

import com.example.android.moviedb3.localDatabase.DataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 30/08/17.
 */

public class NoIDListFinder implements IDataFindCheck<ArrayList<String>>
{
    ArrayList<String> idList;
    DataDB<String> dataDB;

    public NoIDListFinder(ArrayList<String> idList, DataDB<String> dataDB) {
        this.idList = idList;
        this.dataDB = dataDB;
    }

    @Override
    public ArrayList<String> CheckData() {

        ArrayList<String> idDatabaseList = dataDB.getAllData();
        ArrayList<String> idDifferenceList = new ArrayList<>();

        if(idDatabaseList != null && !idDatabaseList.isEmpty())
        {
            for (String idDatabase:idDatabaseList)
            {
                boolean isSame = false;

                for (String id:idList)
                {
                    if(id.equals(idDatabase))
                    {
                        isSame = true;
                        break;
                    }
                }

                if(!isSame)
                {
                    idDifferenceList.add(idDatabase);
                }
            }
        }

        return idDifferenceList;
    }

}

