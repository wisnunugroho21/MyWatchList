package com.example.android.moviedb3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.MovieListFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;

public class MovieListActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        MovieListFragment movieListFragment = new MovieListFragment();
        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(this, R.id.movie_list_layout, movieListFragment));
    }
}
