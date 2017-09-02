package com.example.android.moviedb3.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.VideoDataListRecyclerViewAdapter;
import com.example.android.moviedb3.dataManager.dataGetter.BundleDataGetter;
import com.example.android.moviedb3.dataManager.movieDBGetter.DBGetter;
import com.example.android.moviedb3.dataManager.dataFinderChecker.DataInIDListCheckerAsyncTask;
import com.example.android.moviedb3.dataManager.movieDBGetter.MovieInfoDataGetter;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonParsing.CastListJSONParser;
import com.example.android.moviedb3.jsonParsing.CrewListJSONParser;
import com.example.android.moviedb3.jsonParsing.ReviewListJSONParser;
import com.example.android.moviedb3.jsonParsing.VideoListJSONParser;
import com.example.android.moviedb3.localDatabase.CastDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.ReviewDataDB;
import com.example.android.moviedb3.localDatabase.VideoDataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.movieDB.dateToString.DateToNormalDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nugroho on 23/08/17.
 */

public class MovieDetailFragment extends Fragment {
    ScrollView movieDetailLayout;
    Toolbar movieDetailToolbar;
    ImageView coverPosterImageView;
    ImageView mainMoviePosterImageView;
    TextView movieTitleTextView;
    TextView genreTextView;
    TextView runtimeTextView;
    TextView ratingTextView;
    TextView releaseDateTextView;
    TextView taglineTextView;
    TextView synopsisTextView;

    Button favoriteButton;
    Button watchListButton;
    Button shareButton;

    RecyclerView reviewListRecyclerView;
    Button moreReviewButton;
    TextView noReviewTextView;
    RecyclerView castListRecyclerView;
    Button moreCastButton;
    RecyclerView crewListRecyclerView;
    Button moreCrewButton;
    RecyclerView videoListRecyclerView;

    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;

    MovieData movieData;
    ArrayList<ReviewData> reviewDataArrayList;
    ArrayList<CastData> castDataArrayList;
    ArrayList<CrewData> crewDataArrayList;
    ArrayList<VideoData> videoDataArrayList;

