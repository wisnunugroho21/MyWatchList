package com.example.android.moviedb3.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.TVDetailFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TVDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        TVDetailFragment tvDetailFragment = new TVDetailFragment();
        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(getSupportFragmentManager(), R.id.tv_detail_layout, tvDetailFragment, getIntent().getExtras()));
    }
}
