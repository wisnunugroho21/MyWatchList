package com.example.android.moviedb3.adapter.FragmentAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.fragment.MovieListFragment;
import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.GenreMoviePopularDB;
import com.example.android.moviedb3.localDatabase.GenreMovieTopRateDB;
import com.example.android.moviedb3.localDatabase.NowShowingDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class GenreMovieFragmentAdapter  extends FragmentStatePagerAdapter
{
    Context context;
    String idGenre;

    ArrayList<MovieListFragment> movieListFragments;
    ArrayList<String> pageTitle;

    public GenreMovieFragmentAdapter(FragmentManager fm, Context context, String idGenre, boolean isLinearList) {
        super(fm);
        this.context = context;
        this.idGenre = idGenre;

        movieListFragments = new ArrayList<>();
        pageTitle = new ArrayList<>();

        MovieListFragment popularMovieListFragment = new MovieListFragment();
        popularMovieListFragment.setGenre(idGenre, new GenreMoviePopularDB(context), MovieDataURL.GetGenrePopularMovieListURL(idGenre, context));
        popularMovieListFragment.setLinearList(isLinearList);

        MovieListFragment topRateMovieListFragment = new MovieListFragment();
        topRateMovieListFragment.setGenre(idGenre, new GenreMovieTopRateDB(context), MovieDataURL.GetGenreTopRateMovieListURL(idGenre, context));
        topRateMovieListFragment.setLinearList(isLinearList);

        movieListFragments.add(popularMovieListFragment);
        movieListFragments.add(topRateMovieListFragment);

        pageTitle.add(context.getString(R.string.popular_label));
        pageTitle.add(context.getString(R.string.top_rate_label));
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
