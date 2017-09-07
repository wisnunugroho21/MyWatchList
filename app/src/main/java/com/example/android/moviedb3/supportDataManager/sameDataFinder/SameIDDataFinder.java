package com.example.android.moviedb3.supportDataManager.sameDataFinder;

import com.example.android.moviedb3.supportDataManager.dataComparision.IDataComparision;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public class SameIDDataFinder<Data> implements ISameDataFinder<Data>
{
    IDataComparision<Data> dataComparision;
    ArrayList<Data> dataArrayList;
    ArrayList<String> idList;

    String idData;

    public SameIDDataFinder(IDataComparision<Data> dataComparision, ArrayList<Data> dataArrayList, ArrayList<String> idList) {
        this.dataComparision = dataComparision;
        this.dataArrayList = dataArrayList;
        this.idList = idList;
    }

    public SameIDDataFinder(IDataComparision<Data> dataComparision, ArrayList<Data> dataArrayList, String idData) {
        this.dataComparision = dataComparision;
        this.dataArrayList = dataArrayList;
        this.idData = idData;
    }

    public ArrayList<Data> getDataSameList()
    {
        ArrayList<Data> sameDataList = new ArrayList<>();

        if(idList != null)
        {
            if(dataArrayList != null)
            {
                for (Data data:dataArrayList)
                {
                    for (String id:idList)
                    {
                        if(dataComparision.isSame(id, data))
                        {
                            sameDataList.add(data);
                        }
                    }
                }
            }
        }

        else
        {
            if(dataArrayList != null && idData != null)
            {
                for (Data data:dataArrayList)
                {
                    if(dataComparision.isSame(idData, data))
                    {
                        sameDataList.add(data);
                    }
                }
            }
        }

        return sameDataList;
    }
}
