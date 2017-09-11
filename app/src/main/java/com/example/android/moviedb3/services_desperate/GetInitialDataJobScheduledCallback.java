package com.example.android.moviedb3.services_desperate;

import android.content.Context;
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
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.GenreDataGetter;
import com.example.android.moviedb3.movieDataManager.MovieDataGetter;
import com.example.android.moviedb3.services.PeriodUpdateNotificationUtils;

import java.util.ArrayList;

import io.hypertrack.smart_scheduler.Job;
import io.hypertrack.smart_scheduler.SmartScheduler;

/**
 * Created by nugroho on 08/09/17.
 */

public class GetInitialDataJobScheduledCallback implements SmartScheduler.JobScheduledCallback
{
    @Override
    public void onJobScheduled(Context context, Job job)
    {
        GetInitialDataAsyncTask getInitialDataAsyncTask = new GetInitialDataAsyncTask(context);
        getInitialDataAsyncTask.execute();
    }

    private class GetInitialDataAsyncTask extends AsyncTask<Void, Void, Void>
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
            GetNowShowingMovieList();
            GetComingSoonMovieList();
            GetPopularMovieList();
            GetTopRateMovieList();
            GetGenreList();

//            PeriodUpdateNotificationUtils.showNotification(context, numberNewNowShowingMovie);

            return null;
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
