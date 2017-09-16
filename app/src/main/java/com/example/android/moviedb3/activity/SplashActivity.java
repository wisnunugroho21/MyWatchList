package com.example.android.moviedb3.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activityShifter.ActivityLauncher;
import com.example.android.moviedb3.activityShifter.DefaultIActivityLauncher;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.services.GetMovieListIntentService;
import com.example.android.moviedb3.sharedPreferences.DefaultBooleanStatePreference;
import com.example.android.moviedb3.sharedPreferences.DefaultStringStatePreference;
import com.example.android.moviedb3.sharedPreferences.PreferencesUtils;

public class SplashActivity extends AppCompatActivity {

    ConstraintLayout splashActivityLayout;

    ImageView splashAppLogoImage;
    TextView appTitleSplashTextView;
    TextView downloadDataTextView;
    ProgressBar downloadDataProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashActivityLayout = (ConstraintLayout) findViewById(R.id.splash_activity_layout);
        appTitleSplashTextView = (TextView) findViewById(R.id.txt_app_title_splash_activity);
        splashAppLogoImage = (ImageView) findViewById(R.id.iv_splash_image);
        downloadDataTextView = (TextView) findViewById(R.id.txt_downloading_data_first_time);
        downloadDataProgressBar = (ProgressBar) findViewById(R.id.pb_downloading_data);

        appTitleSplashTextView.setVisibility(View.VISIBLE);
        splashAppLogoImage.setVisibility(View.VISIBLE);
        downloadDataTextView.setVisibility(View.GONE);
        downloadDataProgressBar.setVisibility(View.GONE);

        if(PreferencesUtils.GetData(new DefaultBooleanStatePreference(this), MovieDBKeyEntry.SharedPreferencesKey.CHECK_FIRST_TIME, true)
                || PreferencesUtils.GetData(new DefaultBooleanStatePreference(this), getString(R.string.update_when_open_apps_key), false))
        {
            startGetDataService();

            DoSomethingIfThisIsFirstTime doSomethingIfThisIsFirstTime = new DoSomethingIfThisIsFirstTime();
            doSomethingIfThisIsFirstTime.execute();
        }

        else
        {
            DoSomethingIfThisIsNotFirstTime doSomethingIfThisIsNotFirstTime = new DoSomethingIfThisIsNotFirstTime();
            doSomethingIfThisIsNotFirstTime.execute();
        }
    }

    private void LaunchToNextActivity()
    {
        PreferencesUtils.SetData(new DefaultBooleanStatePreference(this), false, MovieDBKeyEntry.SharedPreferencesKey.CHECK_FIRST_TIME);
        ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(TVMovieListActivity.class, this));
        finish();
    }

    private void startGetDataService()
    {
        Intent intent = new Intent(this, GetMovieListIntentService.class);
        intent.putExtra(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_RESULT_RECEIVER,
                new GetInitialDataResultReceiver(new Handler(), this));
        startService(intent);
    }

    private void AnimateFadeLoadingData()
    {
        appTitleSplashTextView.animate()
                .alpha(0f)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        appTitleSplashTextView.setVisibility(View.GONE);
                    }
                });

        splashAppLogoImage.animate()
                .alpha(0f)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        splashAppLogoImage.setVisibility(View.GONE);
                    }
                });

        downloadDataTextView.setVisibility(View.VISIBLE);
        downloadDataTextView.setAlpha(0f);
        downloadDataTextView.animate()
                .alpha(1f)
                .setDuration(200)
                .setListener(null);

        downloadDataProgressBar.setVisibility(View.VISIBLE);
        downloadDataProgressBar.setAlpha(0f);
        downloadDataProgressBar.animate()
                .alpha(1f)
                .setDuration(200)
                .setListener(null);
        downloadDataProgressBar.setIndeterminate(true);
    }

    public void AnimateFadeFinishLoadingData()
    {
        downloadDataTextView.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        downloadDataTextView.setVisibility(View.GONE);
                    }
                });

        downloadDataProgressBar.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        downloadDataProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void SetInitialSharedPreference()
    {
        PreferencesUtils.SetData(new DefaultStringStatePreference(this), getString(R.string.region_key), getString(R.string.region_usa_value));
        PreferencesUtils.SetData(new DefaultStringStatePreference(this), getString(R.string.content_language_key), getString(R.string.content_language_english_value));
        PreferencesUtils.SetData(new DefaultBooleanStatePreference(this),false, getString(R.string.period_update_key));
        PreferencesUtils.SetData(new DefaultStringStatePreference(this), getString(R.string.update_period_key), getString(R.string.update_period_values_12_hours));
        PreferencesUtils.SetData(new DefaultBooleanStatePreference(this), false, getString(R.string.only_on_wifi_key));
        PreferencesUtils.SetData(new DefaultStringStatePreference(this), getString(R.string.type_notification_key), getString(R.string.normal_led_notification_value));
    }

    private class DoSomethingIfThisIsNotFirstTime extends AsyncTask<Void, Void , Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            LaunchToNextActivity();
        }
    }

    private class DoSomethingIfThisIsFirstTime extends AsyncTask<Void, Void , Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
