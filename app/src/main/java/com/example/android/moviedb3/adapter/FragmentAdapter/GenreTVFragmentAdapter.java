package com.example.android.moviedb3.adapter.FragmentAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.MovieListFragment;
import com.example.android.moviedb3.fragment.TVListFragment;
import com.example.android.moviedb3.localDatabase.GenreMoviePopularDB;
import com.example.android.moviedb3.localDatabase.GenreMovieTopRateDB;
import com.example.android.moviedb3.localDatabase.GenreTVPopularDB;
import com.example.android.moviedb3.localDatabase.GenreTVTopRateDataDB;
import com.example.android.moviedb3.movieDB.MovieDataURL;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class GenreTVFragmentAdapter extends FragmentStatePagerAdapter
{
    Context context;
    String idGenre;

    ArrayList<TVListFragment> tvListFragments;
    ArrayList<String> pageTitle;

    public GenreTVFragmentAdapter(FragmentManager fm, Context context, String idGenre, boolean isLinearList) {
        super(fm);
        this.context = context;
        this.idGenre = idGenre;

        tvListFragments = new ArrayList<>();
        pageTitle = new ArrayList<>();

        TVListFragment popularMovieListFragment = new TVListFragment();
        popularMovieListFragment.setGenre(idGenre, new GenreTVPopularDB(context), MovieDataURL.GetGenrePopularTVListURL(idGenre, context));
        popularMovieListFragment.setLinearList(isLinearList);

        TVListFragment topRateMovieListFragment = new TVListFragment();
        topRateMovieListFragment.setGenre(idGenre, new GenreTVTopRateDataDB(context), MovieDataURL.GetGenreTopRateTVListURL(idGenre, context));
        topRateMovieListFragment.setLinearList(isLinearList);

        tvListFragments.add(popularMovieListFragment);
        tvListFragments.add(topRateMovieListFragment);

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
