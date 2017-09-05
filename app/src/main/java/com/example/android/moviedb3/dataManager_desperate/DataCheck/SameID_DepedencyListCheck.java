/*
package com.example.android.moviedb3.dataManager_desperate.DataCheck;

import com.example.android.moviedb3.movieDB.DependencyData;

import java.util.ArrayList;

*/
/**
 * Created by nugroho on 03/09/17.
 *//*


public class SameID_DepedencyListCheck<Data extends DependencyData> implements IDataCheck<Data> {
    ArrayList<Data> comparisondataArrayList;

    public SameID_DepedencyListCheck(ArrayList<Data> comparisondataArrayList) {
        this.comparisondataArrayList = comparisondataArrayList;
    }

    @Override
    public Data CheckData(String idData) {

        if(comparisondataArrayList != null && !comparisondataArrayList.isEmpty())
        {
            for (Data data : comparisondataArrayList)
            {
                if (idData.equals(data.getIDDependent()))
                {
                    return data; //ternyata movieID masih ada di list favorite, top rate, dll
                }
            }
        }

        return null;
    }
}


*/
