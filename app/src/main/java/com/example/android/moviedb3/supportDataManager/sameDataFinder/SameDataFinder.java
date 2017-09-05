package com.example.android.moviedb3.supportDataManager.sameDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class SameDataFinder
{
    public static <Data>ArrayList<Data> getDataSameList(ISameDataFinder<Data> sameDataFinder)
    {
        return sameDataFinder.getDataSameList();
    }
}
