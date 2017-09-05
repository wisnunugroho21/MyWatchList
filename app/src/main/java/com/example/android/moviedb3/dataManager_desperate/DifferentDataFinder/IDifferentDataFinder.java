package com.example.android.moviedb3.dataManager_desperate.DifferentDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/09/17.
 */

public interface IDifferentDataFinder<Data>
{
    ArrayList<Data> FindDataFromComparedList();
    ArrayList<Data> FindDataFromComparisonList();
}
