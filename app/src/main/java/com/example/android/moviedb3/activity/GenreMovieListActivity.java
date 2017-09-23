package com.example.android.moviedb3.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.adapter.FragmentAdapter.GenreMovieFragmentAdapter;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;
import com.google.firebase.analytics.FirebaseAnalytics;

public class GenreMovieListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    GenreData genreData;
    boolean isLinearList = false;

    private FirebaseAnalytics firebaseAnalytics;

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

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        SendGenreMovieAnalytic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.genre_tv_movie_menu, menu);

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

    private void SendGenreMovieAnalytic()
    {
        Bundle bundle = new Bundle();

        bundle.putString("id", genreData.getId());
        bundle.putString("name", genreData.getName());
        firebaseAnalytics.logEvent("genreMovie", bundle);
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
