package com.example.android.moviedb3.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.PeopleDetailFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;

public class PeopleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detail);

        PeopleDetailFragment peopleDetailFragment = new PeopleDetailFragment();

        BundleDataGetter bundleDataGetter = new BundleDataGetter(getIntent().getExtras());
        PeopleData peopleData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_DATA_PERSISTANCE_KEY);

        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(getSupportFragmentManager(), R.id.people_detail_layout, peopleDetailFragment, getIntent().getExtras()));
    }
}
