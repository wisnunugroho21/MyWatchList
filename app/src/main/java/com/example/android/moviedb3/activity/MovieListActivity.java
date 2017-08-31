package com.example.android.moviedb3.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.adapter.FragmentAdapter.HomeFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.TopListFragmentAdapter;
import com.example.android.moviedb3.fragment.MovieDetailFragment;
import com.example.android.moviedb3.fragment.TabsMovieListFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;

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

    private void SetIntialFragment()
    {
        TabsMovieListFragment tabsMovieListFragment = new TabsMovieListFragment();

        tabsMovieListFragment.setFragmentPagerAdapterIndex(0);
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

                    tabsMovieListFragment.setFragmentPagerAdapterIndex(0);
                    tabsMovieListFragment.setFragmentTitle(getString(R.string.home_label));

                    FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
                    return true;

                case R.id.top_list_movie_list_item_menu :
                    tabsMovieListFragment = new TabsMovieListFragment();

                    tabsMovieListFragment.setFragmentPagerAdapterIndex(1);
                    tabsMovieListFragment.setFragmentTitle(getString(R.string.top_list_label));

                    FragmentShifter.InitializeFragment(new DefaultFragmentShifter(MovieListActivity.this.getSupportFragmentManager(), R.id.main_bottom_navigation_movie_list_layout, tabsMovieListFragment));
                    return true;

                case R.id.yours_movie_list_item_menu :
                    return true;

                case R.id.library_movie_list_item_menu :
                    return true;

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
