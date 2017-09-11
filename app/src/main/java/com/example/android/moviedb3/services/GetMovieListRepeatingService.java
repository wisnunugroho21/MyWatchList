package com.example.android.moviedb3.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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

            GetNowShowingMovieList();
            GetComingSoonMovieList();
            GetPopularMovieList();
            GetTopRateMovieList();
            GetGenreList();

            PeriodUpdateNotificationUtils.createFinishPeriodUpdateNotification(context, numberNewNowShowingMovie);
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


}
