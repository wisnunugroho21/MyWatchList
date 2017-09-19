package com.example.android.moviedb3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activity.GenreMovieListActivity;
import com.example.android.moviedb3.activity.GenreTVListActivity;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.GenreDataListRecyclerViewAdapter;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.TVGenreDataListRecyclerViewAdapter;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.TVGenre;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.DatabaseGenreGetter;
import com.example.android.moviedb3.movieDataManager.DatabaseTVGenreGetter;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVGenreListFragment extends Fragment
{
    CardView genreTVListCardView;
    RecyclerView genreTVListRecyclerView;
    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;
    TextView allTVGenreLabelTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.movie_collection_list, container, false);

        genreTVListCardView = (CardView) view.findViewById(R.id.cv_all_collection_list);
        genreTVListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_collection_list);
        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);
        allTVGenreLabelTextView = (TextView) view.findViewById(R.id.txt_all_collection_label);

        allTVGenreLabelTextView.setText(getString(R.string.all_genre_tv_label));
        GetGenreList();

        return view;
    }

    private void ShowNoDataLayout()
    {
        genreTVListCardView.setVisibility(View.GONE);
        genreTVListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    private void ShowRecycleView()
    {
        genreTVListCardView.setVisibility(View.VISIBLE);
        genreTVListRecyclerView.setVisibility(View.VISIBLE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowLoadingData()
    {
        genreTVListCardView.setVisibility(View.GONE);
        genreTVListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void GetGenreList()
    {
        ShowLoadingData();
        DBGetter.GetData(new DatabaseTVGenreGetter(getContext(), new GenreListObtained()));
    }

    private void SetGenreRecyclerView(ArrayList<TVGenre> tvGenreArrayList)
    {
        genreTVListRecyclerView.setAdapter(new TVGenreDataListRecyclerViewAdapter(tvGenreArrayList, new GenreChoosedListener()));
        genreTVListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        genreTVListRecyclerView.setHasFixedSize(true);
        genreTVListRecyclerView.setNestedScrollingEnabled(false);
    }

    public class GenreListObtained implements OnDataObtainedListener<ArrayList<TVGenre>>
    {
        @Override
        public void onDataObtained(ArrayList<TVGenre> tvGenres)
        {
            if(tvGenres != null)
            {
                if(!tvGenres.isEmpty())
                {
                    SetGenreRecyclerView(tvGenres);
                    ShowRecycleView();
                    return;
                }
            }

            ShowNoDataLayout();
        }
    }

    private class GenreChoosedListener implements OnDataChooseListener<TVGenre>
    {
        @Override
        public void OnDataChoose(TVGenre tvGenre)
        {
            Intent intent = new Intent(getContext(), GenreTVListActivity.class);
            intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.TV_GENRE_PERSISTANCE_KEY, tvGenre);
            startActivity(intent);
        }
    }
}
