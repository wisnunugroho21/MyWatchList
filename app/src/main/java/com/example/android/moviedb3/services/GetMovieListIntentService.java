package com.example.android.moviedb3.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.AirTodayDataDB;
import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.FavoriteTVDataDB;
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
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.GenreDataGetter;
import com.example.android.moviedb3.movieDataManager.MovieDataGetter;
import com.example.android.moviedb3.movieDataManager.PeopleDataGetter;
import com.example.android.moviedb3.movieDataManager.TVDataGetter;
import com.example.android.moviedb3.movieDataManager.TVGenreDataGetter;
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
        ResultReceiver resultReceiver = null;

        if(intent.hasExtra(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_RESULT_RECEIVER))
        {
            resultReceiver = bundleDataGetter.getData(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_RESULT_RECEIVER);
        }

        try
        {
            GetNowShowingMovieList();
            GetComingSoonMovieList();
            GetPopularMovieList();
            GetTopRateMovieList();
            GetGenreList();

            GetAiringTodayTVList();
            GetOnTheAirTVList();
            GetPopularTVList();
            GetTopRateTVList();
            GetTVGenreList();

            GetPopularPeopleList();

            if(resultReceiver != null)
            {
                resultReceiver.send(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_RESULT_SUCCESS, null);
                return;
            }

            else
            {
                GettingAllDataNotificationUtils.showNotificationCompleted(context);
                return;
            }
        }
        catch (Exception e)
        {
            if(resultReceiver != null)
            {
                resultReceiver.send(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_RESULT_FAIL, null);
                return;
            }
        }
    }

    private void GetNowShowingMovieList() throws Exception
    {
        DBGetter.GetData(new MovieDataGetter(context, new NowShowingDataDB(context),
                getInitialOtherNowShowingMovieListDataDB(), MovieDataURL.GetNowShowingURL(this), new newNowShowingMovieObtained()));
    }

    private void GetComingSoonMovieList() throws Exception
    {
        DBGetter.GetData(new MovieDataGetter(context, new ComingSoonDataDB(context),
                getInitialOtherComingSoonMovieListDataDB(), MovieDataURL.GetComingSoonURL(this)));
    }

    private void GetPopularMovieList() throws Exception
    {
        DBGetter.GetData(new MovieDataGetter(context, new PopularDataDB(context),
                getInitialOtherPopularMovieListDataDB(), MovieDataURL.GetPopularURL(this)));
    }

    private void GetTopRateMovieList() throws Exception
    {
        DBGetter.GetData(new MovieDataGetter(context, new TopRateDataDB(context),
                getInitialOtherTopRateMovieListDataDB(), MovieDataURL.GetTopRateURL(this)));
    }

    private void GetGenreList() throws Exception
    {
        DBGetter.GetData(new GenreDataGetter(context, MovieDataURL.GetGenreListURL(this)));
    }

    private void GetAiringTodayTVList() throws Exception
    {
        DBGetter.GetData(new TVDataGetter(context, new AirTodayDataDB(context),
                getInitialOtherAiringTodayTVListDataDB(), MovieDataURL.GetAiringTodayTVURL(this)));
    }

    private void GetOnTheAirTVList() throws Exception
    {
        DBGetter.GetData(new TVDataGetter(context, new OnTheAirDataDB(context),
                getInitialOtherOnTheAirTVListDataDB(), MovieDataURL.GetOnTheAirTVURL(this)));
    }

    private void GetPopularTVList() throws Exception
    {
        DBGetter.GetData(new TVDataGetter(context, new PopularTVDataDB(context),
                getInitialOtherPopularTVListDataDB(), MovieDataURL.GetPopularTVURL(this)));
    }

    private void GetTopRateTVList() throws Exception
    {
        DBGetter.GetData(new TVDataGetter(context, new TopRatedTVDataDB(context),
                getInitialOtherTopRateTVListDataDB(), MovieDataURL.GetTopRateTVURL(this)));
    }

    private void GetTVGenreList() throws Exception
    {
        DBGetter.GetData(new TVGenreDataGetter(context, MovieDataURL.GetTVGenreListURL(this)));
    }

    private void GetPopularPeopleList() throws Exception
    {
        DBGetter.GetData(new PeopleDataGetter(context));
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

    private ArrayList<DataDB<String>> getInitialOtherAiringTodayTVListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new OnTheAirDataDB(context));
        dataDBArrayList.add(new PopularTVDataDB(context));
        dataDBArrayList.add(new TopRatedTVDataDB(context));
        dataDBArrayList.add(new FavoriteTVDataDB(context));
        dataDBArrayList.add(new WatchlistTvDataDB(context));
        dataDBArrayList.add(new PlanToWatchTVDataDB(context));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherOnTheAirTVListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new AirTodayDataDB(context));
        dataDBArrayList.add(new PopularTVDataDB(context));
        dataDBArrayList.add(new TopRatedTVDataDB(context));
        dataDBArrayList.add(new FavoriteTVDataDB(context));
        dataDBArrayList.add(new WatchlistTvDataDB(context));
        dataDBArrayList.add(new PlanToWatchTVDataDB(context));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherPopularTVListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new TopRatedTVDataDB(context));
        dataDBArrayList.add(new AirTodayDataDB(context));
        dataDBArrayList.add(new OnTheAirDataDB(context));
        dataDBArrayList.add(new FavoriteTVDataDB(context));
        dataDBArrayList.add(new WatchlistTvDataDB(context));
        dataDBArrayList.add(new PlanToWatchTVDataDB(context));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherTopRateTVListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new PopularTVDataDB(context));
        dataDBArrayList.add(new AirTodayDataDB(context));
        dataDBArrayList.add(new OnTheAirDataDB(context));
        dataDBArrayList.add(new FavoriteTVDataDB(context));
        dataDBArrayList.add(new WatchlistTvDataDB(context));
        dataDBArrayList.add(new PlanToWatchTVDataDB(context));

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
