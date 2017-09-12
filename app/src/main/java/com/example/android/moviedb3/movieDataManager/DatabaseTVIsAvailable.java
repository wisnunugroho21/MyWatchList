package com.example.android.moviedb3.movieDataManager;

import android.content.Context;

import com.example.android.moviedb3.localDatabase.AirTodayDataDB;
import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.FavoriteTVDataDB;
import com.example.android.moviedb3.localDatabase.GenreTVPopularDB;
import com.example.android.moviedb3.localDatabase.GenreTVTopRateDataDB;
import com.example.android.moviedb3.localDatabase.NowShowingDataDB;
import com.example.android.moviedb3.localDatabase.OnTheAirDataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchDataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchTVDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.PopularTVDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.localDatabase.TopRatedTVDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistTvDataDB;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.GenreTvData;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DefaultDataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IntermedieteDataCompare;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class DatabaseTVIsAvailable
{
    public static boolean isAvailableFromIDList(String idTV, ArrayList<DataDB<String>> otherIDTVListDataDB, Context context)
    {
        for(DataDB<String> idDataDB : otherIDTVListDataDB)
        {
            if(DataAvailableCheck.isDataAvailable(
                    new DefaultDataAvailableCheck<String>(new IDCompare(), idDataDB.getAllData(), idTV)))
            {
                return true;
            }
        }

        for(DataDB<GenreTvData> intermedieteDataDB : getInitialGenreTVDataDB(context))
        {
            if(DataAvailableCheck.isDataAvailable
                    (new DefaultDataAvailableCheck<GenreTvData>
                            (new IntermedieteDataCompare<GenreTvData>(IntermedieteDataCompare.CHECK_FIRST_ID), intermedieteDataDB.getAllData(), idTV)))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean isAvailableFromGenreList(String idTV, String idGenre, Context context)
    {
        for(DataDB<String> idDataDB : getInitialIDTVListDataDB(context))
        {
            if(DataAvailableCheck.isDataAvailable(
                    new DefaultDataAvailableCheck<String>(new IDCompare(), idDataDB.getAllData(), idTV)))
            {
                return true;
            }
        }

        for(DataDB<GenreTvData> intermedieteDataDB : getInitialGenreTVDataDB(context))
        {
            for(GenreTvData genreTvData:intermedieteDataDB.getAllData())
            {
                if(idTV.equals(genreTvData.getIdTV()) && !idGenre.equals(genreTvData.getIdGenre()))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private static ArrayList<DataDB<String>> getInitialIDTVListDataDB(Context context)
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new AirTodayDataDB(context));
        dataDBArrayList.add(new OnTheAirDataDB(context));
        dataDBArrayList.add(new PopularTVDataDB(context));
        dataDBArrayList.add(new TopRatedTVDataDB(context));
        dataDBArrayList.add(new FavoriteTVDataDB(context));
        dataDBArrayList.add(new WatchlistTvDataDB(context));
        dataDBArrayList.add(new PlanToWatchTVDataDB(context));

        return dataDBArrayList;
    }

    private static ArrayList<DataDB<GenreTvData>> getInitialGenreTVDataDB(Context context)
    {
        ArrayList<DataDB<GenreTvData>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new GenreTVPopularDB(context));
        dataDBArrayList.add(new GenreTVTopRateDataDB(context));

        return dataDBArrayList;
    }
}
