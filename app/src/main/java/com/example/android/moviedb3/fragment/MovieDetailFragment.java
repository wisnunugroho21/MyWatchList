package com.example.android.moviedb3.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activity.DataInfoListActivity;
import com.example.android.moviedb3.activity.PeopleDetailActivity;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MovieInfoListRecycleViewAdapter;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.VideoDataListRecyclerViewAdapter;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.NowShowingDataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.MovieInfoData;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.DatabaseMovieInsertandRemove;
import com.example.android.moviedb3.movieDataManager.MovieDetailGetterAsyncTask;
import com.example.android.moviedb3.movieDataManager.MovieInfoDataGetter;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DefaultDataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDCompare;
import com.example.android.moviedb3.supportDataManager.dataGetter.BundleDataGetter;
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
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.movieDB.dateToString.DateToNormalDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterAsyncTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nugroho on 23/08/17.
 */

public class MovieDetailFragment extends Fragment {
    CoordinatorLayout movieDetailLayout;
    Toolbar appBarMovieDetailToolbar;
    AppBarLayout movieDetailAppBar;

    ImageView coverPosterImageView;
    ImageView mainMoviePosterImageView;
    TextView movieTitleTextView;
    TextView genreTextView;
    TextView runtimeTextView;
    TextView ratingTextView;
    TextView releaseDateTextView;
    TextView taglineTextView;
    TextView synopsisTextView;

    ConstraintLayout favoriteButton;
    TextView favoriteTitleTextView;
    ImageView favoriteImageView;
    ConstraintLayout watchListButton;
    ConstraintLayout shareButton;

    RecyclerView reviewListRecyclerView;
    Button moreReviewButton;
    TextView noReviewTextView;
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

    MovieData movieData;
    ArrayList<ReviewData> reviewDataArrayList;
    ArrayList<CastData> castDataArrayList;
    ArrayList<CrewData> crewDataArrayList;
    ArrayList<VideoData> videoDataArrayList;

