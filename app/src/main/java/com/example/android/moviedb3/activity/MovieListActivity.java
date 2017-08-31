package com.example.android.moviedb3.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.adapter.FragmentAdapter.HomeFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.TopListFragmentAdapter;
import com.example.android.moviedb3.fragment.MovieListFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;

public class MovieListActivity extends AppCompatActivity
{
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar_movie_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.top_list_label);

        viewPager = (ViewPager) findViewById(R.id.vp_movie_list);
        viewPager.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager(), this));

        tabLayout = (TabLayout) findViewById(R.id.tabs_movie_list);
        tabLayout.setupWithViewPager(viewPager);
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        MovieListFragment movieListFragment = new MovieListFragment();
        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(this, R.id.movie_list_layout, movieListFragment));
    }*/


}
