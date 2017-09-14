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
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.PeoplePopularListRecyclerViewAdapter;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.DatabasePeopleGetter;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class PopularPeopleListFragment extends Fragment
{
    CardView peopleListCardView;
    RecyclerView genreListRecyclerView;
    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;
    TextView allGenreLabelTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.movie_collection_list, container, false);

        peopleListCardView = (CardView) view.findViewById(R.id.cv_all_collection_list);
        genreListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_collection_list);
        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);
        allGenreLabelTextView = (TextView) view.findViewById(R.id.txt_all_collection_label);

        allGenreLabelTextView.setText(getString(R.string.popular_people_label));
        GetGenreList();

        return view;
    }

    private void ShowNoDataLayout()
    {
        peopleListCardView.setVisibility(View.GONE);
        genreListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    private void ShowRecycleView()
    {
        peopleListCardView.setVisibility(View.VISIBLE);
        genreListRecyclerView.setVisibility(View.VISIBLE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowLoadingData()
    {
        peopleListCardView.setVisibility(View.GONE);
        genreListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void GetGenreList()
    {
        ShowLoadingData();
        DBGetter.GetData(new DatabasePeopleGetter(getContext(), new PeopleListObtained()));
    }

    private void SetGenreRecyclerView(ArrayList<PeopleData> peopleDataArrayList)
    {
        genreListRecyclerView.setAdapter(new PeoplePopularListRecyclerViewAdapter(peopleDataArrayList, getContext(), new PeopleChoosedListener()));
        genreListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        genreListRecyclerView.setHasFixedSize(true);
    }

    public class PeopleListObtained implements OnDataObtainedListener<ArrayList<PeopleData>>
    {
        @Override
        public void onDataObtained(ArrayList<PeopleData> peopleDatas)
        {
            if(peopleDatas != null)
            {
                if(!peopleDatas.isEmpty())
                {
                    SetGenreRecyclerView(peopleDatas);
                    ShowRecycleView();
                    return;
                }
            }

            ShowNoDataLayout();
        }
    }

    private class PeopleChoosedListener implements OnDataChooseListener<PeopleData>
    {
        @Override
        public void OnDataChoose(PeopleData peopleData)
        {
            /*Intent intent = new Intent(getContext(), GenreMovieListActivity.class);
            intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.GENRE_PERSISTANCE_KEY, genreData);
            startActivity(intent);*/
        }
    }
}
