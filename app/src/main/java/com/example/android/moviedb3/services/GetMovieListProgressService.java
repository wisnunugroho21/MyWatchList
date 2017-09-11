package com.example.android.moviedb3.services;

import android.app.IntentService;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

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
import com.example.android.moviedb3.sharedPreferences.DefaultIntegerStatePreference;
import com.example.android.moviedb3.sharedPreferences.PreferencesUtils;

import java.util.ArrayList;

/**
 * Created by nugroho on 10/09/17.
 */

public class GetMovieListProgressService extends IntentService
{
    int notification_id = 719;
    int numberNewNowShowingMovie;
    Context context;

    public GetMovieListProgressService() {
        super("GetMovieListProgressService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        context = this;
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 0));

        GetNowShowingMovieList();
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 1));

        GetComingSoonMovieList();
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 2));

        GetPopularMovieList();
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 3));

        GetTopRateMovieList();
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 4));

        GetGenreList();
        SendMessageToActivity();

        GettingAllDataNotificationUtils.showNotificationCompleted(context);
        stopForeground(true);
    }

    private void SendMessageToActivity()
    {
        Intent intent = new Intent();
        intent.setAction(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_MESSENGER);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void GetNowShowingMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new NowShowingDataDB(context),
                getInitialOtherNowShowingMovieListDataDB(), MovieDataURL.GetNowShowingURL(context), new newNowShowingMovieObtained()));
    }

    private void GetComingSoonMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new ComingSoonDataDB(context),
                getInitialOtherComingSoonMovieListDataDB(), MovieDataURL.GetComingSoonURL(context)));
    }

    private void GetPopularMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new PopularDataDB(context),
                getInitialOtherPopularMovieListDataDB(), MovieDataURL.GetPopularURL(context)));
    }

    private void GetTopRateMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new TopRateDataDB(context),
                getInitialOtherTopRateMovieListDataDB(), MovieDataURL.GetTopRateURL(context)));
    }

    private void GetGenreList()
    {
        DBGetter.GetData(new GenreDataGetter(context, MovieDataURL.GetGenreListURL(context)));
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

    /*@Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        GetInitialDataAsyncTask getInitialDataAsyncTask = new GetInitialDataAsyncTask(this);
        getInitialDataAsyncTask.execute();

        return START_STICKY;
    }*/

/*private class GetInitialDataAsyncTask extends AsyncTask<Void, Void, Void>
{
    int numberNewNowShowingMovie;
    Context context;

    public GetInitialDataAsyncTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 0));

        GetNowShowingMovieList();
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 1));

        GetComingSoonMovieList();
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 2));

        GetPopularMovieList();
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 3));

        GetTopRateMovieList();
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationWithProgress(context, 5, 4));

        GetGenreList();
        startForeground(notification_id, GettingAllDataNotificationUtils.createNotificationCompleted(context));

        SendMessageToActivity();


            *//*GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 0);

            GetNowShowingMovieList();
            GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 1);

            GetComingSoonMovieList();
            GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 2);

            GetPopularMovieList();
            GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 3);

            GetTopRateMovieList();
            GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 4);

            GetGenreList();
            GettingAllDataNotificationUtils.showNotificationCompleted(context);

            SendMessageToActivity();*//*

            *//*int numberDataObtained = PreferencesUtils.GetData(new DefaultIntegerStatePreference(context), MovieDBKeyEntry.SharedPreferencesKey.SAVED_NUMBER, 0);

            if(numberDataObtained == 0)
            {
                GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 0);
                GetNowShowingMovieList();

                numberDataObtained++;
                PreferencesUtils.SetData(new DefaultIntegerStatePreference(context), numberDataObtained, MovieDBKeyEntry.SharedPreferencesKey.SAVED_NUMBER);
            }

            if(numberDataObtained == 1)
            {
                GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 1);
                GetComingSoonMovieList();

                numberDataObtained++;
                PreferencesUtils.SetData(new DefaultIntegerStatePreference(context), numberDataObtained, MovieDBKeyEntry.SharedPreferencesKey.SAVED_NUMBER);
            }

            if(numberDataObtained == 2)
            {
                GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 2);
                GetPopularMovieList();

                numberDataObtained++;
                PreferencesUtils.SetData(new DefaultIntegerStatePreference(context), numberDataObtained, MovieDBKeyEntry.SharedPreferencesKey.SAVED_NUMBER);
            }

            if(numberDataObtained == 3)
            {
                GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 3);
                GetTopRateMovieList();

                numberDataObtained++;
                PreferencesUtils.SetData(new DefaultIntegerStatePreference(context), numberDataObtained, MovieDBKeyEntry.SharedPreferencesKey.SAVED_NUMBER);
            }

            if(numberDataObtained == 4)
            {
                GettingAllDataNotificationUtils.showNotificationOnProgress(context, 5, 4);
                GetGenreList();

                numberDataObtained = 0;
                PreferencesUtils.SetData(new DefaultIntegerStatePreference(context), numberDataObtained, MovieDBKeyEntry.SharedPreferencesKey.SAVED_NUMBER);
            }

            GettingAllDataNotificationUtils.showNotificationCompleted(context);
            SendMessageToActivity();*//*
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        stopSelf();
    }

    private void SendMessageToActivity()
    {
        Intent intent = new Intent();
        intent.setAction(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_MESSENGER);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void GetNowShowingMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new NowShowingDataDB(context),
                getInitialOtherNowShowingMovieListDataDB(), MovieDataURL.GetNowShowingURL(context), new GetInitialDataAsyncTask.newNowShowingMovieObtained()));
    }

    private void GetComingSoonMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new ComingSoonDataDB(context),
                getInitialOtherComingSoonMovieListDataDB(), MovieDataURL.GetComingSoonURL(context)));
    }

    private void GetPopularMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new PopularDataDB(context),
                getInitialOtherPopularMovieListDataDB(), MovieDataURL.GetPopularURL(context)));
    }

    private void GetTopRateMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(context, new TopRateDataDB(context),
                getInitialOtherTopRateMovieListDataDB(), MovieDataURL.GetTopRateURL(context)));
    }

    private void GetGenreList()
    {
        DBGetter.GetData(new GenreDataGetter(context, MovieDataURL.GetGenreListURL(context)));
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
}*/

/*@Override
    public boolean onStartJob(final JobParameters job)
    {
        getInitialDataAsyncTask = new GetInitialDataAsyncTask(getApplicationContext(), job);
        getInitialDataAsyncTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job)
    {
        if(getInitialDataAsyncTask != null)
        {
            getInitialDataAsyncTask.cancel(true);
        }

        return true;
    }*/

/**/
