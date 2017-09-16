package com.example.android.moviedb3.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.DataInfoListFragment;
import com.example.android.moviedb3.fragment.GenreListFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;

public class DataInfoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_info_list);

        if(getIntent().hasExtra(MovieDBKeyEntry.MovieDataPersistance.DATA_INFO_PAGE_TITLE_PERSISTANCE_KEY))
        {
            String pageTitle = getIntent().getStringExtra(MovieDBKeyEntry.MovieDataPersistance.DATA_INFO_PAGE_TITLE_PERSISTANCE_KEY);
            getSupportActionBar().setTitle(pageTitle);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDataInfoListFragment();
    }

    private void setDataInfoListFragment()
    {
        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(getSupportFragmentManager(), R.id.data_info_list_layout, new DataInfoListFragment(), getIntent().getExtras()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home : finish(); return true;
            default : return super.onOptionsItemSelected(item);
        }
    }
}
