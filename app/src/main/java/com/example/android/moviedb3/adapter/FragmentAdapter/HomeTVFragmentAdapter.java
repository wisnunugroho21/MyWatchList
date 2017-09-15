package com.example.android.moviedb3.adapter.FragmentAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.TVListFragment;
import com.example.android.moviedb3.localDatabase.AirTodayDataDB;
import com.example.android.moviedb3.localDatabase.OnTheAirDataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class HomeTVFragmentAdapter  extends FragmentStatePagerAdapter
{
    Context context;
    ArrayList<TVListFragment> tvListFragments;
    ArrayList<String> pageTitle;

    public HomeTVFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        tvListFragments = new ArrayList<>();
        pageTitle = new ArrayList<>();

        TVListFragment airingTodayTVListFragment = new TVListFragment();
        airingTodayTVListFragment.setTVListDB(new AirTodayDataDB(context));

        TVListFragment onTheAirTVListFragment = new TVListFragment();
        onTheAirTVListFragment.setTVListDB(new OnTheAirDataDB(context));

        tvListFragments.add(airingTodayTVListFragment);
        tvListFragments.add(onTheAirTVListFragment);

        pageTitle.add(context.getString(R.string.airing_today_label));
        pageTitle.add(context.getString(R.string.on_the_air_label));
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