    private int numberMovieInfoObtained;
    private Boolean favoriteState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_layout, container, false);

        movieDetailLayout = (ScrollView) view.findViewById(R.id.movie_detail_layout);
        movieDetailToolbar = (Toolbar) view.findViewById(R.id.toolbar_movie_detail);
        coverPosterImageView = (ImageView) view.findViewById(R.id.iv_cover_poster);
        mainMoviePosterImageView = (ImageView) view.findViewById(R.id.iv_main_movie_poster);
        movieTitleTextView = (TextView) view.findViewById(R.id.txt_movie_title);
        genreTextView = (TextView) view.findViewById(R.id.txt_genre);
        runtimeTextView = (TextView) view.findViewById(R.id.txt_runtime);
        ratingTextView = (TextView) view.findViewById(R.id.txt_rating);
        releaseDateTextView = (TextView) view.findViewById(R.id.txt_release_date);
        taglineTextView = (TextView) view.findViewById(R.id.txt_tagline);
        synopsisTextView = (TextView) view.findViewById(R.id.txt_sypnosis);

        favoriteButton = (Button) view.findViewById(R.id.btn_favorite);
        watchListButton = (Button) view.findViewById(R.id.btn_watchlist);
        shareButton = (Button) view.findViewById(R.id.btn_share);

        reviewListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_review_list);
        moreReviewButton = (Button) view.findViewById(R.id.btn_more_review);
        noReviewTextView = (TextView) view.findViewById(R.id.txt_no_review);
        castListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_cast_list);
        moreCastButton = (Button) view.findViewById(R.id.btn_more_cast);
        crewListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_crew_list);
        moreCrewButton = (Button) view.findViewById(R.id.btn_more_crew);
        videoListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_video_list);

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

        outState.putParcelable(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY, movieData);
        outState.putParcelableArrayList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_REVIEW_LIST_PERSISTANCE_KEY, reviewDataArrayList);
        outState.putParcelableArrayList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CAST_LIST_PERSISTANCE_KEY, castDataArrayList);
        outState.putParcelableArrayList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CREW_LIST_PERSISTANCE_KEY, crewDataArrayList);
        outState.putParcelableArrayList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_VIDEO_LIST_PERSISTANCE_KEY, videoDataArrayList);
    }

    private void SetActionBar()
    {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(movieDetailToolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void SetMovieDetail(MovieData movieData) {
        Picasso.with(getContext()).
                load(movieData.getBackdropImageFullURL()).
                placeholder(R.drawable.ic_cached_black_48px).
                error(R.drawable.ic_error_outline_black_48px).
                into(coverPosterImageView);

        Picasso.with(getContext()).
                load(movieData.getSmallMoviePosterURL()).
                placeholder(R.drawable.ic_cached_black_48px).
                error(R.drawable.ic_error_outline_black_48px).
                into(mainMoviePosterImageView);

        movieTitleTextView.setText(movieData.getOriginalTitle());
        genreTextView.setText(movieData.getGenre());

        String duration = String.valueOf(movieData.getDuration());
        runtimeTextView.setText(duration);

        DateToStringSetter dateToStringSetter = new DateToNormalDateStringSetter();
        String releaseDate = dateToStringSetter.getDateString(movieData.getReleaseDate());
        releaseDateTextView.setText(releaseDate);

        String rating = String.valueOf(movieData.getVoteRating());
        ratingTextView.setText(rating);

        taglineTextView.setText(movieData.getTagline());
        synopsisTextView.setText(movieData.getPlotSypnosis());
    }


    private void ShowLoadingData() {
        loadingDataProgressBar.setVisibility(View.VISIBLE);
        movieDetailLayout.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowMovieDetail() {
        loadingDataProgressBar.setVisibility(View.GONE);
        movieDetailLayout.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowNoData() {
        loadingDataProgressBar.setVisibility(View.GONE);
        movieDetailLayout.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    private void ShowNoReviews()
    {
        reviewListRecyclerView.setVisibility(View.GONE);
        noReviewTextView.setVisibility(View.VISIBLE);
        moreReviewButton.setVisibility(View.GONE);
    }

    private void SetAdditionalMovieDetailRecyclerView(RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager, RecyclerView recyclerView) {
        recyclerView.clearFocus();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void SetAllRecyclerView(MovieData movieData) {
        String reviewURL = MovieDataURL.GetReviewURL(String.valueOf(movieData.getId()));
        DBGetter.GetData(new MovieInfoDataGetter<ReviewData>(new ReviewDataListObtainedListener(), new ReviewDataDB(getContext()), new ReviewListJSONParser(), reviewURL, movieData.getId(), getContext()));

        String castURLurl = MovieDataURL.GetCastURL(String.valueOf(movieData.getId()));
        DBGetter.GetData(new MovieInfoDataGetter<CastData>(new CastDataListObtainedListener(), new CastDataDB(getContext()), new CastListJSONParser(), castURLurl, movieData.getId(), getContext()));

        String crewURL = MovieDataURL.GetCrewURL(String.valueOf(movieData.getId()));
        DBGetter.GetData(new MovieInfoDataGetter<CrewData>(new CrewDataListObtainedListener(), new CrewDataDB(getContext()), new CrewListJSONParser(), crewURL, movieData.getId(), getContext()));

        String videoURL = MovieDataURL.GetVideoURL(String.valueOf(movieData.getId()));
        DBGetter.GetData(new MovieInfoDataGetter<VideoData>(new VideoDataListObtainedListener(), new VideoDataDB(getContext()), new VideoListJSONParser(), videoURL, movieData.getId(), getContext()));
    }

    private void CheckAndShowMovieDetail() {
        if (numberMovieInfoObtained < 3) {
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

            movieData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY);
            reviewDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_REVIEW_LIST_PERSISTANCE_KEY);
            castDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CAST_LIST_PERSISTANCE_KEY);
            crewDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CREW_LIST_PERSISTANCE_KEY);
            videoDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_VIDEO_LIST_PERSISTANCE_KEY);
            favoriteState = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.FAVORITE_STATE_PERSISTANCE_KEY);

            SetMovieDetail(movieData);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(reviewDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext()), reviewListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(castDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext()), castListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(crewDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext()), crewListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new VideoDataListRecyclerViewAdapter(videoDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false), videoListRecyclerView);

            ShowMovieDetail();
            SetFavoriteLabel(favoriteState);
        }
        else
        {
            BundleDataGetter bundleDataGetter = new BundleDataGetter(getArguments());
            movieData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY);

            if (movieData != null)
            {
                ShowLoadingData();

                SetMovieDetail(movieData);
                SetInitialFavoriteState();
                SetAllRecyclerView(movieData);
            }
        }
    }

    private void SetInitialFavoriteState()
    {
        DataInIDListCheckerAsyncTask dataInIDListCheckerAsyncTask = new DataInIDListCheckerAsyncTask(new InitialFavoriteObtainedListener(), new FavoriteDataDB(getContext()), movieData.getId());
        dataInIDListCheckerAsyncTask.Execute();
    }

    private boolean FavoriteMovieStateChanged(Boolean favoriteState)
    {
        DataDB<String> dataDB = new FavoriteDataDB(MovieDetailFragment.this.getContext());

        if(favoriteState)
        {
            favoriteState = false;
            SetFavoriteLabel(favoriteState);

            dataDB.removeData(movieData.getId());
            Toast.makeText(MovieDetailFragment.this.getContext(), "Remove this movie from favorite list", Toast.LENGTH_SHORT).show();
        }

        else
        {
            favoriteState = true;
            SetFavoriteLabel(favoriteState);

            dataDB.addData(movieData.getId());
            Toast.makeText(MovieDetailFragment.this.getContext(), "Insert this movie to favorite list", Toast.LENGTH_SHORT).show();
        }

        MovieDetailFragment.this.getActivity().setResult(MovieDBKeyEntry.DatabaseHasChanged.FAVORITE_DATA_CHANGED);
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

    private class ReviewDataListObtainedListener implements OnDataObtainedListener<ArrayList<ReviewData>>
    {
        @Override
        public void onDataObtained(ArrayList<ReviewData> reviewDatas)
        {
            if(reviewDatas != null)
            {
                if(!reviewDatas.isEmpty())
                {
                    reviewDataArrayList = reviewDatas;

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(reviewDatas), new LinearLayoutManager(MovieDetailFragment.this.getContext()), reviewListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoReviews();
            CheckAndShowMovieDetail();
        }
    }

    private class CastDataListObtainedListener implements OnDataObtainedListener<ArrayList<CastData>>
    {
        @Override
        public void onDataObtained(ArrayList<CastData> castDatas)
        {
            if(castDatas != null)
            {
                if(!castDatas.isEmpty())
                {
                    castDataArrayList = castDatas;

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(castDatas), new LinearLayoutManager(MovieDetailFragment.this.getContext()), castListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoData();
        }
    }

    private class CrewDataListObtainedListener implements OnDataObtainedListener<ArrayList<CrewData>>
    {
        @Override
        public void onDataObtained(ArrayList<CrewData> crewDatas)
        {
            if(crewDatas != null)
            {
                if(!crewDatas.isEmpty())
                {
                    crewDataArrayList = crewDatas;

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(crewDatas), new LinearLayoutManager(MovieDetailFragment.this.getContext()), crewListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoData();
        }
    }

    private class VideoDataListObtainedListener implements OnDataObtainedListener<ArrayList<VideoData>>
    {
        @Override
        public void onDataObtained(ArrayList<VideoData> videoDatas)
        {
            if(videoDatas != null)
            {
                if(!videoDatas.isEmpty())
                {
                    videoDataArrayList = videoDatas;

                    SetAdditionalMovieDetailRecyclerView(new VideoDataListRecyclerViewAdapter(videoDatas), new LinearLayoutManager(MovieDetailFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false), videoListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoData();
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

            WatchListDialogFragment watchListDialogFragment = new WatchListDialogFragment();
            watchListDialogFragment.setMovieID(movieData.getId());
            watchListDialogFragment.setOnDataObtainedListener(new WatchListHasSelected());

            watchListDialogFragment.show(getActivity().getSupportFragmentManager(), "watchlistbutton");
        }
    }

    private class WatchListHasSelected implements OnDataObtainedListener<Integer>
    {
        @Override
        public void onDataObtained(Integer integer)
        {
            MovieDetailFragment.this.getActivity().setResult(integer);
        }
    }
}

    /*private void SetInitialData(Bundle savedInstanceState)
    {
        if(savedInstanceState != null)
        {
            BundleDataGetter bundleDataGetter = new BundleDataGetter(savedInstanceState);
            internetNetworkState = savedInstanceState.getInt(MovieDBKeyEntry.MovieDataPersistance.INTERNET_NETWORK_STATE_PERSISTANCE_KEY);

            if(internetNetworkState == MovieDBKeyEntry.InternetNetworkState.CONNECTED)
            {
                movieData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY);
                reviewDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_REVIEW_LIST_PERSISTANCE_KEY);
                castDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CAST_LIST_PERSISTANCE_KEY);
                crewDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CREW_LIST_PERSISTANCE_KEY);
                videoDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_VIDEO_LIST_PERSISTANCE_KEY);

                SetMovieDetail(movieData);
                SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(reviewDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext()), reviewListRecyclerView);
                SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(castDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext()), castListRecyclerView);
                SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(crewDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext()), crewListRecyclerView);
                SetAdditionalMovieDetailRecyclerView(new VideoDataListRecyclerViewAdapter(videoDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false), videoListRecyclerView);

                ShowMovieDetail();

                return;
            }
        }

        else
        {
            BundleDataGetter bundleDataGetter = new BundleDataGetter(getArguments());
            movieData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY);

            if(NetworkConnectionChecker.IsConnect(getContext()))
            {
                if(movieData != null)
                {
                    ShowLoadingData();

                    SetMovieDetail(movieData);
                    SetAllRecyclerView(movieData);

                    return;
                }
            }
        }

        ShowNoInternetAccess();
    }

    private void SetAllRecyclerView(MovieData movieData) {
        String reviewURL = MovieDataURL.GetReviewURL(String.valueOf(movieData.getId()));
        DBGetter.GetData(new MovieInfoDataGetter<ReviewData>(new ReviewDataListObtainedListener(), new ReviewDataDB(getContext()), new ReviewListJSONParser(), reviewURL, movieData.getId(), getContext()));

        String castURLurl = MovieDataURL.GetCastURL(String.valueOf(movieData.getId()));
        DBGetter.GetData(new MovieInfoDataGetter<CastData>(new CastDataListObtainedListener(), new CastDataDB(getContext()), new CastListJSONParser(), castURLurl, movieData.getId(), getContext()));

        String crewURL = MovieDataURL.GetCrewURL(String.valueOf(movieData.getId()));
        DBGetter.GetData(new MovieInfoDataGetter<CrewData>(new CrewDataListObtainedListener(), new CrewDataDB(getContext()), new CrewListJSONParser(), crewURL, movieData.getId(), getContext()));

        String videoURL = MovieDataURL.GetVideoURL(String.valueOf(movieData.getId()));
        DBGetter.GetData(new MovieInfoDataGetter<VideoData>(new VideoDataListObtainedListener(), new VideoDataDB(getContext()), new VideoListJSONParser(), videoURL, movieData.getId(), getContext()));
        /*NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<ReviewData>>(new ReviewListJSONParser(), new ReviewDataListObtainedListener()), reviewURL);

        String castURLurl = MovieDataURL.GetCastURL(String.valueOf(movieData.getId()));
        NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<CastData>>(new CastListJSONParser(), new CastDataListObtainedListener()), castURLurl);

        String crewURL = MovieDataURL.GetCrewURL(String.valueOf(movieData.getId()));
        NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<CrewData>>(new CrewListJSONParser(), new CrewDataListObtainedListener()), crewURL);

        String videoURL = MovieDataURL.GetVideoURL(String.valueOf(movieData.getId()));
        NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<VideoData>>(new VideoListJSONParser(), new VideoDataListObtainedListener()), videoURL);*/




