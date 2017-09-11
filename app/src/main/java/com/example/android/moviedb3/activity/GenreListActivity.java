package com.example.android.moviedb3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.GenreListFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;

public class GenreListActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);

        setGenreListFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.genre_label);
    }

    private void setGenreListFragment()
    {
        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(getSupportFragmentManager(), R.id.genre_list_layout, new GenreListFragment()));
    }
}
