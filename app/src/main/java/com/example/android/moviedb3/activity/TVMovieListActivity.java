package com.example.android.moviedb3.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activityShifter.ActivityLauncher;
import com.example.android.moviedb3.activityShifter.DefaultIActivityLauncher;
import com.example.android.moviedb3.adapter.FragmentAdapter.HomeFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.HomeTVFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.TopListFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.TopListTVFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.YoursFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.YoursTVFragmentAdapter;
import com.example.android.moviedb3.behaviour.BottomNavigationViewBehaviour;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.services.GetMovieListProgressService;
import com.example.android.moviedb3.services.GetMovieListRepeatingService;
import com.example.android.moviedb3.services.JobSchedulerUtils;
import com.example.android.moviedb3.services.PeriodicNetworkJobScheduler;
import com.example.android.moviedb3.sharedPreferences.DefaultBooleanStatePreference;
import com.example.android.moviedb3.sharedPreferences.DefaultStringStatePreference;
import com.example.android.moviedb3.sharedPreferences.PreferencesUtils;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;

public class TVMovieListActivity extends AppCompatActivity
{
    BottomNavigationView movieListBottomNavigationView;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    SettingChangedListener settingChangedListener;
    GetMovieListBroadcastReceiver getMovieListBroadcastReceiver;
    UpdateYoursMovieListBroadcastReceiver updateYoursMovieListBroadcastReceiver;

    int btvHeight;

