package com.example.android.moviedb3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.GenreListFragment;
import com.example.android.moviedb3.fragment.TVGenreListFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;

public class TVGenreListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvgenre_list); setGenreListFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.genre_label);
    }

    private void setGenreListFragment()
    {
        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(getSupportFragmentManager(), R.id.tv_genre_list_layout, new TVGenreListFragment()));
    }
}
