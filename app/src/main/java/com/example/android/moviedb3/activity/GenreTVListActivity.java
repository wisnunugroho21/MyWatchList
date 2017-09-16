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
import com.example.android.moviedb3.adapter.FragmentAdapter.GenreTVFragmentAdapter;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.TVGenre;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;

public class GenreTVListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    TVGenre tvGenre;
    boolean isLinearList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_tvlist);

        toolbar = (Toolbar) findViewById(R.id.toolbar_tv_list);
        viewPager = (ViewPager) findViewById(R.id.vp_tv_list);
        tabLayout = (TabLayout) findViewById(R.id.tabs_tv_list);

        BundleDataGetter bundleDataGetter = new BundleDataGetter(getIntent().getExtras());
        tvGenre = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.TV_GENRE_PERSISTANCE_KEY);

        SetActionBar(tvGenre.getName());
        SetTVListFragment();
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
                SetTVListFragment();
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

    private void SetTVListFragment()
    {
        viewPager.setAdapter(new GenreTVFragmentAdapter(getSupportFragmentManager(), this, tvGenre.getId(), isLinearList));
        tabLayout.setupWithViewPager(viewPager);
    }
}
