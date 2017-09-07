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
 * Created by nugroho on 05/09/17.
 */

public class DatabaseMovieIsAvailable
{
    public static boolean isAvailableFromIDList(String idMovie, ArrayList<DataDB<String>> otherIDMovieListDataDB, Context context)
    {
        for(DataDB<String> idDataDB : otherIDMovieListDataDB)
        {
            if(DataAvailableCheck.isDataAvailable(
                    new DefaultDataAvailableCheck<String>(new IDCompare(), idDataDB.getAllData(), idMovie)))
            {
                return true;
            }
        }

        for(DataDB<GenreMovieData> intermedieteDataDB : getInitialGenreMovieDataDB(context))
        {
            if(DataAvailableCheck.isDataAvailable
                    (new DefaultDataAvailableCheck<GenreMovieData>
                            (new IntermedieteDataCompare<GenreMovieData>(IntermedieteDataCompare.CHECK_FIRST_ID), intermedieteDataDB.getAllData(), idMovie)))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean isAvailableFromGenreList(String idMovie, String idGenre, Context context)
    {
        for(DataDB<String> idDataDB : getInitialIDMovieListDataDB(context))
        {
            if(DataAvailableCheck.isDataAvailable(
                    new DefaultDataAvailableCheck<String>(new IDCompare(), idDataDB.getAllData(), idMovie)))
            {
                return true;
            }
        }

        for(DataDB<GenreMovieData> intermedieteDataDB : getInitialGenreMovieDataDB(context))
        {
            for(GenreMovieData genreMovieData:intermedieteDataDB.getAllData())
            {
                if(idMovie.equals(genreMovieData.getIdMovie()) && !idGenre.equals(genreMovieData.getIdGenre()))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private static ArrayList<DataDB<String>> getInitialIDMovieListDataDB(Context context)
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new NowShowingDataDB(context));
        dataDBArrayList.add(new ComingSoonDataDB(context));
        dataDBArrayList.add(new PopularDataDB(context));
        dataDBArrayList.add(new TopRateDataDB(context));
        dataDBArrayList.add(new FavoriteDataDB(context));
        dataDBArrayList.add(new WatchlistDataDB(context));
        dataDBArrayList.add(new PlanToWatchDataDB(context));

        return dataDBArrayList;
    }

    private static ArrayList<DataDB<GenreMovieData>> getInitialGenreMovieDataDB(Context context)
    {
        ArrayList<DataDB<GenreMovieData>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new GenreMovieTopRateDB(context));
        dataDBArrayList.add(new GenreMoviePopularDB(context));

        return dataDBArrayList;
    }
}
