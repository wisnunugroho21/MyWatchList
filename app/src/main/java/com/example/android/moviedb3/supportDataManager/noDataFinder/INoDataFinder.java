package com.example.android.moviedb3.supportDataManager.noDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public interface INoDataFinder<Data>
{
    ArrayList<Data> FindNotSameData();
    ArrayList<String> FindNotSameID();
}