    boolean isMovieMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieListBottomNavigationView = (BottomNavigationView) findViewById(R.id.btv_main_movie_list);
        movieListBottomNavigationView.setOnNavigationItemSelectedListener(new MainMovieListBottomNavigationView());

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar_movie_list);
        viewPager = (ViewPager) findViewById(R.id.vp_movie_list);
        tabLayout = (TabLayout) findViewById(R.id.tabs_movie_list);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) movieListBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehaviour());

        setSupportActionBar(toolbar);

        SetIntialMovieListFragment();
        SetNavigationDrawer();
        registerSettingChangedListener();
        registerMovieListBroadcastReceiver();
        registerUpdateYoursMovieListBroadcastReceiver();

        btvHeight = movieListBottomNavigationView.getHeight();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (resultCode)
        {
            case MovieDBKeyEntry.DatabaseHasChanged.FAVORITE_DATA_CHANGED :
                SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                        MovieDBKeyEntry.MovieListPageIndex.FAVORITE_PAGE_INDEX);
                break;

            case MovieDBKeyEntry.DatabaseHasChanged.INSERT_PLAN_TO_WATCH_LIST:
                SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                        MovieDBKeyEntry.MovieListPageIndex.PLAN_TO_WATCH_PAGE_INDEX);
                break;

            case MovieDBKeyEntry.DatabaseHasChanged.INSERT_TO_WATCHED_LIST:
                SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                        MovieDBKeyEntry.MovieListPageIndex.WATCHED_PAGE_INDEX);
                break;

            case MovieDBKeyEntry.DatabaseHasChanged.REMOVE_MY_LIST:
                SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX),
                        MovieDBKeyEntry.MovieListPageIndex.NOW_SHOWING_PAGE_INDEX);
                break;
        }
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterSettingChangedListener();
        unregisterMovieListBroadcastReceiver();
        unregisterUpdateYoursMovieListBroadcastReceiver();
    }

    private void registerSettingChangedListener()
    {
        settingChangedListener = new SettingChangedListener();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(settingChangedListener);
    }

    private void unregisterSettingChangedListener()
    {
        if(settingChangedListener != null)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(settingChangedListener);
        }
    }

    private void registerMovieListBroadcastReceiver()
    {
        getMovieListBroadcastReceiver = new GetMovieListBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(getMovieListBroadcastReceiver, new IntentFilter(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_MESSENGER));
    }

    private void unregisterMovieListBroadcastReceiver()
    {
        if(getMovieListBroadcastReceiver != null)
        {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(getMovieListBroadcastReceiver);
        }
    }

    private void registerUpdateYoursMovieListBroadcastReceiver()
    {
        updateYoursMovieListBroadcastReceiver = new UpdateYoursMovieListBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(updateYoursMovieListBroadcastReceiver, new IntentFilter(MovieDBKeyEntry.Messanger.UPDATE_MOVIE_LIST_MESSANGER));
    }

    private void unregisterUpdateYoursMovieListBroadcastReceiver()
    {
        if(updateYoursMovieListBroadcastReceiver != null)
        {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(updateYoursMovieListBroadcastReceiver);
        }
    }

    private void SetNavigationDrawer()
    {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new TabsMovieActionBarDrawer());
    }

    private void SetActionBar(String fragmentTitle)
    {
        getSupportActionBar().setTitle(fragmentTitle);
    }

    private void SetIntialMovieListFragment()
    {
        isMovieMode = true;
        getSupportActionBar().setTitle(getString(R.string.movie_label));

        SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX),
                MovieDBKeyEntry.MovieListPageIndex.NOW_SHOWING_PAGE_INDEX);
    }

    private void SetIntialTVListFragment()
    {
        isMovieMode = false;
        getSupportActionBar().setTitle(getString(R.string.tv_label));

        SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX),
                MovieDBKeyEntry.MovieListPageIndex.NOW_SHOWING_PAGE_INDEX);
    }

    private FragmentStatePagerAdapter setFragmentPagerAdapter(int fragmentAdapterIndex)
    {
        if(isMovieMode)
        {
            switch (fragmentAdapterIndex)
            {
                case MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX : return new HomeFragmentAdapter(getSupportFragmentManager(), this);
                case MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX: return new TopListFragmentAdapter(getSupportFragmentManager(), this);
                case MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX: return new YoursFragmentAdapter(getSupportFragmentManager(), this);

                default: return null;
            }
        }

        else
        {
            switch (fragmentAdapterIndex)
            {
                case MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX : return new HomeTVFragmentAdapter(getSupportFragmentManager(), this);
                case MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX: return new TopListTVFragmentAdapter(getSupportFragmentManager(), this);
                case MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX: return new YoursTVFragmentAdapter(getSupportFragmentManager(), this);

                default: return null;
            }
        }
    }

    private void SetTVMovieListFragment(FragmentStatePagerAdapter fragmentPagerAdapter, int selectedPageIndex)
    {
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.getTabAt(selectedPageIndex);
        tab.select();

        /*tabLayout.setScrollPosition(selectedPageIndex, 0f, true);
        viewPager.setCurrentItem(selectedPageIndex)*/;
    }

    private void startGetDataService()
    {
        Intent intent = new Intent(this, GetMovieListProgressService.class);
        startService(intent);
    }

    private class MainMovieListBottomNavigationView implements BottomNavigationView.OnNavigationItemSelectedListener
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.home_movie_list_item_menu :
                    SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.NOW_SHOWING_PAGE_INDEX);
                    SetActionBar(getString(R.string.home_label));
                    return true;

                case R.id.top_list_movie_list_item_menu :
                    SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.POPULAR_PAGE_INDEX);
                    SetActionBar(getString(R.string.top_list_label));
                    return true;

                case R.id.yours_movie_list_item_menu :
                    SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.FAVORITE_PAGE_INDEX);
                    SetActionBar(getString(R.string.my_list_label));
                    return true;

                default:
                    return true;
            }
        }
    }

    private class TabsMovieActionBarDrawer implements NavigationView.OnNavigationItemSelectedListener
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.movie_drawer_item_menu:
                    SetIntialMovieListFragment();
                    break;

                case R.id.tv_drawer_item_menu:
                    SetIntialTVListFragment();
                    break;

                case R.id.genre_movie_drawer_item_menu:
                    ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(GenreListActivity.class, 0, TVMovieListActivity.this));
                    break;

                case R.id.genre_tv_drawer_item_menu:
                    ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(TVGenreListActivity.class, 0, TVMovieListActivity.this));
                    break;

                case R.id.setting_drawer_item_menu:
                    ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(SettingActivity.class, TVMovieListActivity.this));
                    break;

                case R.id.people_drawer_item_menu:
                    ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(PeoplePopularListActivity.class, 0, TVMovieListActivity.this));
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }

    private class SettingChangedListener implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
        {
            Context context = TVMovieListActivity.this;

            if(key.equals(getString(R.string.content_language_key)) || key.equals(getString(R.string.region_key)))
            {
                startGetDataService();
            }

            else if(key.equals(getString(R.string.period_update_key)) || key.equals(getString(R.string.update_period_key)) || key.equals(getString(R.string.only_on_wifi_key)))
            {
                boolean isPeriodUpdateTurnOn = PreferencesUtils.GetData(new DefaultBooleanStatePreference(context), getString(R.string.period_update_key), false);

                if(isPeriodUpdateTurnOn)
                {
                    SetPeriodUpdateAsyncTask setPeriodUpdateAsyncTask = new SetPeriodUpdateAsyncTask();
                    setPeriodUpdateAsyncTask.execute();
                }

                else
                {
                    UnSetPeriodUpdateAsyncTask unSetPeriodUpdateAsyncTask = new UnSetPeriodUpdateAsyncTask();
                    unSetPeriodUpdateAsyncTask.execute();
                }
            }
        }
    }

    private class GetMovieListBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            SetIntialMovieListFragment();
        }
    }

    private class SetPeriodUpdateAsyncTask extends AsyncTask<Void, Void, Boolean>
    {
        Context context;

        @Override
        protected Boolean doInBackground(Void... params)
        {
            context = TVMovieListActivity.this;

            String updatePeriodString = PreferencesUtils.GetData(new DefaultStringStatePreference(context), getString(R.string.update_period_key), getString(R.string.update_period_values_12_hours));
            boolean isWifiOnly = PreferencesUtils.GetData(new DefaultBooleanStatePreference(context), getString(R.string.only_on_wifi_key), false);

            int updatePeriodSecond = 0;
            if(updatePeriodString.equals(getString(R.string.update_period_label_4_hours)))
            {
                updatePeriodSecond = 14400;
            }

            else if(updatePeriodString.equals(getString(R.string.update_period_label_6_hours)))
            {
                updatePeriodSecond = 21600;
            }

            else if(updatePeriodString.equals(getString(R.string.update_period_label_8_hours)))
            {
                updatePeriodSecond = 28800;
            }

            else if(updatePeriodString.equals(getString(R.string.update_period_values_12_hours)))
            {
                updatePeriodSecond = 43200;
            }

            return JobSchedulerUtils.doJobScheduling(new PeriodicNetworkJobScheduler<>(context, updatePeriodSecond, isWifiOnly, MovieDBKeyEntry.JobSchedulerID.PERIODIC_NETWORK_JOB_KEY, GetMovieListRepeatingService.class))
                    == FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS;
        }

        @Override
        protected void onPostExecute(Boolean bool)
        {
            if(bool)
            {
                Toast.makeText(context, R.string.success_update_period_toas_message, Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(context, R.string.fail_update_period_toas_message, Toast.LENGTH_SHORT).show();

                PreferencesUtils.SetData(new DefaultBooleanStatePreference(context),false, getString(R.string.period_update_key));
                PreferencesUtils.SetData(new DefaultStringStatePreference(context), getString(R.string.update_period_key), getString(R.string.update_period_values_12_hours));
                PreferencesUtils.SetData(new DefaultBooleanStatePreference(context), false, getString(R.string.only_on_wifi_key));
                PreferencesUtils.SetData(new DefaultStringStatePreference(context), getString(R.string.type_notification_key), getString(R.string.normal_led_notification_value));
            }
        }

    }

    private class UnSetPeriodUpdateAsyncTask extends AsyncTask<Void, Void, Boolean>
    {
        Context context;

        @Override
        protected Boolean doInBackground(Void... params)
        {
            context = TVMovieListActivity.this;
            return JobSchedulerUtils.cancelJobScheduling(new PeriodicNetworkJobScheduler<>(context, MovieDBKeyEntry.JobSchedulerID.PERIODIC_NETWORK_JOB_KEY)) == FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if(aBoolean)
            {
                Toast.makeText(context, R.string.success_cancel_update_period_toas_message, Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(context, R.string.fail_cancel_update_period_toas_message, Toast.LENGTH_SHORT).show();
                PreferencesUtils.SetData(new DefaultBooleanStatePreference(context),true, getString(R.string.period_update_key));
            }
        }
    }

    private class UpdateYoursMovieListBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            int result = intent.getIntExtra(MovieDBKeyEntry.Messanger.UPDATE_YOURS_MOVIE_LIST, 0);

            switch (result)
            {
                case MovieDBKeyEntry.DatabaseHasChanged.FAVORITE_DATA_CHANGED :
                    SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.FAVORITE_PAGE_INDEX);
                    break;

                case MovieDBKeyEntry.DatabaseHasChanged.INSERT_PLAN_TO_WATCH_LIST:
                    SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.PLAN_TO_WATCH_PAGE_INDEX);
                    break;

                case MovieDBKeyEntry.DatabaseHasChanged.INSERT_TO_WATCHED_LIST:
                    SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.WATCHED_PAGE_INDEX);
                    break;

                case MovieDBKeyEntry.DatabaseHasChanged.REMOVE_MY_LIST:
                    SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.NOW_SHOWING_PAGE_INDEX);
                    break;
            }

            movieListBottomNavigationView.animate().translationY(btvHeight).setDuration(100);
        }
    }
}