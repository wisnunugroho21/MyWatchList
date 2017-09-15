package com.example.android.moviedb3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activity.MovieDetailActivity;
import com.example.android.moviedb3.activity.TVDetailActivity;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MovieInfoListRecycleViewAdapter;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonParsing.PeopleCastListJSONParser;
import com.example.android.moviedb3.jsonParsing.PeopleCastTvListJSONParser;
import com.example.android.moviedb3.jsonParsing.PeopleCrewListJSONParser;
import com.example.android.moviedb3.jsonParsing.PeopleCrewTVListJSONParser;
import com.example.android.moviedb3.localDatabase.PeopleCastDataDB;
import com.example.android.moviedb3.localDatabase.PeopleCastTVDataDB;
import com.example.android.moviedb3.localDatabase.PeopleCrewDataDB;
import com.example.android.moviedb3.localDatabase.PeopleCrewTVDataDB;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.MovieInfoData;
import com.example.android.moviedb3.movieDB.PeopleCastData;
import com.example.android.moviedb3.movieDB.PeopleCastTvData;
import com.example.android.moviedb3.movieDB.PeopleCrewData;
import com.example.android.moviedb3.movieDB.PeopleCrewTVData;
import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.movieDB.dateToString.DateToNormalDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.MovieDetailGetterAsyncTask;
import com.example.android.moviedb3.movieDataManager.MovieInfoDataGetter;
import com.example.android.moviedb3.movieDataManager.PeopleDetailGetterAsyncTask;
import com.example.android.moviedb3.movieDataManager.PeopleInfoDataGetter;
import com.example.android.moviedb3.movieDataManager.TVDetailGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleDetailFragment extends Fragment
{
    ScrollView peopleDetailLayout;
    Toolbar peopleDetailToolbar;
    ImageView coverPosterImageView;
    ImageView mainPeoplePosterImageView;
    TextView peopleNameTextView;
    TextView placeOfBirthTextView;
    TextView rolesTextView;
    TextView filmographyTextView;
    TextView birthdayDateTextView;
    TextView alsoKnownAsTextView;
    TextView biographyTextView;

    RecyclerView movieCastListRecyclerView;
    Button movieCastButton;
    TextView nomovieCastTextView;
    RecyclerView tvCastListRecyclerView;
    Button moretvCastButton;
    TextView notvCastTextView;
    RecyclerView movieCrewListRecyclerView;
    TextView noMovieCrewTextView;
    Button moreMovieCrewButton;
    RecyclerView tvCrewListRecyclerView;
    Button moreTVCrewButton;
    TextView noTVCrewTextView;

    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;

    PeopleData peopleData;
    ArrayList<PeopleCastData> peopleCastDataArrayList;
    ArrayList<PeopleCrewData> peopleCrewDataArrayList;
    ArrayList<PeopleCastTvData> peopleCastTvDataArrayList;
    ArrayList<PeopleCrewTVData> peopleCrewTVDataArrayList;

    private int numberPeopleInfoObtained;
    private int rolesNumber = 0;
    private int filmographyNumbers = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.people_detail_layout, container, false);

        peopleDetailLayout = (ScrollView) view.findViewById(R.id.people_detail_layout);
        peopleDetailToolbar = (Toolbar) view.findViewById(R.id.toolbar_people_detail);
        coverPosterImageView = (ImageView) view.findViewById(R.id.iv_cover_people);
        mainPeoplePosterImageView = (ImageView) view.findViewById(R.id.iv_main_people_poster);
        peopleNameTextView = (TextView) view.findViewById(R.id.txt_people_name);
        placeOfBirthTextView = (TextView) view.findViewById(R.id.txt_birthplace);
        rolesTextView = (TextView) view.findViewById(R.id.txt_roles);
        filmographyTextView = (TextView) view.findViewById(R.id.txt_filmography);
        birthdayDateTextView = (TextView) view.findViewById(R.id.txt_birthday);
        alsoKnownAsTextView = (TextView) view.findViewById(R.id.txt_also_known_as);
        biographyTextView = (TextView) view.findViewById(R.id.txt_biography);

        movieCastListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie_cast_list);
        movieCastButton = (Button) view.findViewById(R.id.btn_more_movie_casts);
        nomovieCastTextView = (TextView) view.findViewById(R.id.txt_no_movie_casts);
        tvCastListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tv_show_cast_list);
        notvCastTextView = (TextView) view.findViewById(R.id.txt_no_tv_cast);
        moretvCastButton = (Button) view.findViewById(R.id.btn_more_tv_show_casts);
        movieCrewListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie_filmography_list);
        noMovieCrewTextView = (TextView) view.findViewById(R.id.txt_no_movie_filmography);
        moreMovieCrewButton = (Button) view.findViewById(R.id.btn_more_movie_filmography);
        tvCrewListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tv_filmography_list);
        noTVCrewTextView = (TextView) view.findViewById(R.id.txt_no_tv_filmography);
        moreTVCrewButton = (Button) view.findViewById(R.id.btn_more_tv_filmography);

        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);

        SetInitialData(savedInstanceState);
        SetActionBar();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putParcelable(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY, peopleData);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home : getActivity().finish(); return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void SetActionBar()
    {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(peopleDetailToolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void SetMovieDetail(PeopleData peopleData)
    {
        String backdropImage = peopleData.getBackdropImageFullURL();

        Picasso.with(getContext()).
                load(backdropImage).
                placeholder(R.drawable.ic_cached_black_48px).
                error(R.drawable.ic_error_outline_black_48px).
                into(coverPosterImageView);

        Picasso.with(getContext()).
                load(peopleData.getFullProfileImage()).
                placeholder(R.drawable.ic_cached_black_48px).
                error(R.drawable.ic_error_outline_black_48px).
                into(mainPeoplePosterImageView);

        peopleNameTextView.setText(peopleData.getName());
        placeOfBirthTextView.setText(peopleData.getPlaceOfBirth());

        DateToStringSetter dateToStringSetter = new DateToNormalDateStringSetter();
        String releaseDate = dateToStringSetter.getDateString(peopleData.getBirthtdayDate());
        birthdayDateTextView.setText(releaseDate);

        alsoKnownAsTextView.setText(peopleData.getOthersName());
        biographyTextView.setText(peopleData.getBiography());
    }


    private void ShowLoadingData() {
        loadingDataProgressBar.setVisibility(View.VISIBLE);
        peopleDetailLayout.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowMovieDetail() {
        loadingDataProgressBar.setVisibility(View.GONE);
        peopleDetailLayout.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowNoData() {
        loadingDataProgressBar.setVisibility(View.GONE);
        peopleDetailLayout.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    private void ShowNoCasts()
    {
        movieCastListRecyclerView.setVisibility(View.GONE);
        nomovieCastTextView.setVisibility(View.VISIBLE);
        movieCastButton.setVisibility(View.GONE);
    }

    public void ShowNoCrews()
    {
        movieCrewListRecyclerView.setVisibility(View.GONE);
        noMovieCrewTextView.setVisibility(View.VISIBLE);
        moreMovieCrewButton.setVisibility(View.GONE);
    }

    public void ShowNoTVCast()
    {
        tvCastListRecyclerView.setVisibility(View.GONE);
        notvCastTextView.setVisibility(View.VISIBLE);
        moretvCastButton.setVisibility(View.GONE);
    }

    public void ShowNoTVCrew()
    {
        tvCrewListRecyclerView.setVisibility(View.GONE);
        noTVCrewTextView.setVisibility(View.VISIBLE);
        moreTVCrewButton.setVisibility(View.GONE);
    }

    private void SetAdditionalMovieDetailRecyclerView(RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager, RecyclerView recyclerView) {
        recyclerView.clearFocus();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void SetAllRecyclerView(PeopleData peopleData)
    {
        String peopleMovieCastURL = MovieDataURL.GetPeopleMovieCastURL(String.valueOf(peopleData.getId()), getContext());
        DBGetter.GetData(new PeopleInfoDataGetter<PeopleCastData>(new PeopleMovieCastDataListObtainedListener(), new PeopleCastDataDB(getContext()), new PeopleCastListJSONParser(), peopleMovieCastURL, peopleData.getId(), getContext()));

        String peopleMovieCrewURL = MovieDataURL.GetPeopleMovieCrewURL(String.valueOf(peopleData.getId()), getContext());
        DBGetter.GetData(new MovieInfoDataGetter<PeopleCrewData>(new PeopleMovieCrewDataListObtainedListener(), new PeopleCrewDataDB(getContext()), new PeopleCrewListJSONParser(), peopleMovieCrewURL, peopleData.getId(), getContext()));

        String peopleTVCastURL = MovieDataURL.GetPeopleTVCastURL(String.valueOf(peopleData.getId()), getContext());
        DBGetter.GetData(new MovieInfoDataGetter<PeopleCastTvData>(new PeopleTVCastDataListObtainedListener(), new PeopleCastTVDataDB(getContext()), new PeopleCastTvListJSONParser(), peopleTVCastURL, peopleData.getId(), getContext()));

        String peopleTVCrewURL = MovieDataURL.GetPeopleTVCrewURL(String.valueOf(peopleData.getId()), getContext());
        DBGetter.GetData(new MovieInfoDataGetter<PeopleCrewTVData>(new PeopleTVCrewDataListObtainedListener(), new PeopleCrewTVDataDB(getContext()), new PeopleCrewTVListJSONParser(), peopleTVCrewURL, peopleData.getId(), getContext()));
    }

    private void CheckAndShowMovieDetail()
    {
        if (numberPeopleInfoObtained < 3) {
            numberPeopleInfoObtained++;
        } else {
            ShowMovieDetail();
        }
    }

    private void SetInitialData(Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
        {
            BundleDataGetter bundleDataGetter = new BundleDataGetter(savedInstanceState);

            peopleData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_DATA_PERSISTANCE_KEY);
            peopleCastDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CAST_LIST_PERSISTANCE_KEY);
            peopleCrewDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CREW_LIST_PERSISTANCE_KEY);
            peopleCastTvDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.TV_CAST_LIST_PERSISTANCE_KEY);
            peopleCrewTVDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.TV_CREW_LIST_PERSISTANCE_KEY);

            SetMovieDetail(peopleData);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(peopleCastDataArrayList, getContext(), new OnMovieCastChoosedListener()), new LinearLayoutManager(this.getContext()), movieCastListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(peopleCrewDataArrayList, getContext(), new OnMovieCrewChoosedListener()), new LinearLayoutManager(this.getContext()), movieCrewListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(peopleCastTvDataArrayList, getContext(), new OnTVCastChoosedListener()), new LinearLayoutManager(this.getContext()), tvCastListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(peopleCrewTVDataArrayList, getContext(), new OnTVCrewChoosedListener()), new LinearLayoutManager(this.getContext()), tvCrewListRecyclerView);

            ShowMovieDetail();
        }
        else
        {
            if(getArguments().containsKey(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_DATA_PERSISTANCE_KEY))
            {
                BundleDataGetter bundleDataGetter = new BundleDataGetter(getArguments());
                peopleData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_DATA_PERSISTANCE_KEY);

                if (peopleData != null)
                {
                    ShowLoadingData();

                    SetMovieDetail(peopleData);
                    SetAllRecyclerView(peopleData);
                }
            }

            else if(getArguments().containsKey(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_ID_PERSISTANCE_KEY))
            {
                ShowLoadingData();
                String peopleID = getArguments().getString(MovieDBKeyEntry.MovieDataPersistance.PEOPLE_ID_PERSISTANCE_KEY);

                PeopleDetailGetterAsyncTask peopleDetailGetterAsyncTask = new PeopleDetailGetterAsyncTask(new PeopleDataObtainedListener(), peopleID, getContext());
                peopleDetailGetterAsyncTask.getData();
            }
        }
    }

    private class PeopleDataObtainedListener implements OnDataObtainedListener<PeopleData>
    {
        @Override
        public void onDataObtained(PeopleData peopleData)
        {
            if (peopleData != null)
            {
                PeopleDetailFragment.this.peopleData = peopleData;
                ShowLoadingData();

                SetMovieDetail(peopleData);
                SetAllRecyclerView(peopleData);
            }
        }
    }

    private class PeopleMovieCastDataListObtainedListener implements OnDataObtainedListener<ArrayList<PeopleCastData>>
    {
        @Override
        public void onDataObtained(ArrayList<PeopleCastData> peopleCastDatas)
        {
            if(peopleCastDatas != null)
            {
                if(!peopleCastDatas.isEmpty())
                {
                    peopleCastDataArrayList = peopleCastDatas;
                    rolesNumber += peopleCastDataArrayList.size();
                    rolesTextView.setText(String.valueOf(rolesNumber));

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(peopleCastDatas, getContext(), new OnMovieCastChoosedListener()), new LinearLayoutManager(PeopleDetailFragment.this.getContext()), movieCastListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoCasts();
            CheckAndShowMovieDetail();
        }
    }

    private class PeopleMovieCrewDataListObtainedListener implements OnDataObtainedListener<ArrayList<PeopleCrewData>>
    {
        @Override
        public void onDataObtained(ArrayList<PeopleCrewData> peopleCrewDatas)
        {
            if(peopleCrewDatas != null)
            {
                if(!peopleCrewDatas.isEmpty())
                {
                    peopleCrewDataArrayList = peopleCrewDatas;
                    filmographyNumbers += peopleCrewDataArrayList.size();
                    filmographyTextView.setText(String.valueOf(filmographyNumbers));

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(peopleCrewDatas, getContext(), new OnMovieCrewChoosedListener()), new LinearLayoutManager(PeopleDetailFragment.this.getContext()), movieCrewListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoCrews();
            CheckAndShowMovieDetail();
        }
    }

    private class PeopleTVCastDataListObtainedListener implements OnDataObtainedListener<ArrayList<PeopleCastTvData>>
    {
        @Override
        public void onDataObtained(ArrayList<PeopleCastTvData> peopleCastTvDatas)
        {
            if(peopleCastTvDatas != null)
            {
                if(!peopleCastTvDatas.isEmpty())
                {
                    peopleCastTvDataArrayList = peopleCastTvDatas;
                    rolesNumber += peopleCastTvDatas.size();
                    rolesTextView.setText(String.valueOf(rolesNumber));

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(peopleCastTvDatas, getContext(), new OnTVCastChoosedListener()), new LinearLayoutManager(PeopleDetailFragment.this.getContext()), tvCastListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoTVCast();
            CheckAndShowMovieDetail();
        }
    }

    private class PeopleTVCrewDataListObtainedListener implements OnDataObtainedListener<ArrayList<PeopleCrewTVData>>
    {
        @Override
        public void onDataObtained(ArrayList<PeopleCrewTVData> peopleCrewTVDatas)
        {
            if(peopleCrewTVDatas != null)
            {
                if(!peopleCrewTVDatas.isEmpty())
                {
                    peopleCrewTVDataArrayList = peopleCrewTVDatas;
                    filmographyNumbers += peopleCrewTVDatas.size();
                    filmographyTextView.setText(String.valueOf(filmographyNumbers));

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(peopleCrewTVDatas, getContext(), new OnTVCrewChoosedListener()), new LinearLayoutManager(PeopleDetailFragment.this.getContext()), tvCrewListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoTVCrew();
            CheckAndShowMovieDetail();
        }
    }

    private class OnMovieCastChoosedListener implements OnDataChooseListener<MovieInfoData>
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

    private class OnTVCastChoosedListener implements OnDataChooseListener<MovieInfoData>
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

    private class OnMovieCrewChoosedListener implements OnDataChooseListener<MovieInfoData>
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

    private class OnTVCrewChoosedListener implements OnDataChooseListener<MovieInfoData>
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
