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
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.GenreDataListRecyclerViewAdapter;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.DatabaseGenreGetter;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreListFragment extends Fragment
{
    CardView genreListCardView;
    RecyclerView genreListRecyclerView;
    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;
    TextView allGenreLabelTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.movie_collection_list, container, false);

        genreListCardView = (CardView) view.findViewById(R.id.cv_all_collection_list);
        genreListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_collection_list);
        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);
        allGenreLabelTextView = (TextView) view.findViewById(R.id.txt_all_collection_label);

        allGenreLabelTextView.setText(getString(R.string.all_genre_movie_label));
        GetGenreList();

        return view;
    }

    private void ShowNoDataLayout()
    {
        genreListCardView.setVisibility(View.GONE);
        genreListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    private void ShowRecycleView()
    {
        genreListCardView.setVisibility(View.VISIBLE);
        genreListRecyclerView.setVisibility(View.VISIBLE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowLoadingData()
    {
        genreListCardView.setVisibility(View.GONE);
        genreListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void GetGenreList()
    {
        ShowLoadingData();

        try
        {
            DBGetter.GetData(new DatabaseGenreGetter(getContext(), new GenreListObtained()));
        }
        catch (Exception e)
        {
            ShowNoDataLayout();
        }
    }

    private void SetGenreRecyclerView(ArrayList<GenreData> genreDataArrayList)
    {
        genreListRecyclerView.setAdapter(new GenreDataListRecyclerViewAdapter(genreDataArrayList, new GenreChoosedListener()));
        genreListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        genreListRecyclerView.setHasFixedSize(true);
        genreListRecyclerView.setNestedScrollingEnabled(true);
    }

    public class GenreListObtained implements OnDataObtainedListener<ArrayList<GenreData>>
    {
        @Override
        public void onDataObtained(ArrayList<GenreData> genreDatas)
        {
            if(genreDatas != null)
            {
                if(!genreDatas.isEmpty())
                {
                    SetGenreRecyclerView(genreDatas);
                    ShowRecycleView();
                    return;
                }
            }

            ShowNoDataLayout();
        }
    }

     private class GenreChoosedListener implements OnDataChooseListener<GenreData>
     {
         @Override
         public void OnDataChoose(GenreData genreData)
         {
             Intent intent = new Intent(getContext(), GenreMovieListActivity.class);
             intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.GENRE_PERSISTANCE_KEY, genreData);
             startActivity(intent);
         }
     }
}
