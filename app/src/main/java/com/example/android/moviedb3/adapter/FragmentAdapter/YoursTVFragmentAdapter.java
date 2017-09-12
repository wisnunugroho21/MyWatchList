package com.example.android.moviedb3.adapter.FragmentAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.TVListFragment;
import com.example.android.moviedb3.localDatabase.AirTodayDataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.FavoriteTVDataDB;
import com.example.android.moviedb3.localDatabase.OnTheAirDataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchTVDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistTvDataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class YoursTVFragmentAdapter extends FragmentStatePagerAdapter
{
    Context context;
    ArrayList<TVListFragment> tvListFragments;
    ArrayList<String> pageTitle;

    public YoursTVFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        tvListFragments = new ArrayList<>();
        pageTitle = new ArrayList<>();

        TVListFragment favoriteTVListFragment = new TVListFragment();
        favoriteTVListFragment.setTVListDB(new FavoriteTVDataDB(context));

        TVListFragment watchTVListFragment = new TVListFragment();
        watchTVListFragment.setTVListDB(new WatchlistTvDataDB(context));

        TVListFragment planToWatchTVListFragment = new TVListFragment();
        planToWatchTVListFragment.setTVListDB(new PlanToWatchTVDataDB(context));

        tvListFragments.add(favoriteTVListFragment);
        tvListFragments.add(watchTVListFragment);
        tvListFragments.add(planToWatchTVListFragment);

        pageTitle.add(context.getString(R.string.favorite_label));
        pageTitle.add(context.getString(R.string.watched_label));
        pageTitle.add(context.getString(R.string.plan_to_watched_label));
    }

    @Override
    public Fragment getItem(int position)
    {
        return tvListFragments.get(position);
    }

    @Override
    public int getCount()
    {
        return tvListFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return pageTitle.get(position);
    }
}
