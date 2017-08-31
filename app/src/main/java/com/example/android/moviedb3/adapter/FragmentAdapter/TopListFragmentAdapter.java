package com.example.android.moviedb3.adapter.FragmentAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.moviedb3.fragment.MovieDetailFragment;
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
 * Created by nugroho on 30/08/17.
 */

public class TopListFragmentAdapter extends FragmentPagerAdapter
{
    Context context;
    ArrayList<MovieListFragment> movieListFragments;
    ArrayList<String> pageTitle;

    public TopListFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        movieListFragments = new ArrayList<>();
        pageTitle = new ArrayList<>();

        MovieListFragment popularMovieListFragment = new MovieListFragment();
        popularMovieListFragment.setMovieListDB(new PopularDataDB(context));

        MovieListFragment topRateMovieListFragment = new MovieListFragment();
        topRateMovieListFragment.setMovieListDB(new TopRateDataDB(context));

        movieListFragments.add(popularMovieListFragment);
        movieListFragments.add(topRateMovieListFragment);

        pageTitle.add("Popular");
        pageTitle.add("Top Rate");
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

    private ArrayList<DataDB<String>> getInitialOtherPopularMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new TopRateDataDB(context));
        dataDBArrayList.add(new NowShowingDataDB(context));
        dataDBArrayList.add(new ComingSoonDataDB(context));
        dataDBArrayList.add(new FavoriteDataDB(context));
        dataDBArrayList.add(new WatchlistDataDB(context));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherTopRateMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new PopularDataDB(context));
        dataDBArrayList.add(new NowShowingDataDB(context));
        dataDBArrayList.add(new ComingSoonDataDB(context));
        dataDBArrayList.add(new FavoriteDataDB(context));
        dataDBArrayList.add(new WatchlistDataDB(context));

        return dataDBArrayList;
    }
}
