package com.example.android.moviedb3.dataManager_desperate.SameDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public class SameDataFInder
{
    public static <Data> ArrayList<Data> FindData(ISameDataFinder<Data> dataFind)
    {
        return dataFind.FindData();
    }
}
