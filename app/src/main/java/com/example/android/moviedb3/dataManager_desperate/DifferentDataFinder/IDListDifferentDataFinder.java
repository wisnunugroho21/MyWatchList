package com.example.android.moviedb3.dataManager_desperate.DifferentDataFinder;

//import com.example.android.moviedb3.dataManager_desperate.DataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.DataCheck.IDataCheck;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public class IDListDifferentDataFinder implements IDifferentDataFinder<String>
{
    ArrayList<String> comparedDataList;
    IDataCheck<String> dataCheck;

    public IDListDifferentDataFinder(ArrayList<String> comparedDataList, IDataCheck<String> dataCheck) {
        this.comparedDataList = comparedDataList;
        this.dataCheck = dataCheck;
    }

    @Override
    public ArrayList<String> FindDataFromComparedList() {
        ArrayList<String> sameDataArrayList = new ArrayList<>();

        for (String searchedData:comparedDataList)
        {
            /*if(DataCheck.CheckData(dataCheck, searchedData) == null)
            {
                sameDataArrayList.add(searchedData);
            }*/
        }

        return sameDataArrayList;
    }

    @Override
    public ArrayList<String> FindDataFromComparisonList() {
        ArrayList<String> sameDataArrayList = new ArrayList<>();

        for (String searchedData:comparedDataList)
        {
            /*String receivedData = DataCheck.CheckData(dataCheck, searchedData);

            if(receivedData == null)
            {
                sameDataArrayList.add(receivedData);
            }*/
        }

        return sameDataArrayList;
    }
}
