package com.example.android.moviedb3.dataManager_desperate.SameDataFinder;

import com.example.android.moviedb3.dataManager_desperate.DataCheck.IDataCheck;
//import com.example.android.moviedb3.dataManager_desperate.DataCheck.DataCheck;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public class IDListSameDataFinder implements ISameDataFinder
{
    ArrayList<String> searchedDataList;
    IDataCheck dataCheck;

    public IDListSameDataFinder(ArrayList<String> searchedDataList, IDataCheck dataCheck) {
        this.searchedDataList = searchedDataList;
        this.dataCheck = dataCheck;
    }

    public ArrayList<String> FindData()
    {
        ArrayList<String> sameDataArrayList = new ArrayList<>();

        for (String searchedData:searchedDataList)
        {
            /*if(DataCheck.CheckData(dataCheck, searchedData))
            {
                sameDataArrayList.add(searchedData);
            }*/
        }

        return sameDataArrayList;
    }
}