//            SetInitialSharedPreference();

            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            AnimateFadeLoadingData();
        }
    }

    private class GetInitialDataResultReceiver extends ResultReceiver
    {
        Context context;

        public GetInitialDataResultReceiver(Handler handler, Context context)
        {
            super(handler);
            this.context = context;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData)
        {
            if(resultCode == MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_RESULT_SUCCESS)
            {
                AnimateFadeFinishLoadingData();
                LaunchToNextActivity();
            }
        }
    }
}

    /*private BroadcastReceiver setBroadCastReceiver()
    {
        return new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                LaunchToNextActivity();
            }
        };
    }*/

    /*private void SetJobSchedulerToGetData()
    {
        broadcastReceiver = setBroadCastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(MovieDBKeyEntry.GET_MOVIE_LIST_MESSENGER));

        JobInfo initialJobInfo = new JobInfo.Builder(MovieDBKeyEntry.JobSchedulerID.FIRST_GET_DATA, new ComponentName(getPackageName(), GetInitialDataService.class.getName()))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        jobScheduler.schedule(initialJobInfo);
    }*/

/*private class GetInitialDataResultReceiver extends ResultReceiver
    {
        Context context;

        public GetInitialDataResultReceiver(Handler handler, Context context)
        {
            super(handler);
            this.context = context;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData)
        {
            if(resultCode == MovieDBKeyEntry.GET_MOVIE_LIST_RESULT_SUCCESS)
            {
                ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(TVMovieListActivity.class, context));
                SplashActivity.this.finish();
            }
        }
    }*/

/*int amountDataObtained;

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
    };*/

/*private void GetNowShowingMovieList()
    {
        DBGetter.GetData(new MovieDataGetterAsyncTask(this,
                new MainMovieListObtainedListener(), new NowShowingDataDB(this),
                getInitialOtherNowShowingMovieListDataDB(), MovieDataURL.GetNowShowingURL()));
    }

    private void GetComingSoonMovieList()
    {
        DBGetter.GetData(new MovieDataGetterAsyncTask(this,
                new MainMovieListObtainedListener(), new ComingSoonDataDB(this),
                getInitialOtherComingSoonMovieListDataDB(), MovieDataURL.GetComingSoonURL()));
    }

    private void GetPopularMovieList()
    {
        DBGetter.GetData(new MovieDataGetterAsyncTask(this,
                new MainMovieListObtainedListener(), new PopularDataDB(this),
                getInitialOtherPopularMovieListDataDB(), MovieDataURL.GetPopularURL()));
    }

    private void GetTopRateMovieList()
    {
        DBGetter.GetData(new MovieDataGetterAsyncTask(this,
                new MainMovieListObtainedListener(), new TopRateDataDB(this),
                getInitialOtherTopRateMovieListDataDB(), MovieDataURL.GetTopRateURL()));
    }

    private void GetGenreList()
    {
        DBGetter.GetData(new GenreDataGetterAsyncTask(this, MovieDataURL.GetGenreListURL(), new MainMovieListObtainedListener()));
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
                ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(TVMovieListActivity.class, SplashActivity.this));
                finish();
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
*/
