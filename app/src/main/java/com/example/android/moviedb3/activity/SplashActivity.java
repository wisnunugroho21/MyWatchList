package com.example.android.moviedb3.activity;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.example.android.moviedb3.localDatabase.PlanToWatchDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class SplashActivity extends AppCompatActivity {

    TextView stillLoadingTextView;
    ConstraintLayout splashActivityLayout;
    int amountDataObtained;

    private Runnable visibleLoadingTextRunnable = new Runnable() {
        @Override
        public void run()
        {
            stillLoadingTextView.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(callMainMovieObtainedListener, 3000);
        }
    };

    private Runnable callMainMovieObtainedListener = new Runnable()
    {
        @Override
        public void run()
        {
            MainMovieListObtainedListener mainMovieListObtainedListener = new MainMovieListObtainedListener();
            mainMovieListObtainedListener.onComplete(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        stillLoadingTextView = (TextView) findViewById(R.id.txt_still_loading_splash_activity);
        splashActivityLayout = (ConstraintLayout) findViewById(R.id.splash_activity_layout);

        TransitionManager.beginDelayedTransition(splashActivityLayout);
        stillLoadingTextView.setVisibility(View.GONE);

        amountDataObtained = 0;

        GetNowShowingMovieList();
        GetComingSoonMovieList();
        GetPopularMovieList();
        GetTopRateMovieList();

        Handler handler = new Handler();
        handler.postDelayed(visibleLoadingTextRunnable, 5000);
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

    private void GetPopularMovieList()
    {
        MovieDataGetter movieDataGetter = new MovieDataGetter(this,
                new MainMovieListObtainedListener(), new PopularDataDB(this),
                getInitialOtherPopularMovieListDataDB(), MovieDataURL.GetPopularURL());
        movieDataGetter.Execute();
    }

    private void GetTopRateMovieList()
    {
        MovieDataGetter movieDataGetter = new MovieDataGetter(this,
                new MainMovieListObtainedListener(), new TopRateDataDB(this),
                getInitialOtherTopRateMovieListDataDB(), MovieDataURL.GetTopRateURL());
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

    private class MainMovieListObtainedListener implements OnAsyncTaskCompleteListener
    {
        @Override
        public void onComplete(boolean isSuccess)
        {
            if(amountDataObtained >= 4)
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
