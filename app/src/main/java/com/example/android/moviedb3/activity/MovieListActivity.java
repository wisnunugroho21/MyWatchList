package com.example.android.moviedb3.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.TabsMovieListFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;

public class MovieListActivity extends AppCompatActivity
{
    BottomNavigationView movieListBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieListBottomNavigationView = (BottomNavigationView) findViewById(R.id.btv_main_movie_list);
        movieListBottomNavigationView.setOnNavigationItemSelectedListener(new MainMovieListBottomNavigationView());

        SetIntialFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        TabsMovieListFragment tabsMovieListFragment;

        switch (resultCode)
        {
            case MovieDBKeyEntry.DatabaseHasChanged.FAVORITE_DATA_CHANGED :
                tabsMovieListFragment = new TabsMovieListFragment();
                movieListBottomNavigationView.setSelectedItemId(R.id.yours_movie_list_item_menu);

                tabsMovieListFragment.setFragmentPagerAdapterIndex(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX);
                tabsMovieListFragment.setSelectedPageIndex(0);
                tabsMovieListFragment.setFragmentTitle(getString(R.string.mylist_label));

                FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
                break;

            case MovieDBKeyEntry.DatabaseHasChanged.INSERT_PLAN_TO_WATCH_LIST:
                tabsMovieListFragment = new TabsMovieListFragment();
                movieListBottomNavigationView.setSelectedItemId(R.id.yours_movie_list_item_menu);

                tabsMovieListFragment.setFragmentPagerAdapterIndex(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX);
                tabsMovieListFragment.setSelectedPageIndex(2);
                tabsMovieListFragment.setFragmentTitle(getString(R.string.mylist_label));

                FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
                break;

            case MovieDBKeyEntry.DatabaseHasChanged.INSERT_TO_WATCHED_LIST:
                tabsMovieListFragment = new TabsMovieListFragment();
                movieListBottomNavigationView.setSelectedItemId(R.id.yours_movie_list_item_menu);

                tabsMovieListFragment.setFragmentPagerAdapterIndex(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX);
                tabsMovieListFragment.setSelectedPageIndex(1);
                tabsMovieListFragment.setFragmentTitle(getString(R.string.mylist_label));

                FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
                break;

            case MovieDBKeyEntry.DatabaseHasChanged.REMOVE_MY_LIST:
                tabsMovieListFragment = new TabsMovieListFragment();
                movieListBottomNavigationView.setSelectedItemId(R.id.home_movie_list_item_menu);

                tabsMovieListFragment.setFragmentPagerAdapterIndex(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX);
                tabsMovieListFragment.setSelectedPageIndex(0);
                tabsMovieListFragment.setFragmentTitle(getString(R.string.home_label));

                FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
                break;
        }

    }

    private void SetIntialFragment()
    {
        TabsMovieListFragment tabsMovieListFragment = new TabsMovieListFragment();

        tabsMovieListFragment.setFragmentPagerAdapterIndex(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX);
        tabsMovieListFragment.setFragmentTitle(getString(R.string.home_label));

        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
    }

    private class MainMovieListBottomNavigationView implements BottomNavigationView.OnNavigationItemSelectedListener
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            TabsMovieListFragment tabsMovieListFragment;

            switch (item.getItemId())
            {
                case R.id.home_movie_list_item_menu :
                    tabsMovieListFragment = new TabsMovieListFragment();

                    tabsMovieListFragment.setFragmentPagerAdapterIndex(MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX);
                    tabsMovieListFragment.setFragmentTitle(getString(R.string.home_label));

                    FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
                    return true;

                case R.id.top_list_movie_list_item_menu :
                    tabsMovieListFragment = new TabsMovieListFragment();

                    tabsMovieListFragment.setFragmentPagerAdapterIndex(MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX);
                    tabsMovieListFragment.setFragmentTitle(getString(R.string.top_list_label));

                    FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
                    return true;

                case R.id.yours_movie_list_item_menu :
                    tabsMovieListFragment = new TabsMovieListFragment();

                    tabsMovieListFragment.setFragmentPagerAdapterIndex(MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX);
                    tabsMovieListFragment.setFragmentTitle(getString(R.string.mylist_label));

                    FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
                    return true;

                /*case R.id.library_movie_list_item_menu :
                    return true;*/

                default:
                    return true;
            }
        }
    }
}

/*@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_activity_movie_layout);

        MovieListFragment movieListFragment = new MovieListFragment();
        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(this, R.id.movie_list_layout, movieListFragment));
    }*/
