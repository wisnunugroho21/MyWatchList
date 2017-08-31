package com.example.android.moviedb3.movieDB;

import com.example.android.moviedb3.BuildConfig;

/**
 * Created by nugroho on 30/07/17.
 */

public class MovieDBKeyEntry
{
    public static final String GET_MOVIE_LIST_MESSENGER = "GetMovieListMessenger";

    public class MovieDataPersistance
    {
        public static final String MOVIE_DATA_PERSISTANCE_KEY = "MovieData";
        public static final String MOVIE_DATA_LIST_PERSISTANCE_KEY = "MovieDataList";
        public static final String MOVIE_REVIEW_LIST_PERSISTANCE_KEY = "MovieReviewList";
        public static final String MOVIE_CAST_LIST_PERSISTANCE_KEY = "MovieCastList";
        public static final String MOVIE_CREW_LIST_PERSISTANCE_KEY = "MovieCrewList";
        public static final String MOVIE_VIDEO_LIST_PERSISTANCE_KEY = "MovieVideoList";
        public static final String INTERNET_NETWORK_STATE_PERSISTANCE_KEY = "InternetNetworkState";
        public static final String ADDITIONAL_MOVIE_DETAIL_STATE_PERSISTANCE_KEY = "AdditionalMovieDetailState";
    }

    public class DatabaseHasChanged
    {
        public static final int FAVORITE_DATA_CHANGED = 70;
        public static final int WATCHED_DATA_CHANGED = 80;
    }

    public class MovieListState
    {
        public static final String POPULAR_STATE = "403";
        public static final String TOP_RATE_STATE = "586";
        public static final String NOW_SHOWING_STATE = "867";
        public static final String COMING_SOON_STATE = "191";
        public static final String FAVORITE_STATE = "76";
        public static final String WATCHED_STATE = "908";
    }

    public class InternetNetworkState
    {
        public static final int CONNECTED = 847;
        public static final int NOT_CONNECTED = 89;
    }

    public class AdditionalMovieDetailState
    {
        public static final int REVIEW = 728;
        public static final int VIDEO = 737;
        public static final int CAST = 391;
        public static final int CREW = 469;
    }

    public class SharedPreferencesKey
    {
        public static final String MOVIE_LIST_STATE = "MovieListState";
    }

}
