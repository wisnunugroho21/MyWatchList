package com.example.android.moviedb3.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.adapter.FragmentAdapter.HomeFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.TopListFragmentAdapter;
import com.example.android.moviedb3.adapter.FragmentAdapter.YoursFragmentAdapter;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;

/**
 * Created by nugroho on 31/08/17.
 */

public class TabsMovieListFragment extends Fragment
{
    int selectedPageIndex = 0;
    int fragmentAdapterIndex = 0;
    String fragmentTitle;

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tabs_activity_layout, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar_movie_list);
        SetActionBar();

        viewPager = (ViewPager) view.findViewById(R.id.vp_movie_list);
        viewPager.setAdapter(setFragmentPagerAdapter());

        tabLayout = (TabLayout) view.findViewById(R.id.tabs_movie_list);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setScrollPosition(selectedPageIndex, 0f, true);
        viewPager.setCurrentItem(selectedPageIndex);

        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) view.findViewById(R.id.nav_view);

        SetNavigationDrawer();

        return view;
    }

    public void setFragmentPagerAdapterIndex(int fragmentAdapterIndex)
    {
        this.fragmentAdapterIndex = fragmentAdapterIndex;
    }

    public void setSelectedPageIndex(int selectedPageIndex)
    {
        this.selectedPageIndex = selectedPageIndex;
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }

    private FragmentPagerAdapter setFragmentPagerAdapter()
    {
        switch (fragmentAdapterIndex)
        {
            case MovieDBKeyEntry.MovieListPageAdapterIndex.HOME_PAGE_ADAPTER_INDEX : return new HomeFragmentAdapter(getChildFragmentManager(), getContext());
            case MovieDBKeyEntry.MovieListPageAdapterIndex.TOP_LIST_PAGE_ADAPTER_INDEX: return new TopListFragmentAdapter(getChildFragmentManager(), getContext());
            case MovieDBKeyEntry.MovieListPageAdapterIndex.YOURS_PAGE_ADAPTER_INDEX: return new YoursFragmentAdapter(getChildFragmentManager(), getContext());

            default: return null;
        }
    }

    private void SetActionBar()
    {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setTitle(fragmentTitle);
    }

    private void SetNavigationDrawer()
    {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new TabsMovieActionBarDrawer());
    }

    private class TabsMovieActionBarDrawer implements NavigationView.OnNavigationItemSelectedListener
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }
}
