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
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
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

        stillLoadingTextView = (TextView) findViewById(R.id.txt_app_title_splash_activity);
        splashActivityLayout = (ConstraintLayout) findViewById(R.id.splash_activity_layout);

        TransitionManager.beginDelayedTransition(splashActivityLayout);
        stillLoadingTextView.setVisibility(View.VISIBLE);

        amountDataObtained = 0;

        GetNowShowingMovieList();
    }

    private void GetNowShowingMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(this,
                new MainMovieListObtainedListener(), new NowShowingDataDB(this),
                getInitialOtherNowShowingMovieListDataDB(), MovieDataURL.GetNowShowingURL()));
    }

    private void GetComingSoonMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(this,
                new MainMovieListObtainedListener(), new ComingSoonDataDB(this),
                getInitialOtherComingSoonMovieListDataDB(), MovieDataURL.GetComingSoonURL()));
    }

    private void GetPopularMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(this,
                new MainMovieListObtainedListener(), new PopularDataDB(this),
                getInitialOtherPopularMovieListDataDB(), MovieDataURL.GetPopularURL()));
    }

    private void GetTopRateMovieList()
    {
        DBGetter.GetData(new MovieDataGetter(this,
                new MainMovieListObtainedListener(), new TopRateDataDB(this),
                getInitialOtherTopRateMovieListDataDB(), MovieDataURL.GetTopRateURL()));
    }

    private void GetGenreList()
    {
        DBGetter.GetData(new GenreDataGetter(this, MovieDataURL.GetGenreListURL(), new MainMovieListObtainedListener()));
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

                switch (amountDataObtained)
                {
                    case 1 : GetComingSoonMovieList(); break;
                    case 2 : GetPopularMovieList(); break;
                    case 3 : GetTopRateMovieList(); break;
                    case 4 : GetGenreList(); break;
                    default: break;
                }
            }
        }
    }




}
