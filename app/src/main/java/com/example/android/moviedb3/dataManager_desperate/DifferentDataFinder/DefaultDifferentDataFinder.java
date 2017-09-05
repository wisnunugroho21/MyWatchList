package com.example.android.moviedb3.dataManager_desperate.DifferentDataFinder;

//import com.example.android.moviedb3.dataManager_desperate.DataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.DataCheck.IDataCheck;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class DefaultDifferentDataFinder<Data extends BaseData> implements IDifferentDataFinder<Data>
{
    ArrayList<Data> comparedDataList;
    IDataCheck<Data> dataCheck;

    public DefaultDifferentDataFinder(ArrayList<Data> comparedDataList, IDataCheck<Data> dataCheck) {
        this.comparedDataList = comparedDataList;
        this.dataCheck = dataCheck;
    }

    @Override
    public ArrayList<Data> FindDataFromComparedList() {
        ArrayList<Data> sameDataArrayList = new ArrayList<>();

        for (Data searchedData:comparedDataList)
        {
            /*if(DataCheck.CheckData(dataCheck, searchedData.getId()) == null)
            {
                sameDataArrayList.add(searchedData);
            }*/
        }

        return sameDataArrayList;
    }

    @Override
    public ArrayList<Data> FindDataFromComparisonList() {
        ArrayList<Data> sameDataArrayList = new ArrayList<>();

        for (Data searchedData:comparedDataList)
        {
            /*Data receivedData = DataCheck.CheckData(dataCheck, searchedData.getId());

            if(receivedData == null)
            {
                sameDataArrayList.add(receivedData);
            }*/
        }

        return sameDataArrayList;
    }


}
