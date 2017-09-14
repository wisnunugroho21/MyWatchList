package com.example.android.moviedb3.movieDataManager;

import android.content.Context;

import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.GenreMoviePopularDB;
import com.example.android.moviedb3.localDatabase.GenreMovieTopRateDB;
import com.example.android.moviedb3.localDatabase.NowShowingDataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DefaultDataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IntermedieteDataCompare;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class DatabasePeopleIsAvailable
{
    public static boolean isAvailableFromIDList(String idMovie, ArrayList<DataDB<String>> otherIDMovieListDataDB)
    {
        for(DataDB<String> idDataDB : otherIDMovieListDataDB)
        {
            if(DataAvailableCheck.isDataAvailable(
                    new DefaultDataAvailableCheck<String>(new IDCompare(), idDataDB.getAllData(), idMovie)))
            {
                return true;
            }
        }

        return false;
    }
}
