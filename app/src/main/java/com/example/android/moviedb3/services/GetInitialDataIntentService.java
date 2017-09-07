package com.example.android.moviedb3.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.NowShowingDataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.GenreDataGetter;
import com.example.android.moviedb3.movieDataManager.MovieDataGetter;

import java.util.ArrayList;

/**
 * Created by nugroho on 07/09/17.
 */

public class GetInitialDataIntentService extends IntentService
{
    public GetInitialDataIntentService()
    {
        super("GetInitialDataIntentService");
    }

    public static final String GetInitialDataServiceCompleted = "Get Initial Data Service has been completed";

    private int numberNewNowShowingMovie;

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        GetNowShowingMovieList();
        GetComingSoonMovieList();
        GetPopularMovieList();
        GetTopRateMovieList();
        GetGenreList();

        /*if(numberNewNowShowingMovie <= 0)
        {
            NewNowShowingNotificationUtils.showNotification(this, numberNewNowShowingMovie);
        }*/

        NewNowShowingNotificationUtils.showNotification(this, numberNewNowShowingMovie);

        Intent broadcastIntent = new Intent(GetInitialDataServiceCompleted);
        sendBroadcast(broadcastIntent);
    }

    private void GetNowShowingMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(this, new NowShowingDataDB(this),
                getInitialOtherNowShowingMovieListDataDB(), MovieDataURL.GetNowShowingURL(), new newNowShowingMovieObtained()));
    }

    private void GetComingSoonMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(this, new ComingSoonDataDB(this),
                getInitialOtherComingSoonMovieListDataDB(), MovieDataURL.GetComingSoonURL()));
    }

    private void GetPopularMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(this, new PopularDataDB(this),
                getInitialOtherPopularMovieListDataDB(), MovieDataURL.GetPopularURL()));
    }

    private void GetTopRateMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(this, new TopRateDataDB(this),
                getInitialOtherTopRateMovieListDataDB(), MovieDataURL.GetTopRateURL()));
    }

    private void GetGenreList()
    {
        DBGetter.GetData(new GenreDataGetter(this, MovieDataURL.GetGenreListURL()));
    }

    private ArrayList<DataDB<String>> getInitialOtherNowShowingMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new ComingSoonDataDB(this));
        dataDBArrayList.add(new PopularDataDB(this));
        dataDBArrayList.add(new TopRateDataDB(this));
        dataDBArrayList.add(new FavoriteDataDB(this));
        dataDBArrayList.add(new WatchlistDataDB(this));
        dataDBArrayList.add(new PlanToWatchDataDB(this));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherComingSoonMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new NowShowingDataDB(this));
        dataDBArrayList.add(new PopularDataDB(this));
        dataDBArrayList.add(new TopRateDataDB(this));
        dataDBArrayList.add(new FavoriteDataDB(this));
        dataDBArrayList.add(new WatchlistDataDB(this));
        dataDBArrayList.add(new PlanToWatchDataDB(this));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherPopularMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new TopRateDataDB(this));
        dataDBArrayList.add(new NowShowingDataDB(this));
        dataDBArrayList.add(new ComingSoonDataDB(this));
        dataDBArrayList.add(new FavoriteDataDB(this));
        dataDBArrayList.add(new WatchlistDataDB(this));
        dataDBArrayList.add(new PlanToWatchDataDB(this));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherTopRateMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new PopularDataDB(this));
        dataDBArrayList.add(new NowShowingDataDB(this));
        dataDBArrayList.add(new ComingSoonDataDB(this));
        dataDBArrayList.add(new FavoriteDataDB(this));
        dataDBArrayList.add(new WatchlistDataDB(this));
        dataDBArrayList.add(new PlanToWatchDataDB(this));

        return dataDBArrayList;
    }

    private class newNowShowingMovieObtained implements OnDataObtainedListener<Integer>
    {
        @Override
        public void onDataObtained(Integer integer)
        {
            numberNewNowShowingMovie = integer;
        }
    }
}
