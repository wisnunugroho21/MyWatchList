package com.example.android.moviedb3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.moviedb3.BuildConfig;
import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.MovieDetailFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(this, R.id.movie_detail_layout, movieDetailFragment, getIntent().getExtras()));
    }

}
