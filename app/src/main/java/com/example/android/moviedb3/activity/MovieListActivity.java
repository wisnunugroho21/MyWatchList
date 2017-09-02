package com.example.android.moviedb3.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.adapter.FragmentAdapter.HomeFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.TopListFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.YoursFragmentAdapter;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;

public class MovieListActivity extends AppCompatActivity
{
    BottomNavigationView movieListBottomNavigationView;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    ViewPager viewPager;
    TabLayout tabLayout;

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

        setSupportActionBar(toolbar);

        SetIntialMovieListFragment();
        SetNavigationDrawer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode)
        {
            case MovieDBKeyEntry.DatabaseHasChanged.FAVORITE_DATA_CHANGED :
                SetMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                        MovieDBKeyEntry.MovieListPageIndex.FAVORITE_PAGE_INDEX);
                break;

            case MovieDBKeyEntry.DatabaseHasChanged.INSERT_PLAN_TO_WATCH_LIST:
                SetMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                        MovieDBKeyEntry.MovieListPageIndex.PLAN_TO_WATCH_PAGE_INDEX);
                break;

            case MovieDBKeyEntry.DatabaseHasChanged.INSERT_TO_WATCHED_LIST:
                SetMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
                        MovieDBKeyEntry.MovieListPageIndex.WATCHED_PAGE_INDEX);
                break;

            case MovieDBKeyEntry.DatabaseHasChanged.REMOVE_MY_LIST:
                SetMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX),
                        MovieDBKeyEntry.MovieListPageIndex.NOW_SHOWING_PAGE_INDEX);
                break;
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
        SetMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX),
                MovieDBKeyEntry.MovieListPageIndex.NOW_SHOWING_PAGE_INDEX);
    }

    private FragmentStatePagerAdapter setFragmentPagerAdapter(int fragmentAdapterIndex)
    {
        switch (fragmentAdapterIndex)
        {
            case MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX : return new HomeFragmentAdapter(getSupportFragmentManager(), this);
            case MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX: return new TopListFragmentAdapter(getSupportFragmentManager(), this);
            case MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX: return new YoursFragmentAdapter(getSupportFragmentManager(), this);

            default: return null;
        }
    }

    private void SetMovieListFragment(FragmentStatePagerAdapter fragmentPagerAdapter, int selectedPageIndex)
    {
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setScrollPosition(selectedPageIndex, 0f, true);
        viewPager.setCurrentItem(selectedPageIndex);
    }

    private class MainMovieListBottomNavigationView implements BottomNavigationView.OnNavigationItemSelectedListener
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.home_movie_list_item_menu :
                    SetMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.NOW_SHOWING_PAGE_INDEX);
                    SetActionBar(getString(R.string.home_label));
                    return true;

                case R.id.top_list_movie_list_item_menu :
                    SetMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX),
                            MovieDBKeyEntry.MovieListPageIndex.POPULAR_PAGE_INDEX);
                    SetActionBar(getString(R.string.top_list_label));
                    return true;

                case R.id.yours_movie_list_item_menu :
                    SetMovieListFragment(setFragmentPagerAdapter(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX),
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
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }
}