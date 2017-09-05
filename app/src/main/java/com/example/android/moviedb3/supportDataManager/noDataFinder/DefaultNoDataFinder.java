package com.example.android.moviedb3.supportDataManager.noDataFinder;

import com.example.android.moviedb3.supportDataManager.dataComparision.IDataComparision;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public class DefaultNoDataFinder<Data> implements INoDataFinder<Data>
{
    IDataComparision<Data> dataComparision;
    ArrayList<Data> dataArrayList;
    ArrayList<String> idList;

    public DefaultNoDataFinder(IDataComparision<Data> dataComparision, ArrayList<Data> dataArrayList, ArrayList<String> idList) {
        this.dataComparision = dataComparision;
        this.dataArrayList = dataArrayList;
        this.idList = idList;
    }

    public ArrayList<Data> FindNotSameData()
    {
        ArrayList<Data> notSameDataList = new ArrayList<>();

        if(idList != null && dataArrayList != null)
        {
            for (Data data:dataArrayList)
            {
                boolean isSame = false;

                for (String idData:idList)
                {
                    if(dataComparision.isSame(idData, data))
                    {
                        isSame = true;
                        break;
                    }
                }

                if(!isSame)
                {
                    notSameDataList.add(data);
                }
            }
        }

        return notSameDataList;
    }

    public ArrayList<String> FindNotSameID()
    {
        ArrayList<String> notSameIDList = new ArrayList<>();

        if(idList != null && dataArrayList != null)
        {
            for (String idData:idList)
            {
                boolean isSame = false;

                for (Data data:dataArrayList)
                {
                    if(dataComparision.isSame(idData, data))
                    {
                        isSame = true;
                        break;
                    }
                }

                if(!isSame)
                {
                    notSameIDList.add(idData);
                }
            }
        }

        return notSameIDList;
    }
}
