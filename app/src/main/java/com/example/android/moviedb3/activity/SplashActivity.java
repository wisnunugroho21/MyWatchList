package com.example.android.moviedb3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activityShifter.ActivityLauncher;
import com.example.android.moviedb3.activityShifter.DefaultIActivityLauncher;
import com.example.android.moviedb3.dataManager.movieDBGetter.MovieDataGetter;
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.NowShowingDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    int amountDataObtained;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        amountDataObtained = 0;

        GetNowShowingMovieList();
        GetComingSoonMovieList();
    }

    private void GetNowShowingMovieList()
    {
        MovieDataGetter movieDataGetter = new MovieDataGetter(this,
                new MainMovieListObtainedListener(), new NowShowingDataDB(this),
                getInitialOtherNowShowingMovieListDataDB(), MovieDataURL.GetNowShowingURL());
        movieDataGetter.Execute();
    }

    private void GetComingSoonMovieList()
    {
        MovieDataGetter movieDataGetter = new MovieDataGetter(this,
                new MainMovieListObtainedListener(), new ComingSoonDataDB(this),
                getInitialOtherComingSoonMovieListDataDB(), MovieDataURL.GetComingSoonURL());
        movieDataGetter.Execute();
    }

    private ArrayList<DataDB<String>> getInitialOtherNowShowingMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new ComingSoonDataDB(this));
        dataDBArrayList.add(new PopularDataDB(this));
        dataDBArrayList.add(new TopRateDataDB(this));
        dataDBArrayList.add(new FavoriteDataDB(this));
        dataDBArrayList.add(new WatchlistDataDB(this));

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

        return dataDBArrayList;
    }

    private class MainMovieListObtainedListener implements OnAsyncTaskCompleteListener
    {
        @Override
        public void onComplete(boolean isSuccess)
        {
            if(amountDataObtained >= 1)
            {
                ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(MovieListActivity.class, SplashActivity.this));
            }

            else
            {
                amountDataObtained++;
            }
        }
    }
}
