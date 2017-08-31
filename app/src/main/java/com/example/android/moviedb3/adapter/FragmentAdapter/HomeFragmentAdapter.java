package com.example.android.moviedb3.adapter.FragmentAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.moviedb3.fragment.MovieListFragment;
import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.NowShowingDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.movieDB.MovieDataURL;

import java.util.ArrayList;

/**
 * Created by nugroho on 31/08/17.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter
{
    Context context;
    ArrayList<MovieListFragment> movieListFragments;
    ArrayList<String> pageTitle;

    public HomeFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        movieListFragments = new ArrayList<>();
        pageTitle = new ArrayList<>();

        MovieListFragment nowShowingMovieListFragment = new MovieListFragment();
        nowShowingMovieListFragment.setMovieListDB(new NowShowingDataDB(context));

        MovieListFragment comingSoonMovieListFragment = new MovieListFragment();
        comingSoonMovieListFragment.setMovieListDB(new ComingSoonDataDB(context));

        movieListFragments.add(nowShowingMovieListFragment);
        movieListFragments.add(comingSoonMovieListFragment);

        pageTitle.add("Now Showing");
        pageTitle.add("Coming Soon");
    }

    @Override
    public Fragment getItem(int position)
    {
        return movieListFragments.get(position);
    }

    @Override
    public int getCount()
    {
        return movieListFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return pageTitle.get(position);
    }
}