    private int numberMovieInfoObtained;
    private Boolean favoriteState;
    private Boolean isToolbarTransparent = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_layout, container, false);

        movieDetailLayout = (CoordinatorLayout) view.findViewById(R.id.movie_detail_layout);
        appBarMovieDetailToolbar = (Toolbar) view.findViewById(R.id.toolbar_movie_detail_appbar);
        movieDetailAppBar = (AppBarLayout) view.findViewById(R.id.appbar_movie_detail);

        coverPosterImageView = (ImageView) view.findViewById(R.id.iv_cover_poster);
        mainMoviePosterImageView = (ImageView) view.findViewById(R.id.iv_main_movie_poster);
        movieTitleTextView = (TextView) view.findViewById(R.id.txt_movie_title);
        genreTextView = (TextView) view.findViewById(R.id.txt_genre);
        runtimeTextView = (TextView) view.findViewById(R.id.txt_runtime);
        ratingTextView = (TextView) view.findViewById(R.id.txt_rating);
        releaseDateTextView = (TextView) view.findViewById(R.id.txt_release_date);
        taglineTextView = (TextView) view.findViewById(R.id.txt_tagline);
        synopsisTextView = (TextView) view.findViewById(R.id.txt_sypnosis);

        favoriteButton = (ConstraintLayout) view.findViewById(R.id.btn_favorite);
        favoriteImageView = (ImageView) view.findViewById(R.id.iv_star_favorite);
        favoriteTitleTextView = (TextView) view.findViewById(R.id.txt_title_favorite);
        watchListButton = (ConstraintLayout) view.findViewById(R.id.btn_watchlist);
        shareButton = (ConstraintLayout) view.findViewById(R.id.btn_share);

        reviewListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_review_list);
        moreReviewButton = (Button) view.findViewById(R.id.btn_more_review);
        noReviewTextView = (TextView) view.findViewById(R.id.txt_no_review);
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
        shareButton.setOnClickListener(new OnSharedButtonClickListener());

        SetInitialData(savedInstanceState);
        SetToolbar();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home : getActivity().finish(); return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void SetToolbar()
    {
        movieDetailAppBar.addOnOffsetChangedListener( new AppBarLayout.OnOffsetChangedListener()
        {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
            {
                int heightCoverPoster = appBarLayout.getTotalScrollRange() * -1;
                if(verticalOffset > heightCoverPoster)
                {
                    if(isToolbarTransparent)
                    {
                        SetTransparentActionBar();
                        isToolbarTransparent = false;
                    }
                }

                else
                {
                    if(!isToolbarTransparent)
                    {
                        SetAppBartActionBar();
                        isToolbarTransparent = true;
                    }
                }
            }
        });

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(appBarMovieDetailToolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle("");
    }

    private void SetTransparentActionBar()
    {
        int colorFrom = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        int colorTo = ContextCompat.getColor(getContext(), android.R.color.transparent);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(100); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                appBarMovieDetailToolbar.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle("");
    }

    private void SetAppBartActionBar()
    {
        int colorFrom = ContextCompat.getColor(getContext(), android.R.color.transparent);
        int colorTo = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(100); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                appBarMovieDetailToolbar.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle(movieData.getOriginalTitle());
    }

    private void SetMovieDetail(MovieData movieData)
    {
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

    private void SetAllRecyclerView(MovieData movieData)
    {
        try
        {
            String reviewURL = MovieDataURL.GetReviewURL(String.valueOf(movieData.getId()), getContext());
            DBGetter.GetData(new MovieInfoDataGetter<ReviewData>(new ReviewDataListObtainedListener(), new ReviewDataDB(getContext()), new ReviewListJSONParser(), reviewURL, movieData.getId(), getContext()));

            String castURLurl = MovieDataURL.GetCastURL(String.valueOf(movieData.getId()));
            DBGetter.GetData(new MovieInfoDataGetter<CastData>(new CastDataListObtainedListener(), new CastDataDB(getContext()), new CastListJSONParser(), castURLurl, movieData.getId(), getContext()));

            String crewURL = MovieDataURL.GetCrewURL(String.valueOf(movieData.getId()));
            DBGetter.GetData(new MovieInfoDataGetter<CrewData>(new CrewDataListObtainedListener(), new CrewDataDB(getContext()), new CrewListJSONParser(), crewURL, movieData.getId(), getContext()));

            String videoURL = MovieDataURL.GetVideoURL(String.valueOf(movieData.getId()), getContext());
            DBGetter.GetData(new MovieInfoDataGetter<VideoData>(new VideoDataListObtainedListener(), new VideoDataDB(getContext()), new VideoListJSONParser(), videoURL, movieData.getId(), getContext()));
        }
        catch (Exception e)
        {
            ShowNoData();
        }
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
            favoriteState = getArguments().getBoolean(MovieDBKeyEntry.MovieDataPersistance.FAVORITE_STATE_PERSISTANCE_KEY);

            SetMovieDetail(movieData);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(reviewDataArrayList, getContext(), true), new LinearLayoutManager(MovieDetailFragment.this.getContext()), reviewListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(castDataArrayList, getContext(), new OnMovieCastChoosedListener()), new LinearLayoutManager(MovieDetailFragment.this.getContext()), castListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(crewDataArrayList, getContext(), new OnMovieCrewChoosedListener()), new LinearLayoutManager(MovieDetailFragment.this.getContext()), crewListRecyclerView);
            SetAdditionalMovieDetailRecyclerView(new VideoDataListRecyclerViewAdapter(videoDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false), videoListRecyclerView);

            ShowMovieDetail();
            SetFavoriteLabel(favoriteState);
        }
        else
        {
            ShowLoadingData();

            if(getArguments().containsKey(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY))
            {
                BundleDataGetter bundleDataGetter = new BundleDataGetter(getArguments());
                movieData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY);

                if (movieData != null)
                {
                    SetMovieDetail(movieData);
                    SetInitialFavoriteState();
                    SetAllRecyclerView(movieData);
                }
            }

            else if(getArguments().containsKey(MovieDBKeyEntry.MovieDataPersistance.MOVIE_ID_PERSISTANCE_KEY))
            {
                ShowLoadingData();
                String movieID = getArguments().getString(MovieDBKeyEntry.MovieDataPersistance.MOVIE_ID_PERSISTANCE_KEY);

                MovieDetailGetterAsyncTask movieDetailGetterAsyncTask = new MovieDetailGetterAsyncTask(movieID, getContext(), new MovieDataObtainedListener());
                movieDetailGetterAsyncTask.GetData();
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
        DataDB<String> dataDB = new FavoriteDataDB(MovieDetailFragment.this.getContext());

        if(favoriteState)
        {
            RemoveFavoriteMovie removeFavoriteMovie = new RemoveFavoriteMovie();
            removeFavoriteMovie.execute();

            favoriteState = false;
            SetFavoriteLabel(favoriteState);

            dataDB.removeData(movieData.getId());
        }

        else
        {
            InsertFavoriteMovie insertFavoriteMovie = new InsertFavoriteMovie();
            insertFavoriteMovie.execute();

            favoriteState = true;
            SetFavoriteLabel(favoriteState);

            dataDB.addData(movieData.getId());
        }

        SendMessageToActivity(MovieDBKeyEntry.DatabaseHasChanged.FAVORITE_DATA_CHANGED);
        return favoriteState;
    }

    private void SetFavoriteLabel(boolean state)
    {
        if (!state)
        {
            favoriteImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_white_24px));
            favoriteTitleTextView.setText(R.string.favorite_label);
        }

        else
        {
            favoriteImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_border_white_24px));
            favoriteTitleTextView.setText(R.string.favorite_label);
        }
    }

    private ArrayList<DataDB<String>> getInitialOtherFavoriteMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new NowShowingDataDB(getContext()));
        dataDBArrayList.add(new ComingSoonDataDB(getContext()));
        dataDBArrayList.add(new PopularDataDB(getContext()));
        dataDBArrayList.add(new TopRateDataDB(getContext()));
        dataDBArrayList.add(new WatchlistDataDB(getContext()));
        dataDBArrayList.add(new PlanToWatchDataDB(getContext()));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getInitialOtherWatchAndPlanWatchMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new NowShowingDataDB(getContext()));
        dataDBArrayList.add(new ComingSoonDataDB(getContext()));
        dataDBArrayList.add(new PopularDataDB(getContext()));
        dataDBArrayList.add(new TopRateDataDB(getContext()));
        dataDBArrayList.add(new FavoriteDataDB(getContext()));

        return dataDBArrayList;
    }

    private <Data extends MovieInfoData>void GoToMoreDataInfoList(ArrayList<Data> movieInfoDatas, String pageTitle, String labelTitle)
    {
        Intent intent = new Intent(getContext(), DataInfoListActivity.class);

        intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.DATA_INFO_PAGE_TITLE_PERSISTANCE_KEY, pageTitle);
        intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.DATA_INFO_LABEL_TITLE_PERSISTANCE_KEY, labelTitle);
        intent.putExtra(MovieDBKeyEntry.MovieDataPersistance.DATA_INFO_LIST_PERSISTANCE_KEY, movieInfoDatas);

        startActivity(intent);
    }

    private void SendMessageToActivity(int result)
    {
        Intent intent = new Intent();
        intent.putExtra(MovieDBKeyEntry.Messanger.UPDATE_YOURS_MOVIE_LIST, result);
        intent.setAction(MovieDBKeyEntry.Messanger.UPDATE_MOVIE_LIST_MESSANGER);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    private void ShareURL()
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        String urlMovie = "https://www.themoviedb.org/movie/" + movieData.getId();

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Movie");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, urlMovie);

        startActivity(Intent.createChooser(sharingIntent, "Share this movie via"));
    }

    private class InsertFavoriteMovie extends AsyncTask<Void, Void, Void>
    {
        Context context;

        public InsertFavoriteMovie()
        {
            context = MovieDetailFragment.this.getContext();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            DatabaseMovieInsertandRemove databaseMovieInsertandRemove = new DatabaseMovieInsertandRemove();
            databaseMovieInsertandRemove.Insert(movieData, castDataArrayList, crewDataArrayList, videoDataArrayList, reviewDataArrayList, context);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Toast.makeText(context, "Insert this movie to favorite list", Toast.LENGTH_SHORT).show();
        }
    }

    private class RemoveFavoriteMovie extends AsyncTask<Void, Void, Void>
    {
        Context context;

        public RemoveFavoriteMovie()
        {
            context = MovieDetailFragment.this.getContext();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            DatabaseMovieInsertandRemove databaseMovieInsertandRemove = new DatabaseMovieInsertandRemove();
            databaseMovieInsertandRemove.Remove(movieData.getId(), getInitialOtherFavoriteMovieListDataDB(), context);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Toast.makeText(context, "Remove this movie from favorite list", Toast.LENGTH_SHORT).show();
        }
    }

    private class InsertWatchAndPlanWatchMovie extends AsyncTask<Void, Void, Void>
    {
        Context context;

        public InsertWatchAndPlanWatchMovie()
        {
            context = MovieDetailFragment.this.getContext();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            DatabaseMovieInsertandRemove databaseMovieInsertandRemove = new DatabaseMovieInsertandRemove();
            databaseMovieInsertandRemove.Insert(movieData, castDataArrayList, crewDataArrayList, videoDataArrayList, reviewDataArrayList, context);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Toast.makeText(context, "Add this movie from the list", Toast.LENGTH_SHORT).show();
        }
    }

    private class RemoveWatchAndPlanWatchMovie extends AsyncTask<Void, Void, Void>
    {
        Context context;

        public RemoveWatchAndPlanWatchMovie()
        {
            context = MovieDetailFragment.this.getContext();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            DatabaseMovieInsertandRemove databaseMovieInsertandRemove = new DatabaseMovieInsertandRemove();
            databaseMovieInsertandRemove.Remove(movieData.getId(), getInitialOtherWatchAndPlanWatchMovieListDataDB(), context);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Toast.makeText(context, "Remove this movie from the list", Toast.LENGTH_SHORT).show();
        }
    }

    private class MovieDataObtainedListener implements OnDataObtainedListener<MovieData>
    {
        @Override
        public void onDataObtained(MovieData movieData)
        {
            if (movieData != null)
            {
                MovieDetailFragment.this.movieData = movieData;

                SetMovieDetail(movieData);
                SetInitialFavoriteState();

                NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<ReviewData>>
                        (new ReviewListJSONParser(), new ReviewDataListObtainedListener()), MovieDataURL.GetReviewURL(movieData.getId(), getContext()));
                NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<CastData>>
                        (new CastListJSONParser(), new CastDataListObtainedListener()), MovieDataURL.GetCastURL(movieData.getId()));
                NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<CrewData>>
                        (new CrewListJSONParser(), new CrewDataListObtainedListener()), MovieDataURL.GetCrewURL(movieData.getId()));
                NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<VideoData>>
                        (new VideoListJSONParser(), new VideoDataListObtainedListener()), MovieDataURL.GetVideoURL(movieData.getId(), getContext()));
            }
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

                    if(reviewDataArrayList.size() > 5)
                    {
                        moreReviewButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                GoToMoreDataInfoList(reviewDataArrayList, movieData.getOriginalTitle(), getString(R.string.review_label));
                            }
                        });
                    }

                    else
                    {
                        moreReviewButton.setVisibility(View.GONE);
                    }


                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(reviewDatas, getContext(), true), new LinearLayoutManager(MovieDetailFragment.this.getContext()), reviewListRecyclerView);
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

                    if(castDataArrayList.size() > 5)
                    {
                        moreCastButton.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                GoToMoreDataInfoList(castDataArrayList, movieData.getOriginalTitle(), getString(R.string.cast_label));
                            }
                        });
                    }

                    else
                    {
                        moreCastButton.setVisibility(View.GONE);
                    }

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(castDatas, getContext(), new OnMovieCastChoosedListener()), new LinearLayoutManager(MovieDetailFragment.this.getContext()), castListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoCasts();
            CheckAndShowMovieDetail();
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

                    if(crewDataArrayList.size() > 5)
                    {
                        moreCrewButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                GoToMoreDataInfoList(crewDataArrayList, movieData.getOriginalTitle(), getString(R.string.crew_label));
                            }
                        });
                    }

                    else
                    {
                        moreCrewButton.setVisibility(View.GONE);
                    }

                    SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter<>(crewDatas, getContext(), new OnMovieCrewChoosedListener()), new LinearLayoutManager(MovieDetailFragment.this.getContext()), crewListRecyclerView);
                    CheckAndShowMovieDetail();

                    return;
                }
            }

            ShowNoCrews();
            CheckAndShowMovieDetail();
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
            InsertWatchAndPlanWatchMovie insertWatchAndPlanWatchMovie = new InsertWatchAndPlanWatchMovie();
            RemoveWatchAndPlanWatchMovie removeWatchAndPlanWatchMovie = new RemoveWatchAndPlanWatchMovie();

            switch (integer)
            {
                case MovieDBKeyEntry.DatabaseHasChanged.REMOVE_MY_LIST :
                    removeWatchAndPlanWatchMovie.execute();
                    break;

                case MovieDBKeyEntry.DatabaseHasChanged.INSERT_TO_WATCHED_LIST :
                    insertWatchAndPlanWatchMovie.execute();
                    break;

                case MovieDBKeyEntry.DatabaseHasChanged.INSERT_PLAN_TO_WATCH_LIST :
                    insertWatchAndPlanWatchMovie.execute();
                    break;
            }

            SendMessageToActivity(integer);
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
            return DataAvailableCheck.isDataAvailable(new DefaultDataAvailableCheck<String>(new IDCompare(), idMovieList, movieData.getId()));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            onDataObtainedListener.onDataObtained(aBoolean);
        }
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

    private class OnSharedButtonClickListener implements ConstraintLayout.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            ShareURL();
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
                tvData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY);
                reviewDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_REVIEW_LIST_PERSISTANCE_KEY);
                castDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CAST_LIST_PERSISTANCE_KEY);
                crewDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_CREW_LIST_PERSISTANCE_KEY);
                castTVDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_VIDEO_LIST_PERSISTANCE_KEY);

                SetMovieDetail(tvData);
                SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(reviewDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext()), movieCastListRecyclerView);
                SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(castDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext()), tvCastListRecyclerView);
                SetAdditionalMovieDetailRecyclerView(new MovieInfoListRecycleViewAdapter(crewDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext()), movieCrewListRecyclerView);
                SetAdditionalMovieDetailRecyclerView(new VideoDataListRecyclerViewAdapter(castTVDataArrayList), new LinearLayoutManager(MovieDetailFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false), tvCrewListRecyclerView);

                ShowMovieDetail();

                return;
            }
        }

        else
        {
            BundleDataGetter bundleDataGetter = new BundleDataGetter(getArguments());
            tvData = bundleDataGetter.getData(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY);

            if(NetworkConnectionChecker.IsConnect(getContext()))
            {
                if(tvData != null)
                {
                    ShowLoadingData();

                    SetMovieDetail(tvData);
                    SetAllRecyclerView(tvData);

                    return;
                }
            }
        }

        ShowNoInternetAccess();
    }

    private void SetAllRecyclerView(PeopleData tvData) {
        String reviewURL = MovieDataURL.GetReviewURL(String.valueOf(tvData.getId()));
        DBGetter.GetDataAsyncTask(new MovieInfoDataGetter<ReviewData>(new ReviewDataListObtainedListener(), new ReviewDataDB(getContext()), new ReviewListJSONParser(), reviewURL, tvData.getId(), getContext()));

        String castURLurl = MovieDataURL.GetCastURL(String.valueOf(tvData.getId()));
        DBGetter.GetDataAsyncTask(new MovieInfoDataGetter<CastData>(new CastDataListObtainedListener(), new CastDataDB(getContext()), new CastListJSONParser(), castURLurl, tvData.getId(), getContext()));

        String crewURL = MovieDataURL.GetCrewURL(String.valueOf(tvData.getId()));
        DBGetter.GetDataAsyncTask(new MovieInfoDataGetter<CrewData>(new CrewDataListObtainedListener(), new CrewDataDB(getContext()), new CrewListJSONParser(), crewURL, tvData.getId(), getContext()));

        String videoURL = MovieDataURL.GetVideoURL(String.valueOf(tvData.getId()));
        DBGetter.GetDataAsyncTask(new MovieInfoDataGetter<VideoData>(new VideoDataListObtainedListener(), new VideoDataDB(getContext()), new VideoListJSONParser(), videoURL, tvData.getId(), getContext()));
        /*NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<ReviewData>>(new ReviewListJSONParser(), new ReviewDataListObtainedListener()), reviewURL);

        String castURLurl = MovieDataURL.GetCastURL(String.valueOf(tvData.getId()));
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<CastData>>(new CastListJSONParser(), new CastDataListObtainedListener()), castURLurl);

        String crewURL = MovieDataURL.GetCrewURL(String.valueOf(tvData.getId()));
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<CrewData>>(new CrewListJSONParser(), new CrewDataListObtainedListener()), crewURL);

        String videoURL = MovieDataURL.GetVideoURL(String.valueOf(tvData.getId()));
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<VideoData>>(new VideoListJSONParser(), new VideoDataListObtainedListener()), videoURL);*/




