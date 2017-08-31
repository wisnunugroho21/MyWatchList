package com.example.android.moviedb3.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.adapter.FragmentAdapter.HomeFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.TopListFragmentAdapter;

/**
 * Created by nugroho on 31/08/17.
 */

public class TabsMovieListFragment extends Fragment
{
    int fragmentAdapterIndex;
    String fragmentTitle;

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tabs_activity_movie_layout, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar_movie_list);
        SetActionBar();

        viewPager = (ViewPager) view.findViewById(R.id.vp_movie_list);
        viewPager.setAdapter(setFragmentPagerAdapter());

        tabLayout = (TabLayout) view.findViewById(R.id.tabs_movie_list);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    public void setFragmentPagerAdapterIndex(int index)
    {
        this.fragmentAdapterIndex = index;
    }

    private FragmentPagerAdapter setFragmentPagerAdapter()
    {
        switch (fragmentAdapterIndex)
        {
            case 0 : return new HomeFragmentAdapter(getChildFragmentManager(), getContext());
            case 1 : return new TopListFragmentAdapter(getChildFragmentManager(), getContext());

            default: return null;
        }
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }

    private void SetActionBar()
    {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setTitle(fragmentTitle);
    }
}
