package com.example.android.moviedb3.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.android.moviedb3.R;
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
import com.example.android.moviedb3.sharedPreferences.DefaultStringStatePreference;
import com.example.android.moviedb3.sharedPreferences.PreferencesUtils;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


import java.util.ArrayList;

/**
 * Created by nugroho on 07/09/17.
 */

public class GetMovieListRepeatingService extends JobService
{
    GetInitialDataAsyncTask getInitialDataAsyncTask;
    int notification_id = 719;

    @Override
    public boolean onStartJob(final JobParameters job)
    {
        getInitialDataAsyncTask = new GetInitialDataAsyncTask(this, job);
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
    }

    private class GetInitialDataAsyncTask extends AsyncTask<Void, Void, Void>
    {
        int numberNewNowShowingMovie;
        Context context;
        JobParameters jobParameters;

        public GetInitialDataAsyncTask(Context context, JobParameters jobParameters)
        {
            this.context = context;
            this.jobParameters = jobParameters;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            startForeground(notification_id, PeriodUpdateNotificationUtils.createStartPeriodUpdateNotification(context));

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

                String typeNotificationString = PreferencesUtils.GetData(new DefaultStringStatePreference(getApplicationContext()), getString(R.string.type_notification_key), getString(R.string.normal_led_notification_value));
                int typeNotification = 0;

                if(typeNotificationString.equals(getString(R.string.normal_led_notification_value)))
                {
                    typeNotification = PeriodUpdateNotificationUtils.LIGHT_NOTIFICATION;
                }

                if(typeNotificationString.equals(getString(R.string.vibrate_notification_value)))
                {
                    typeNotification = PeriodUpdateNotificationUtils.VIBRATE_NOTIFICATION;
                }

                if(typeNotificationString.equals(getString(R.string.sound_notification_value)))
                {
                    typeNotification = PeriodUpdateNotificationUtils.SOUND_NOTIFICATION;
                }

                if(typeNotificationString.equals(getString(R.string.all_set_notification_value)))
                {
                    typeNotification = PeriodUpdateNotificationUtils.ALL_SET_NOTIFICATION;
                }

                SendMessageToActivity();
                PeriodUpdateNotificationUtils.createFinishPeriodUpdateNotification(getApplicationContext(), numberNewNowShowingMovie, typeNotification);
            }
            catch (Exception e)
            {

            }

            stopForeground(true);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            jobFinished(jobParameters, false);
        }

        private void SendMessageToActivity()
        {
            Intent intent = new Intent();
            intent.setAction(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_MESSENGER);
            sendBroadcast(intent);
        }

        private void GetNowShowingMovieList() throws Exception
        {
            DBGetter.GetData(new MovieDataGetter(context, new NowShowingDataDB(context),
                    getInitialOtherNowShowingMovieListDataDB(), MovieDataURL.GetNowShowingURL(context), new newNowShowingMovieObtained()));
        }

        private void GetComingSoonMovieList() throws Exception
        {
            DBGetter.GetData(new MovieDataGetter(context, new ComingSoonDataDB(context),
                    getInitialOtherComingSoonMovieListDataDB(), MovieDataURL.GetComingSoonURL(context)));
        }

        private void GetPopularMovieList() throws Exception
        {
            DBGetter.GetData(new MovieDataGetter(context, new PopularDataDB(context),
                    getInitialOtherPopularMovieListDataDB(), MovieDataURL.GetPopularURL(context)));
        }

        private void GetTopRateMovieList() throws Exception
        {
            DBGetter.GetData(new MovieDataGetter(context, new TopRateDataDB(context),
                    getInitialOtherTopRateMovieListDataDB(), MovieDataURL.GetTopRateURL(context)));
        }

        private void GetGenreList() throws Exception
        {
            DBGetter.GetData(new GenreDataGetter(context, MovieDataURL.GetGenreListURL(context)));
        }

        private void GetAiringTodayTVList() throws Exception
        {
            DBGetter.GetData(new TVDataGetter(context, new AirTodayDataDB(context),
                    getInitialOtherAiringTodayTVListDataDB(), MovieDataURL.GetAiringTodayTVURL(context)));
        }

        private void GetOnTheAirTVList() throws Exception
        {
            DBGetter.GetData(new TVDataGetter(context, new OnTheAirDataDB(context),
                    getInitialOtherOnTheAirTVListDataDB(), MovieDataURL.GetOnTheAirTVURL(context)));
        }

        private void GetPopularTVList() throws Exception
        {
            DBGetter.GetData(new TVDataGetter(context, new PopularTVDataDB(context),
                    getInitialOtherPopularTVListDataDB(), MovieDataURL.GetPopularTVURL(context)));
        }

        private void GetTopRateTVList() throws Exception
        {
            DBGetter.GetData(new TVDataGetter(context, new TopRatedTVDataDB(context),
                    getInitialOtherTopRateTVListDataDB(), MovieDataURL.GetTopRateTVURL(context)));
        }

        private void GetTVGenreList() throws Exception
        {
            DBGetter.GetData(new TVGenreDataGetter(context, MovieDataURL.GetTVGenreListURL(context)));
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


}
