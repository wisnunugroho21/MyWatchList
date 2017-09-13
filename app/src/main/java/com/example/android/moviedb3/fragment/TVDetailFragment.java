package com.example.android.moviedb3.fragment;

import android.os.AsyncTask;
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
import android.widget.Toast;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MovieInfoListRecycleViewAdapter;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.VideoTVDataListRecyclerViewAdapter;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonParsing.CastTVListJSONParser;
import com.example.android.moviedb3.jsonParsing.CrewTVListJSONParser;
import com.example.android.moviedb3.jsonParsing.VideoTVListJSONParser;
import com.example.android.moviedb3.localDatabase.CastTVDataDB;
import com.example.android.moviedb3.localDatabase.CrewTVDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.FavoriteTVDataDB;
import com.example.android.moviedb3.localDatabase.VideoTVDataDB;
import com.example.android.moviedb3.movieDB.CastTVData;
import com.example.android.moviedb3.movieDB.CrewTVData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.movieDB.VideoTVData;
import com.example.android.moviedb3.movieDB.dateToString.DateToNormalDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.TVInfoDataGetter;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DefaultDataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDCompare;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVDetailFragment extends Fragment {
    ScrollView tvDetailLayout;
    Toolbar tvDetailToolbar;
    ImageView coverPosterImageView;
    ImageView mainTVPosterImageView;
    TextView movieTitleTextView;
    TextView genreTextView;
    TextView episodeSeasonTextView;
    TextView ratingTextView;
    TextView firstReleaseDateTextView;
    TextView synopsisTextView;
    TextView networkTextView;
    TextView seriesStatusTextView;

    Button favoriteButton;
    Button watchListButton;
    Button shareButton;

    RecyclerView castListRecyclerView;
    Button moreCastButton;
    TextView noCastTextView;
    RecyclerView crewListRecyclerView;
    TextView noCrewTextView;
    Button moreCrewButton;
    RecyclerView videoListRecyclerView;
    TextView noVideoTextView;

    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;

    TVData tvData;
    ArrayList<CastTVData> castDataArrayList;
    ArrayList<CrewTVData> crewDataArrayList;
    ArrayList<VideoTVData> videoDataArrayList;

    private int numberMovieInfoObtained;
    private Boolean favoriteState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tv_detail_layout, container, false);

        tvDetailLayout = (ScrollView) view.findViewById(R.id.tv_detail_layout);
        tvDetailToolbar = (Toolbar) view.findViewById(R.id.toolbar_tv_detail);
        coverPosterImageView = (ImageView) view.findViewById(R.id.iv_cover_poster);
        mainTVPosterImageView = (ImageView) view.findViewById(R.id.iv_main_tv_poster);
        movieTitleTextView = (TextView) view.findViewById(R.id.txt_tv_title);
        genreTextView = (TextView) view.findViewById(R.id.txt_genre);
        episodeSeasonTextView = (TextView) view.findViewById(R.id.txt_episode_season);
        ratingTextView = (TextView) view.findViewById(R.id.txt_rating);
        firstReleaseDateTextView = (TextView) view.findViewById(R.id.txt_first_release);
        synopsisTextView = (TextView) view.findViewById(R.id.txt_sypnosis);
        networkTextView = (TextView) view.findViewById(R.id.txt_network);
        seriesStatusTextView = (TextView) view.findViewById(R.id.txt_series_status);

        favoriteButton = (Button) view.findViewById(R.id.btn_favorite);
        watchListButton = (Button) view.findViewById(R.id.btn_watchlist);
        shareButton = (Button) view.findViewById(R.id.btn_share);

        castListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_cast_list);
        noCastTextView = (TextView) view.findViewById(R.id.txt_no_cast);
        moreCastButton = (Button) view.findViewById(R.id.btn_more_cast);
        crewListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_crew_list);
        noCrewTextView = (TextView) view.findViewById(R.id.txt_no_crew);
        moreCrewButton = (Button) view.findViewById(R.id.btn_more_crew);
        videoListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_video_list);
        noVideoTextView = (TextView) view.findViewById(R.id.txt_no_video);

        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);

        favoriteButton.setOnClickListener(new FavoriteButtonClickListener());
        watchListButton.setOnClickListener(new WatchListButtonClickListener());

        SetInitialData(savedInstanceState);
        SetActionBar();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(MovieDBKeyEntry.MovieDataPersistance.TV_DATA_PERSISTANCE_KEY, tvData);
        outState.putParcelableArrayList(MovieDBKeyEntry.MovieDataPersistance.TV_CAST_LIST_PERSISTANCE_KEY, castDataArrayList);
        outState.putParcelableArrayList(MovieDBKeyEntry.MovieDataPersistance.TV_CREW_LIST_PERSISTANCE_KEY, crewDataArrayList);
        outState.putParcelableArrayList(MovieDBKeyEntry.MovieDataPersistance.TV_VIDEO_LIST_PERSISTANCE_KEY, videoDataArrayList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home : getActivity().finish(); return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void SetActionBar()
    {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(tvDetailToolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void SetMovieDetail(TVData tvData) {
        Picasso.with(getContext()).
                load(tvData.getBackdropImageFullURL()).
                placeholder(R.drawable.ic_cached_black_48px).
                error(R.drawable.ic_error_outline_black_48px).
                into(coverPosterImageView);

        Picasso.with(getContext()).
                load(tvData.getSmallMoviePosterURL()).
                placeholder(R.drawable.ic_cached_black_48px).
                error(R.drawable.ic_error_outline_black_48px).
                into(mainTVPosterImageView);

        movieTitleTextView.setText(tvData.getOriginalTitle());
        genreTextView.setText(tvData.getGenre());

        String episodeSeason = String.valueOf(tvData.getEpisode()) + "/" + String.valueOf(tvData.getSeason());
        episodeSeasonTextView.setText(episodeSeason);

        String rating = String.valueOf(tvData.getVoteRating());
        ratingTextView.setText(rating);

        DateToStringSetter dateToStringSetter = new DateToNormalDateStringSetter();
        String releaseDate = dateToStringSetter.getDateString(tvData.getFirstReleaseDate());
        firstReleaseDateTextView.setText(releaseDate);

        synopsisTextView.setText(tvData.getPlotSypnosis());
        networkTextView.setText(tvData.getAvailableOnNetwork());
        seriesStatusTextView.setText(tvData.getSeriesStatus());
    }


    private void ShowLoadingData() {
        loadingDataProgressBar.setVisibility(View.VISIBLE);
        tvDetailLayout.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowMovieDetail() {
        loadingDataProgressBar.setVisibility(View.GONE);
        tvDetailLayout.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowNoData() {
        loadingDataProgressBar.setVisibility(View.GONE);
        tvDetailLayout.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    public void ShowNoVideos()
    {
        videoListRecyclerView.setVisibility(View.GONE);
        noVideoTextView.setVisibility(View.VISIBLE);
    }

    public void ShowNoCasts()
    {
        castListRecyclerView.setVisibility(View.GONE);
        noCastTextView.setVisibility(View.VISIBLE);
        moreCastButton.setVisibility(View.GONE);
    }

    public void ShowNoCrews()
    {
        crewListRecyclerView.setVisibility(View.GONE);
        noCrewTextView.setVisibility(View.VISIBLE);
        moreCrewButton.setVisibility(View.GONE);
    }

    private void SetAdditionalMovieDetailRecyclerView(RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager, RecyclerView recyclerView) {
        recyclerView.clearFocus();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void SetAllRecyclerView(TVData tvData) {

        String castURLurl = MovieDataURL.GetCastTVURL(String.valueOf(tvData.getId()));
        DBGetter.GetData(new TVInfoDataGetter<>(new CastDataListObtainedListener(), new CastTVDataDB(getContext()), new CastTVListJSONParser(), castURLurl, tvData.getId(), getContext()));

        String crewURL = MovieDataURL.GetCrewTVURL(String.valueOf(tvData.getId()));
        DBGetter.GetData(new TVInfoDataGetter<>(new CrewDataListObtainedListener(), new CrewTVDataDB(getContext()), new CrewTVListJSONParser(), crewURL, tvData.getId(), getContext()));

        String videoURL = MovieDataURL.GetVideoTVURL(String.valueOf(tvData.getId()), getContext());
        DBGetter.GetData(new TVInfoDataGetter<>(new VideoDataListObtainedListener(), new VideoTVDataDB(getContext()), new VideoTVListJSONParser(), videoURL, tvData.getId(), getContext()));
    }

    private void CheckAndShowMovieDetail() {
        if (numberMovieInfoObtained < 2) {
            numberMovieInfoObtained++;
        } else {
            ShowMovieDetail();
        }
    }

    private void SetInitialData(Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
        {
            BundleDataGetter bundleDataGetter = new BundleDataGetter(savedInstanceState);

            tvData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.TV_DATA_PERSISTANCE_KEY);
            castDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.TV_CAST_LIST_PERSISTANCE_KEY);
            crewDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.TV_CREW_LIST_PERSISTANCE_KEY);
            videoDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.TV_VIDEO_LIST_PERSISTANCE_KEY);
            favoriteState = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.FAVORITE_STATE_PERSISTANCE_KEY);

            SetMovieDetail(tvData);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(castDataArrayList, getContext()), new LinearLayoutManager(this.getContext()), castListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(crewDataArrayList, getContext()), new LinearLayoutManager(this.getContext()), crewListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new VideoTVDataListRecyclerViewAdapter(videoDataArrayList), new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false), videoListRecyclerView);

            ShowMovieDetail();
            SetFavoriteLabel(favoriteState);
        }

        else
        {
            BundleDataGetter bundleDataGetter = new BundleDataGetter(getArguments());
            tvData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.TV_DATA_PERSISTANCE_KEY);

            if (tvData != null)
            {
                ShowLoadingData();

                SetMovieDetail(tvData);
                SetInitialFavoriteState();
                SetAllRecyclerView(tvData);
            }
        }
    }

    private void SetInitialFavoriteState()
    {
        CheckFavoriteMovieList checkFavoriteMovieList = new CheckFavoriteMovieList(new InitialFavoriteObtainedListener());
        checkFavoriteMovieList.execute();
    }

    private boolean FavoriteMovieStateChanged(Boolean favoriteState)
    {
        DataDB<String> dataDB = new FavoriteTVDataDB(this.getContext());

        if(favoriteState)
        {
            favoriteState = false;
            SetFavoriteLabel(favoriteState);

            dataDB.removeData(tvData.getId());
            Toast.makeText(this.getContext(), "Remove this movie from favorite list", Toast.LENGTH_SHORT).show();
        }

        else
        {
            favoriteState = true;
            SetFavoriteLabel(favoriteState);

            dataDB.addData(tvData.getId());
            Toast.makeText(this.getContext(), "Insert this movie to favorite list", Toast.LENGTH_SHORT).show();
        }

        this.getActivity().setResult(MovieDBKeyEntry.DatabaseHasChanged.FAVORITE_DATA_CHANGED);
        return favoriteState;
    }

    private void SetFavoriteLabel(boolean state)
    {
        if (!state)
        {
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_white_24px, 0, 0);
            favoriteButton.setText(R.string.favorite_label);
        }

        else
        {
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_border_white_24px, 0, 0);
            favoriteButton.setText(R.string.un_favorite_label);
        }
    }

    private class CastDataListObtainedListener implements OnDataObtainedListener<ArrayList<CastTVData>>
    {
        @Override
        public void onDataObtained(ArrayList<CastTVData> castDatas)
        {
            if(castDatas != null)
            {
                if(!castDatas.isEmpty())
                {
                    castDataArrayList = castDatas;

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(castDatas, getContext()), new LinearLayoutManager(TVDetailFragment.this.getContext()), castListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoCasts();
            CheckAndShowMovieDetail();
        }
    }

    private class CrewDataListObtainedListener implements OnDataObtainedListener<ArrayList<CrewTVData>>
    {
        @Override
        public void onDataObtained(ArrayList<CrewTVData> crewDatas)
        {
            if(crewDatas != null)
            {
                if(!crewDatas.isEmpty())
                {
                    crewDataArrayList = crewDatas;

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(crewDatas, getContext()), new LinearLayoutManager(TVDetailFragment.this.getContext()), crewListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoCrews();
            CheckAndShowMovieDetail();
        }
    }

    private class VideoDataListObtainedListener implements OnDataObtainedListener<ArrayList<VideoTVData>>
    {
        @Override
        public void onDataObtained(ArrayList<VideoTVData> videoDatas)
        {
            if(videoDatas != null)
            {
                if(!videoDatas.isEmpty())
                {
                    videoDataArrayList = videoDatas;

                    SetAdditionalMovieDetailRecyclerView(new VideoTVDataListRecyclerViewAdapter(videoDatas), new LinearLayoutManager(TVDetailFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false), videoListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoVideos();
            CheckAndShowMovieDetail();
        }
    }

    private class InitialFavoriteObtainedListener implements OnDataObtainedListener<Boolean>
    {
        @Override
        public void onDataObtained(Boolean aBoolean)
        {
            favoriteState = aBoolean;
            SetFavoriteLabel(favoriteState);
        }
    }

    private class FavoriteButtonClickListener implements Button.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            favoriteState = FavoriteMovieStateChanged(favoriteState);
        }
    }

    private class WatchListButtonClickListener implements Button.OnClickListener
    {
        @Override
        public void onClick(View v) {

            WatchTVListDialogFragment watchListDialogFragment = new WatchTVListDialogFragment();
            watchListDialogFragment.setTvID(tvData.getId());
            watchListDialogFragment.setOnDataObtainedListener(new WatchListHasSelected());

            watchListDialogFragment.show(getActivity().getSupportFragmentManager(), "watchlistbutton");
        }
    }

    private class WatchListHasSelected implements OnDataObtainedListener<Integer>
    {
        @Override
        public void onDataObtained(Integer integer)
        {
            TVDetailFragment.this.getActivity().setResult(integer);
        }
    }

    private class CheckFavoriteMovieList extends AsyncTask<Void, Void, Boolean>
    {
        OnDataObtainedListener<Boolean> onDataObtainedListener;

        public CheckFavoriteMovieList(OnDataObtainedListener<Boolean> onDataObtainedListener) {
            this.onDataObtainedListener = onDataObtainedListener;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            DataDB<String> dataDB = new FavoriteDataDB(getContext());

            ArrayList<String> idMovieList = dataDB.getAllData();
            return DataAvailableCheck.isDataAvailable(new DefaultDataAvailableCheck<String>(new IDCompare(), idMovieList, tvData.getId()));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            onDataObtainedListener.onDataObtained(aBoolean);
        }
    }
}
