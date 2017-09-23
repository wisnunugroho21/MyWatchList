package com.example.android.moviedb3.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class TVMovieListActivity extends AppCompatActivity
{
    BottomNavigationView movieListBottomNavigationView;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;


    GetMovieListBroadcastReceiver getMovieListBroadcastReceiver;
    UpdateYoursMovieListBroadcastReceiver updateYoursMovieListBroadcastReceiver;

    int btvHeight;
    boolean isMovieMode = true;
    boolean isLinearList = false;

    private FirebaseAnalytics firebaseAnalytics;

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
        tabLayout.addOnTabSelectedListener(new TabTVMovieList());

        btvHeight = movieListBottomNavigationView.getHeight();
        isMovieMode = true;
        isLinearList = false;

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) movieListBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehaviour());

        setSupportActionBar(toolbar);

        SetIntialMovieListFragment();
        SetNavigationDrawer();

        registerMovieListBroadcastReceiver();
        registerUpdateYoursMovieListBroadcastReceiver();
    }

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

        unregisterMovieListBroadcastReceiver();
        unregisterUpdateYoursMovieListBroadcastReceiver();

        viewPager.removeAllViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_tv_movie_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.view_mode_item_menu);

        if(isLinearList)
        {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_view_module_white_24px));
        }

        else
        {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_list_white_24px));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.view_mode_item_menu:
                isLinearList = !isLinearList;
                movieListBottomNavigationView.setSelectedItemId(movieListBottomNavigationView.getSelectedItemId());
                invalidateOptionsMenu();
                return true;

            case R.id.search_item_menu :
                ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(SearchActivity.class, this));
                return true;

            case R.id.updtae_list_item_menu :
                startGetDataService();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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
                case MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX : return new HomeFragmentAdapter(getSupportFragmentManager(), this, isLinearList);
                case MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX: return new TopListFragmentAdapter(getSupportFragmentManager(), this, isLinearList);
                case MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX: return new YoursFragmentAdapter(getSupportFragmentManager(), this, isLinearList);

                default: return null;
            }
        }

        else
        {
            switch (fragmentAdapterIndex)
            {
                case MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX : return new HomeTVFragmentAdapter(getSupportFragmentManager(), this, isLinearList);
                case MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX: return new TopListTVFragmentAdapter(getSupportFragmentManager(), this, isLinearList);
                case MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX: return new YoursTVFragmentAdapter(getSupportFragmentManager(), this, isLinearList);

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
    }

    private void startGetDataService()
    {
        Intent intent = new Intent(this, GetMovieListProgressService.class);
        startService(intent);
    }

    private void SendEmailToDeveloper()
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nugroho8dewantoro@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for My Watchlist apps");

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(Intent.createChooser(intent, "Send mail via"));
        }

        else
        {
            Toast.makeText(TVMovieListActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private class TabTVMovieList implements TabLayout.OnTabSelectedListener
    {
        @Override
        public void onTabSelected(TabLayout.Tab tab)
        {
            String id = String.valueOf((int )(Math.random() * Integer.MAX_VALUE + 1000000));

            String movieTV = "";
            if(isMovieMode)
            {
                movieTV = "movie";
            }

            else
            {
                movieTV = "tv";
            }

            Bundle bundle = new Bundle();
            bundle.putString("tvOrMovie", movieTV);
            bundle.putString("listName", tab.getText().toString());

            firebaseAnalytics.logEvent("selectedList", bundle);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
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
                    break;

                case R.id.top_list_movie_list_item_menu :
                    SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.POPULAR_PAGE_INDEX);
                    SetActionBar(getString(R.string.top_list_label));
                    break;

                case R.id.yours_movie_list_item_menu :
                    SetTVMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.FAVORITE_PAGE_INDEX);
                    SetActionBar(getString(R.string.my_list_label));
                    break;
            }

            return true;
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

                case R.id.feedback_drawer_item_menu :
                    SendEmailToDeveloper();
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



    private class GetMovieListBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            SetIntialMovieListFragment();
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