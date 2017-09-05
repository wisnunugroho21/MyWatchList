package com.example.android.moviedb3.dataManager_desperate.SameDataFinder;

//import com.example.android.moviedb3.dataManager_desperate.DataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.DataCheck.IDataCheck;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class DefaultSameDataFinder<Data extends BaseData> implements ISameDataFinder<Data>
{
    private static final int BASED_ID_LIST = 314;
    private static final int BASED_DATA_LIST = 868;

    ArrayList<Data> searchedDataList;
    IDataCheck dataCheck;

    public DefaultSameDataFinder(ArrayList<Data> searchedDataList, IDataCheck dataCheck) {
        this.searchedDataList = searchedDataList;
        this.dataCheck = dataCheck;
    }

    public ArrayList<Data> FindData()
    {
        ArrayList<Data> sameDataArrayList = new ArrayList<>();

        for (Data searchedData:searchedDataList)
        {
            /*if(DataCheck.CheckData(dataCheck, searchedData.getId()))
            {
                sameDataArrayList.add(searchedData);
            }*/
        }

        return sameDataArrayList;
    }

    public ArrayList<Data> x()
    {
        ArrayList<Data> sameDataArrayList = new ArrayList<>();

        for (Data searchedData:searchedDataList)
        {
            /*if(DataCheck.CheckData(dataCheck, searchedData))
            {
                sameDataArrayList.add(searchedData);
            }*/
        }

        return sameDataArrayList;
    }


}
