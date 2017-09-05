package com.example.android.moviedb3.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.GenreDataListRecyclerViewAdapter;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonParsing.GenreListJSONParser;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterSyncTask;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreListFragment extends Fragment
{
    RecyclerView genreListRecyclerView;
    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.movie_genre_list, container, false);

        genreListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_genre_list);
        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);

        GetGenreList();

        return view;
    }

    private void ShowNoDataLayout()
    {
        genreListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    private void ShowRecycleView()
    {
        genreListRecyclerView.setVisibility(View.VISIBLE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowLoadingData()
    {
        genreListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void GetGenreList()
    {
        ShowLoadingData();
        NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<GenreData>>(new GenreListJSONParser(), new GenreListObtained()), MovieDataURL.GetGenreListURL());
    }

    private void SetGenreRecyclerView(ArrayList<GenreData> genreDataArrayList)
    {
        genreListRecyclerView.setAdapter(new GenreDataListRecyclerViewAdapter(genreDataArrayList));
        genreListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        genreListRecyclerView.setHasFixedSize(true);
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

         }
     }


}
