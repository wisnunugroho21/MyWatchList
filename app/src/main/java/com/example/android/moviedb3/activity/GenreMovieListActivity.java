package com.example.android.moviedb3.activity;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.adapter.FragmentAdapter.GenreMovieFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.HomeFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.TopListFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.YoursFragmentAdapter;
import com.example.android.moviedb3.fragment.GenreListFragment;
import com.example.android.moviedb3.fragment.MovieListFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;

public class GenreMovieListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    GenreData genreData;
    boolean isLinearList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_movie_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar_movie_list);
        viewPager = (ViewPager) findViewById(R.id.vp_movie_list);
        tabLayout = (TabLayout) findViewById(R.id.tabs_movie_list);

        BundleDataGetter bundleDataGetter = new BundleDataGetter(getIntent().getExtras());
        genreData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.GENRE_PERSISTANCE_KEY);

        SetActionBar(genreData.getName());
        SetMovieListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_tv_movie_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.view_mode_item_menu :
                isLinearList = !isLinearList;
                SetMovieListFragment();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void SetActionBar(String fragmentTitle)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(fragmentTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void SetMovieListFragment()
    {
        viewPager.setAdapter(new GenreMovieFragmentAdapter(getSupportFragmentManager(), this, genreData.getId(), isLinearList));
        tabLayout.setupWithViewPager(viewPager);
    }
}
