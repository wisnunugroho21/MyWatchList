package com.example.android.moviedb3.movieDB;

import com.example.android.moviedb3.BuildConfig;

/**
 * Created by nugroho on 30/07/17.
 */

public class MovieDBKeyEntry
{
    public class GetDataIntentServiceKey
    {
        public static final String GET_MOVIE_LIST_RESULT_RECEIVER = "GetMovieListResultReceiver";
        public static final int GET_MOVIE_LIST_RESULT_SUCCESS = 1978;
        public static final int GET_MOVIE_LIST_RESULT_FAIL = 1729;
        public static final String GET_MOVIE_LIST_MESSENGER = "GetMovieListMessenger";
    }

    public class JobSchedulerID
    {
        public static final String PERIODIC_NETWORK_JOB_KEY = "periodicNetworkJob";
        public static final int NOW_NETWORK_JOB_KEY = 156;
    }

    public class MovieDataPersistance
    {
        public static final String MOVIE_DATA_PERSISTANCE_KEY = "MovieData";
        public static final String MOVIE_ID_PERSISTANCE_KEY = "MovieIDData";
        public static final String MOVIE_DATA_LIST_PERSISTANCE_KEY = "MovieDataList";
        public static final String GENRE_PERSISTANCE_KEY = "GenreData";

        public static final String MOVIE_REVIEW_LIST_PERSISTANCE_KEY = "MovieReviewList";
        public static final String MOVIE_CAST_LIST_PERSISTANCE_KEY = "MovieCastList";
        public static final String MOVIE_CREW_LIST_PERSISTANCE_KEY = "MovieCrewList";
        public static final String MOVIE_VIDEO_LIST_PERSISTANCE_KEY = "MovieVideoList";

        public static final String FAVORITE_STATE_PERSISTANCE_KEY = "FavoriteState";

        public static final String TV_DATA_PERSISTANCE_KEY = "TVData";
        public static final String TV_ID_PERSISTANCE_KEY = "TVIDData";
        public static final String TV_DATA_LIST_PERSISTANCE_KEY = "TVDataList";
        public static final String TV_GENRE_PERSISTANCE_KEY = "TVGenreData";

        public static final String TV_CAST_LIST_PERSISTANCE_KEY = "TVCastList";
        public static final String TV_CREW_LIST_PERSISTANCE_KEY = "TVCrewList";
        public static final String TV_VIDEO_LIST_PERSISTANCE_KEY = "TVVideoList";

        public static final String PEOPLE_MOVIE_CAST_LIST_PERSISTANCE_KEY = "PeopleMovieCastList";
        public static final String PEOPLE_MOVIE_CREW_LIST_PERSISTANCE_KEY = "PeopleMovieCrewList";
        public static final String PEOPLE_TV_CAST_LIST_PERSISTANCE_KEY = "PeopleTVCastList";
        public static final String PEOPLE_TV_CREW_LIST_PERSISTANCE_KEY = "PeopleTVCrewList";

        public static final String PEOPLE_DATA_PERSISTANCE_KEY = "PeopleData";
        public static final String PEOPLE_ID_PERSISTANCE_KEY = "PeopleIDData";

        public static final String DATA_INFO_LIST_PERSISTANCE_KEY = "DataInfoList";
        public static final String DATA_INFO_PAGE_TITLE_PERSISTANCE_KEY = "DataInfoPageTitle";
        public static final String DATA_INFO_LABEL_TITLE_PERSISTANCE_KEY = "DataInfoLabelTitle";

        public static final String INTERNET_NETWORK_STATE_PERSISTANCE_KEY = "InternetNetworkState";
        public static final String ADDITIONAL_MOVIE_DETAIL_STATE_PERSISTANCE_KEY = "AdditionalMovieDetailState";
    }

    public class NotificationKey
    {
        public static final int NEW_NOW_SHOWING_MOVIE_NOTIFICATION = 1980;
        public static final int GETTING_DATA_NOTIFICATION = 2901;
    }

    public class DatabaseHasChanged
    {
        public static final int FAVORITE_DATA_CHANGED = 70;
        public static final int REMOVE_MY_LIST = 100;
        public static final int INSERT_TO_WATCHED_LIST = 80;
        public static final int INSERT_PLAN_TO_WATCH_LIST = 90;
    }

    public class MovieListPageIndex
    {
        public static final int POPULAR_PAGE_INDEX = 0;
        public static final int TOP_RATE_PAGE_INDEX = 1;
        public static final int NOW_SHOWING_PAGE_INDEX = 0;
        public static final int COMING_SOON_PAGE_INDEX = 1;
        public static final int FAVORITE_PAGE_INDEX = 0;
        public static final int WATCHED_PAGE_INDEX = 1;
        public static final int PLAN_TO_WATCH_PAGE_INDEX = 2;
    }

    public class MovieListPageAdapterIndex
    {
        public static final int HOME_PAGE_ADAPTER_INDEX = 0;
        public static final int TOP_LIST_PAGE_ADAPTER_INDEX = 1;
        public static final int YOURS_PAGE_ADAPTER_INDEX = 3;
    }

    public class Messanger
    {
        public static final String UPDATE_MOVIE_LIST_MESSANGER = "UpdateMovieListMessanger";
        public static final String UPDATE_YOURS_MOVIE_LIST = "UpdateYoursMovieList";
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
        public static final String CHECK_FIRST_TIME = "checkFirstTime";
        public static final String SAVED_NUMBER = "savedNumber";
    }

}
