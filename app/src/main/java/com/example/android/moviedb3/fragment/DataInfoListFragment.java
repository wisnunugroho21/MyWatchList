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
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activity.MovieDetailActivity;
import com.example.android.moviedb3.activity.PeopleDetailActivity;
import com.example.android.moviedb3.activity.TVDetailActivity;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MovieInfoListRecycleViewAdapter;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CastTVData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.CrewTVData;
import com.example.android.moviedb3.movieDB.DependencyData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieInfoData;
import com.example.android.moviedb3.movieDB.PeopleCastData;
import com.example.android.moviedb3.movieDB.PeopleCastTvData;
import com.example.android.moviedb3.movieDB.PeopleCrewData;
import com.example.android.moviedb3.movieDB.PeopleCrewTVData;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;

import java.util.ArrayList;

/**
 * Created by nugroho on 15/09/17.
 */

public class DataInfoListFragment<Data extends MovieInfoData> extends Fragment
{
    CardView dataInfoListCardView;
    RecyclerView dataInfoListRecyclerView;
    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;
    TextView allDataInfoLabelTextView;

    String labelTitle;
    ArrayList<Data> movieInfoDataArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.movie_collection_list, container, false);

        dataInfoListCardView = (CardView) view.findViewById(R.id.cv_all_collection_list);
        dataInfoListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_collection_list);
        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);
        allDataInfoLabelTextView = (TextView) view.findViewById(R.id.txt_all_collection_label);

        allDataInfoLabelTextView.setText(labelTitle);
        GetDataInfo();

        return view;
    }

    public void GetDataInfo()
    {
        BundleDataGetter bundleDataGetter = new BundleDataGetter(getArguments());

        if(getArguments().containsKey(MovieDBKeyEntry.MovieDataPersistance.DATA_INFO_LABEL_TITLE_PERSISTANCE_KEY))
        {
            labelTitle = getArguments().getString(MovieDBKeyEntry.MovieDataPersistance.DATA_INFO_LABEL_TITLE_PERSISTANCE_KEY);
            allDataInfoLabelTextView.setText(labelTitle);
        }

        if(getArguments().containsKey(MovieDBKeyEntry.MovieDataPersistance.DATA_INFO_LIST_PERSISTANCE_KEY))
        {
            ArrayList<Data> dataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.DATA_INFO_LIST_PERSISTANCE_KEY);
            movieInfoDataArrayList = dataArrayList;
            SetAdditionalMovieDetailRecyclerView(dataArrayList);
        }
    }

    private void SetAdditionalMovieDetailRecyclerView(ArrayList<Data> movieInfoDatas)
    {
        dataInfoListRecyclerView.clearFocus();
        MovieInfoListRecycleViewAdapter movieInfoListRecycleViewAdapter = new MovieInfoListRecycleViewAdapter<>(movieInfoDatas, getContext(), false);

        if(movieInfoDatas.get(0) instanceof CastData)
        {
            movieInfoListRecycleViewAdapter.setOnDataChooseListener(new OnMovieCastChoosedListener());
        }

        else if(movieInfoDatas.get(0) instanceof CrewData)
        {
            movieInfoListRecycleViewAdapter.setOnDataChooseListener(new OnMovieCrewChoosedListener());
        }

        else if(movieInfoDatas.get(0) instanceof CastTVData)
        {
            movieInfoListRecycleViewAdapter.setOnDataChooseListener(new OnTVCastChoosedListener());
        }

        else if(movieInfoDatas.get(0) instanceof CrewTVData)
        {
            movieInfoListRecycleViewAdapter.setOnDataChooseListener(new OnTVCrewChoosedListener());
        }

        else if(movieInfoDatas.get(0) instanceof PeopleCastData)
        {
            movieInfoListRecycleViewAdapter.setOnDataChooseListener(new OnPeopleMovieCastChoosedListener());
        }

        else if(movieInfoDatas.get(0) instanceof PeopleCrewData)
        {
            movieInfoListRecycleViewAdapter.setOnDataChooseListener(new OnPeopleMovieCrewChoosedListener());
        }

        else if(movieInfoDatas.get(0) instanceof PeopleCastTvData)
        {
            movieInfoListRecycleViewAdapter.setOnDataChooseListener(new OnPeopleTVCastChoosedListener());
        }

        else if(movieInfoDatas.get(0) instanceof PeopleCrewTVData)
        {
            movieInfoListRecycleViewAdapter.setOnDataChooseListener(new OnPeopleTVCrewChoosedListener());
        }

        dataInfoListRecyclerView.setAdapter(movieInfoListRecycleViewAdapter);
        dataInfoListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataInfoListRecyclerView.setHasFixedSize(true);
    }

    private class OnMovieCastChoosedListener implements OnDataChooseListener<MovieInfoData>
    {
        @Override
        public void OnDataChoose(MovieInfoData movieInfoData)
        {
            if(movieInfoData instanceof CastData)
            {
                CastData castData = (CastData) movieInfoData;

                Intent intent = new Intent(getContext(), PeopleDetailActivity.class);
                intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_ID_PERSISTANCE_KEY, castData.getPeopleID());
                startActivity(intent);
            }
        }
    }

    private class OnMovieCrewChoosedListener implements OnDataChooseListener<MovieInfoData>
    {
        @Override
        public void OnDataChoose(MovieInfoData movieInfoData)
        {
            if(movieInfoData instanceof CrewData)
            {
                CrewData crewData = (CrewData) movieInfoData;

                Intent intent = new Intent(getContext(), PeopleDetailActivity.class);
                intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_ID_PERSISTANCE_KEY, crewData.getPeopleID());
                startActivity(intent);
            }
        }
    }

    private class OnTVCastChoosedListener implements OnDataChooseListener<MovieInfoData>
    {
        @Override
        public void OnDataChoose(MovieInfoData movieInfoData)
        {
            if(movieInfoData instanceof CastTVData)
            {
                CastTVData castTVData = (CastTVData) movieInfoData;

                Intent intent = new Intent(getContext(), PeopleDetailActivity.class);
                intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_ID_PERSISTANCE_KEY, castTVData.getPeopleID());
                startActivity(intent);
            }
        }
    }

    private class OnTVCrewChoosedListener implements OnDataChooseListener<MovieInfoData>
    {
        @Override
        public void OnDataChoose(MovieInfoData movieInfoData)
        {
            if(movieInfoData instanceof CrewTVData)
            {
                CrewTVData crewTVData = (CrewTVData) movieInfoData;

                Intent intent = new Intent(getContext(), PeopleDetailActivity.class);
                intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_ID_PERSISTANCE_KEY, crewTVData.getPeopleID());
                startActivity(intent);
            }
        }
    }

    private class OnPeopleMovieCastChoosedListener implements OnDataChooseListener<MovieInfoData>
    {
        @Override
        public void OnDataChoose(MovieInfoData movieInfoData)
        {
            if(movieInfoData instanceof PeopleCastData)
            {
                PeopleCastData peopleCastData = (PeopleCastData) movieInfoData;

                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.MOVIE_ID_PERSISTANCE_KEY, peopleCastData.getMovieID());
                startActivity(intent);
            }
        }
    }

    private class OnPeopleTVCastChoosedListener implements OnDataChooseListener<MovieInfoData>
    {
        @Override
        public void OnDataChoose(MovieInfoData movieInfoData)
        {
            if(movieInfoData instanceof PeopleCastTvData)
            {
                PeopleCastTvData peopleCastTvData = (PeopleCastTvData) movieInfoData;

                Intent intent = new Intent(getContext(), TVDetailActivity.class);
                intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.TV_ID_PERSISTANCE_KEY, peopleCastTvData.getTvID());
                startActivity(intent);
            }
        }
    }

    private class OnPeopleMovieCrewChoosedListener implements OnDataChooseListener<MovieInfoData>
    {
        @Override
        public void OnDataChoose(MovieInfoData movieInfoData)
        {
            if(movieInfoData instanceof PeopleCrewData)
            {
                PeopleCrewData peopleCrewData = (PeopleCrewData) movieInfoData;

                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.MOVIE_ID_PERSISTANCE_KEY, peopleCrewData.getMovieID());
                startActivity(intent);
            }
        }
    }

    private class OnPeopleTVCrewChoosedListener implements OnDataChooseListener<MovieInfoData>
    {
        @Override
        public void OnDataChoose(MovieInfoData movieInfoData)
        {
            if(movieInfoData instanceof PeopleCrewTVData)
            {
                PeopleCrewTVData peopleCrewTVData = (PeopleCrewTVData) movieInfoData;

                Intent intent = new Intent(getContext(), TVDetailActivity.class);
                intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.TV_ID_PERSISTANCE_KEY, peopleCrewTVData.getTvID());
                startActivity(intent);
            }
        }
    }
}
