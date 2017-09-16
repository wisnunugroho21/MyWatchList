package com.example.android.moviedb3.adapter.FragmentAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.MovieListFragment;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 01/09/17.
 */

public class YoursFragmentAdapter extends FragmentStatePagerAdapter
{
    Context context;
    ArrayList<MovieListFragment> movieListFragments;
    ArrayList<String> pageTitle;

    public YoursFragmentAdapter(FragmentManager fm, Context context, boolean isLinearList) {
        super(fm);
        this.context = context;

        movieListFragments = new ArrayList<>();
        pageTitle = new ArrayList<>();

        MovieListFragment favoriteMovieListFragment = new MovieListFragment();
        favoriteMovieListFragment.setMovieListDB(new FavoriteDataDB(context));
        favoriteMovieListFragment.setLinearList(isLinearList);

        MovieListFragment watchMovieListFragment = new MovieListFragment();
        watchMovieListFragment.setMovieListDB(new WatchlistDataDB(context));
        watchMovieListFragment.setLinearList(isLinearList);

        MovieListFragment planToWatchListFragment = new MovieListFragment();
        planToWatchListFragment.setMovieListDB(new PlanToWatchDataDB(context));
        planToWatchListFragment.setLinearList(isLinearList);

        movieListFragments.add(favoriteMovieListFragment);
        movieListFragments.add(watchMovieListFragment);
        movieListFragments.add(planToWatchListFragment);

        pageTitle.add(context.getString(R.string.favorite_label));
        pageTitle.add(context.getString(R.string.watched_label));
        pageTitle.add(context.getString(R.string.plan_to_watched_label));
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
