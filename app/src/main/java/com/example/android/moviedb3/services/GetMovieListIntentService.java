package com.example.android.moviedb3.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
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
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.GenreDataGetter;
import com.example.android.moviedb3.movieDataManager.MovieDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;

import java.util.ArrayList;

/**
 * Created by nugroho on 08/09/17.
 */

public class GetMovieListIntentService extends IntentService
{
    Context context;
    int numberNewNowShowingMovie;
    BundleDataGetter bundleDataGetter;

    public GetMovieListIntentService()
    {
        super("GetMovieListIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        context = getApplicationContext();
        bundleDataGetter = new BundleDataGetter(intent.getExtras());

        GetNowShowingMovieList();
        GetComingSoonMovieList();
        GetPopularMovieList();
        GetTopRateMovieList();
        GetGenreList();

        GettingAllDataNotificationUtils.showNotificationCompleted(context);

        ResultReceiver resultReceiver = bundleDataGetter.getData(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_RESULT_RECEIVER);
        resultReceiver.send(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_RESULT_SUCCESS, null);
    }

    private void GetNowShowingMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new NowShowingDataDB(context),
                getInitialOtherNowShowingMovieListDataDB(), MovieDataURL.GetNowShowingURL(this), new newNowShowingMovieObtained()));
    }

    private void GetComingSoonMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new ComingSoonDataDB(context),
                getInitialOtherComingSoonMovieListDataDB(), MovieDataURL.GetComingSoonURL(this)));
    }

    private void GetPopularMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new PopularDataDB(context),
                getInitialOtherPopularMovieListDataDB(), MovieDataURL.GetPopularURL(this)));
    }

    private void GetTopRateMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new TopRateDataDB(context),
                getInitialOtherTopRateMovieListDataDB(), MovieDataURL.GetTopRateURL(this)));
    }

    private void GetGenreList()
    {
        DBGetter.GetData(new GenreDataGetter(context, MovieDataURL.GetGenreListURL(this)));
    }

    private ArrayList<DataDB<String>> getInitialOtherNowShowingMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new ComingSoonDataDB(context));
        dataDBArrayList.add(new PopularDataDB(context));
        dataDBArrayList.add(new TopRateDataDB(context));
        dataDBArrayList.add(new FavoriteDataDB(context));
        dataDBArrayList.add(new WatchlistDataDB(context));
        dataDBArrayList.add(new PlanToWatchDataDB(context));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherComingSoonMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new NowShowingDataDB(context));
        dataDBArrayList.add(new PopularDataDB(context));
        dataDBArrayList.add(new TopRateDataDB(context));
        dataDBArrayList.add(new FavoriteDataDB(context));
        dataDBArrayList.add(new WatchlistDataDB(context));
        dataDBArrayList.add(new PlanToWatchDataDB(context));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherPopularMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new TopRateDataDB(context));
        dataDBArrayList.add(new NowShowingDataDB(context));
        dataDBArrayList.add(new ComingSoonDataDB(context));
        dataDBArrayList.add(new FavoriteDataDB(context));
        dataDBArrayList.add(new WatchlistDataDB(context));
        dataDBArrayList.add(new PlanToWatchDataDB(context));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherTopRateMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new PopularDataDB(context));
        dataDBArrayList.add(new NowShowingDataDB(context));
        dataDBArrayList.add(new ComingSoonDataDB(context));
        dataDBArrayList.add(new FavoriteDataDB(context));
        dataDBArrayList.add(new WatchlistDataDB(context));
        dataDBArrayList.add(new PlanToWatchDataDB(context));

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
