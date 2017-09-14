package com.example.android.moviedb3.adapter.FragmentAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.TVListFragment;
import com.example.android.moviedb3.localDatabase.AirTodayDataDB;
import com.example.android.moviedb3.localDatabase.OnTheAirDataDB;
import com.example.android.moviedb3.localDatabase.PopularTVDataDB;
import com.example.android.moviedb3.localDatabase.TopRatedTVDataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TopListTVFragmentAdapter extends FragmentStatePagerAdapter
{
    Context context;
    ArrayList<TVListFragment> tvListFragments;
    ArrayList<String> pageTitle;

    public TopListTVFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        tvListFragments = new ArrayList<>();
        pageTitle = new ArrayList<>();

        TVListFragment popularTVListFragment = new TVListFragment();
        popularTVListFragment.setTVListDB(new PopularTVDataDB(context));

        TVListFragment topRateTVListFragment = new TVListFragment();
        topRateTVListFragment.setTVListDB(new TopRatedTVDataDB(context));

        tvListFragments.add(popularTVListFragment);
        tvListFragments.add(topRateTVListFragment);

        pageTitle.add(context.getString(R.string.popular_label));
        pageTitle.add(context.getString(R.string.top_rate_label));
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
