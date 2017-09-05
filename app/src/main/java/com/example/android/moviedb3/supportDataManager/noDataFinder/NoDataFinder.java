package com.example.android.moviedb3.supportDataManager.noDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class NoDataFinder
{
    public static <Data>ArrayList<Data> FindNotSameData(INoDataFinder<Data> noDataFinder)
    {
        return noDataFinder.FindNotSameData();
    }

    public static <Data>ArrayList<String> FindNotSameID(INoDataFinder<Data> noDataFinder)
    {
        return noDataFinder.FindNotSameID();
    }
}
