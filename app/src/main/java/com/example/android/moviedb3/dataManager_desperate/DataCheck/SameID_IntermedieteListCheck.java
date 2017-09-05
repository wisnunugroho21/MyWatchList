package com.example.android.moviedb3.dataManager_desperate.DataCheck;

import com.example.android.moviedb3.movieDB.IntermedieteData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

/*
public class SameID_IntermedieteListCheck<Data extends IntermedieteData> implements IDataCheck<Data>
{
    public static final int CHECK_FIRST_ID = 587;
    public static final int CHECK_SECOND_ID = 103;

    int checkMode;
    ArrayList<Data> comparisondataArrayList;

    public SameID_IntermedieteListCheck(int checkMode, ArrayList<Data> comparisondataArrayList) {
        this.checkMode = checkMode;
        this.comparisondataArrayList = comparisondataArrayList;
    }

    @Override
    public Data CheckData(String idData)
    {
        if(comparisondataArrayList != null && !comparisondataArrayList.isEmpty())
        {
            switch (checkMode)
            {
                case CHECK_FIRST_ID :
                    for (Data data : comparisondataArrayList)
                    {
                        if (idData.equals(data.getFirstID()))
                        {
                            return data; //ternyata movieID masih ada di list favorite, top rate, dll
                        }
                    }
                    break;

                case CHECK_SECOND_ID :
                    for (Data data : comparisondataArrayList)
                    {
                        if (idData.equals(data.getSecondID()))
                        {
                            return data;
                        }
                    }
                    break;
            }
        }

        return null;
    }
}
*/
