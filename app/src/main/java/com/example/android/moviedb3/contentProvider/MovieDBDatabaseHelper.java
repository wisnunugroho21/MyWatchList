package com.example.android.moviedb3.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nugroho on 26/07/17.
 */

public class MovieDBDatabaseHelper  extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "MovieDB.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDBDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQLITE_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieDBContract.MovieDataEntry.TABLE_MOVIE_DATA + " (" +
                MovieDBContract.MovieDataEntry._ID + " TEXT, " +
                MovieDBContract.MovieDataEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
                MovieDBContract.MovieDataEntry.COLUMN_MOVIE_POSTER_URL + " TEXT, " +
                MovieDBContract.MovieDataEntry.COLUMN_BACKDROP_IMAGE_URL + " TEXT, " +
                MovieDBContract.MovieDataEntry.COLUMN_GENRE + " TEXT, " +
                MovieDBContract.MovieDataEntry.COLUMN_DURATION + " INTEGER, " +
                MovieDBContract.MovieDataEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                MovieDBContract.MovieDataEntry.COLUMN_VOTE_RATING + " DOUBLE, " +
                MovieDBContract.MovieDataEntry.COLUMN_TAGLINE + " TEXT, " +
                MovieDBContract.MovieDataEntry.COLUMN_PLOT_SYNOPSIS + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_MOVIE_TABLE);

        final String SQLITE_CREATE_POPULAR_TABLE = "CREATE TABLE " + MovieDBContract.PopularDataEntry.TABLE_POPULAR_DATA + " (" +
                MovieDBContract.PopularDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.PopularDataEntry.COLUMN_MOVIE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_POPULAR_TABLE);

        final String SQLITE_CREATE_TOP_RATE_TABLE = "CREATE TABLE " + MovieDBContract.TopRateDataEntry.TABLE_TOP_RATE_DATA + " (" +
                MovieDBContract.TopRateDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.TopRateDataEntry.COLUMN_MOVIE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_TOP_RATE_TABLE);

        final String SQLITE_CREATE_NOW_SHOWING_TABLE = "CREATE TABLE " + MovieDBContract.NowShowingDataEntry.TABLE_NOW_SHOWING_DATA + " (" +
                MovieDBContract.NowShowingDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.NowShowingDataEntry.COLUMN_MOVIE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_NOW_SHOWING_TABLE);

        final String SQLITE_CREATE_COMING_SOON_TABLE = "CREATE TABLE " + MovieDBContract.ComingSoonDataEntry.TABLE_COMING_SOON_DATA + " (" +
                MovieDBContract.ComingSoonDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.ComingSoonDataEntry.COLUMN_MOVIE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_COMING_SOON_TABLE);


        final String SQLITE_CREATE_FAVORITE_TABLE = "CREATE TABLE " + MovieDBContract.FavoriteDataEntry.TABLE_FAVORITE_DATA + " (" +
                MovieDBContract.FavoriteDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.FavoriteDataEntry.COLUMN_MOVIE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_FAVORITE_TABLE);

        final String SQLITE_CREATE_WATCHLIST_TABLE = "CREATE TABLE " + MovieDBContract.WatchlistDataEntry.TABLE_WATCHLIST_DATA + " (" +
                MovieDBContract.WatchlistDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.WatchlistDataEntry.COLUMN_MOVIE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_WATCHLIST_TABLE);

        final String SQLITE_CREATE_PLAN_TO_WATCH_LIST_TABLE = "CREATE TABLE " + MovieDBContract.PlanToWatchlistDataEntry.TABLE_PLAN_TO_WATCH_LIST_DATA + " (" +
                MovieDBContract.WatchlistDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.WatchlistDataEntry.COLUMN_MOVIE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_PLAN_TO_WATCH_LIST_TABLE);

        final String SQLITE_CREATE_CREW_TABLE = "CREATE TABLE " + MovieDBContract.CrewDataEntry.TABLE_CREW_DATA + " (" +
                MovieDBContract.CrewDataEntry._ID + " TEXT, " +
                MovieDBContract.CrewDataEntry.COLUMN_CREW_NAME + " TEXT, " +
                MovieDBContract.CrewDataEntry.COLUMN_CREW_POSITION + " TEXT, " +
                MovieDBContract.CrewDataEntry.COLUMN_MOVIE_ID + " TEXT, " +
                MovieDBContract.CrewDataEntry.COLUMN_PEOPLE_ID + " TEXT, " +
                MovieDBContract.CrewDataEntry.COLUMN_IMAGE + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_CREW_TABLE);

        final String SQLITE_CREATE_CAST_TABLE = "CREATE TABLE " + MovieDBContract.CastDataEntry.TABLE_CAST_DATA + " (" +
                MovieDBContract.CastDataEntry._ID + " TEXT, " +
                MovieDBContract.CastDataEntry.COLUMN_CAST_NAME + " TEXT, " +
                MovieDBContract.CastDataEntry.COLUMN_CHARACTER_NAME + " TEXT, " +
                MovieDBContract.CastDataEntry.COLUMN_MOVIE_ID + " TEXT, " +
                MovieDBContract.CastDataEntry.COLUMN_PEOPLE_ID +" TEXT, " +
                MovieDBContract.CastTVDataEntry.COLUMN_IMAGE + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_CAST_TABLE);

        final String SQLITE_CREATE_REVIEW_TABLE = "CREATE TABLE " + MovieDBContract.ReviewDataEntry.TABLE_REVIEW_DATA + " (" +
                MovieDBContract.ReviewDataEntry._ID + " TEXT, " +
                MovieDBContract.ReviewDataEntry.COLUMN_REVIEWER_NAME + " TEXT, " +
                MovieDBContract.ReviewDataEntry.COLUMN_REVIEW_CONTENT + " TEXT, " +
                MovieDBContract.ReviewDataEntry.COLUMN_MOVIE_ID + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_REVIEW_TABLE);

        final String SQLITE_CREATE_VIDEO_TABLE = "CREATE TABLE " + MovieDBContract.VideoDataEntry.TABLE_VIDEO_DATA + " (" +
                MovieDBContract.VideoDataEntry._ID + " TEXT, " +
                MovieDBContract.VideoDataEntry.COLUMN_VIDEO_URL + " TEXT, " +
                MovieDBContract.VideoDataEntry.COLUMN_VIDEO_THUMBNAIL_URL + " TEXT, " +
                MovieDBContract.VideoDataEntry.COLUMN_MOVIE_ID + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_VIDEO_TABLE);

        final String SQLITE_CREATE_GENRE_TABLE = "CREATE TABLE " + MovieDBContract.GenreDataEntry.TABLE_GENRE_DATA + " (" +
                MovieDBContract.GenreDataEntry._ID + " TEXT, " +
                MovieDBContract.GenreDataEntry.COLUMN_GENRE_NAME + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_GENRE_TABLE);

        final String SQLITE_CREATE_GENRE_MOVIE_POPULAR_TABLE = "CREATE TABLE " + MovieDBContract.GenreMoviePopularEntry.TABLE_GENRE_MOVIE_POPULAR_DATA + " (" +
                MovieDBContract.GenreMoviePopularEntry._ID + " TEXT, " +
                MovieDBContract.GenreMoviePopularEntry.COLUMN_MOVIE_ID + " TEXT, " +
                MovieDBContract.GenreMoviePopularEntry.COLUMN_GENRE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_GENRE_MOVIE_POPULAR_TABLE);

        final String SQLITE_CREATE_GENRE_MOVIE_TOP_RATE_TABLE = "CREATE TABLE " + MovieDBContract.GenreMovieTopRateEntry.TABLE_GENRE_MOVIE_TOP_RATE_DATA + " (" +
                MovieDBContract.GenreMovieTopRateEntry._ID + " TEXT, " +
                MovieDBContract.GenreMovieTopRateEntry.COLUMN_MOVIE_ID + " TEXT, " +
                MovieDBContract.GenreMovieTopRateEntry.COLUMN_GENRE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_GENRE_MOVIE_TOP_RATE_TABLE);

        final String SQLITE_CREATE_PEOPLE_TABLE = "CREATE TABLE " + MovieDBContract.PeopleDataEntry.TABLE_PEOPLE_DATA + " (" +
                MovieDBContract.PeopleDataEntry._ID + " TEXT, " +
                MovieDBContract.PeopleDataEntry.COLUMN_PEOPLE_NAME + " TEXT, " +
                MovieDBContract.PeopleDataEntry.COLUMN_PLACE_OF_BIRTH + " TEXT, " +
                MovieDBContract.PeopleDataEntry.COLUMN_BIRTHDAY + " TEXT, " +
                MovieDBContract.PeopleDataEntry.COLUMN_BIOGRAPHY + " TEXT, " +
                MovieDBContract.PeopleDataEntry.COLUMN_PROFILE_IMAGE + " TEXT, " +
                MovieDBContract.PeopleDataEntry.COLUMN_OTHER_NAME + " TEXT, " +
                MovieDBContract.PeopleDataEntry.COLUMN_KNOWN_ROLES + " TEXT, " +
                MovieDBContract.PeopleDataEntry.COLUMN_BACKDROP_IMAGE + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_PEOPLE_TABLE);

        final String SQLITE_CREATE_TV_TABLE = "CREATE TABLE " + MovieDBContract.TVDataEntry.TABLE_TV_DATA + " (" +
                MovieDBContract.TVDataEntry._ID + " TEXT, " +
                MovieDBContract.TVDataEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
                MovieDBContract.TVDataEntry.COLUMN_MOVIE_POSTER_URL + " TEXT, " +
                MovieDBContract.TVDataEntry.COLUMN_BACKDROP_IMAGE_URL + " TEXT, " +
                MovieDBContract.TVDataEntry.COLUMN_GENRE + " TEXT, " +
                MovieDBContract.TVDataEntry.COLUMN_EPISODE + " INTEGER, " +
                MovieDBContract.TVDataEntry.COLUMN_SEASON + " INTEGER, " +
                MovieDBContract.TVDataEntry.COLUMN_FIRST_RELEASE_DATE + " TEXT, " +
                MovieDBContract.TVDataEntry.COLUMN_VOTE_RATING + " DOUBLE, " +
                MovieDBContract.TVDataEntry.COLUMN_PLOT_SYNOPSIS + " TEXT, " +
                MovieDBContract.TVDataEntry.COLUMN_AVAILABLE_ON_NETWORK + " TEXT, " +
                MovieDBContract.TVDataEntry.COLUMN_SERIES_STATUS + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_TV_TABLE);

        final String SQLITE_CREATE_POPULAR_TV_TABLE = "CREATE TABLE " + MovieDBContract.PopularTVDataEntry.TABLE_POPULAR_TV_DATA + " (" +
                MovieDBContract.PopularTVDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.PopularTVDataEntry.COLUMN_TV_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_POPULAR_TV_TABLE);

        final String SQLITE_CREATE_TOP_RATE_TV_TABLE = "CREATE TABLE " + MovieDBContract.TopRateTVDataEntry.TABLE_TOP_RATE_TV_DATA + " (" +
                MovieDBContract.TopRateTVDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.TopRateTVDataEntry.COLUMN_TV_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_TOP_RATE_TV_TABLE);

        final String SQLITE_CREATE_AIR_TODAY_TV_TABLE = "CREATE TABLE " + MovieDBContract.AirTodayTVDataEntry.TABLE_AIR_TODAY_TV_DATA + " (" +
                MovieDBContract.AirTodayTVDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.AirTodayTVDataEntry.COLUMN_TV_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_AIR_TODAY_TV_TABLE);

        final String SQLITE_CREATE_ON_THE_AIR_TV_TABLE = "CREATE TABLE " + MovieDBContract.OnTheAirTVDataEntry.TABLE_ON_THE_AIR_TV_DATA + " (" +
                MovieDBContract.OnTheAirTVDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.OnTheAirTVDataEntry.COLUMN_TV_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_ON_THE_AIR_TV_TABLE);


        final String SQLITE_CREATE_FAVORITE_TV_TABLE = "CREATE TABLE " + MovieDBContract.FavoriteTVDataEntry.TABLE_FAVORITE_TV_DATA + " (" +
                MovieDBContract.FavoriteTVDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.FavoriteTVDataEntry.COLUMN_TV_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_FAVORITE_TV_TABLE);

        final String SQLITE_CREATE_WATCHLIST_TV_TABLE = "CREATE TABLE " + MovieDBContract.WatchlistTVDataEntry.TABLE_WATCHLIST_TV_DATA + " (" +
                MovieDBContract.WatchlistTVDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.WatchlistTVDataEntry.COLUMN_TV_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_WATCHLIST_TV_TABLE);

        final String SQLITE_CREATE_PLAN_TO_WATCH_LIST_TV_TABLE = "CREATE TABLE " + MovieDBContract.PlanToWatchlistTVDataEntry.TABLE_PLAN_TO_WATCH_LIST_TV_DATA + " (" +
                MovieDBContract.PlanToWatchlistTVDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDBContract.PlanToWatchlistTVDataEntry.COLUMN_TV_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_PLAN_TO_WATCH_LIST_TV_TABLE);

        final String SQLITE_CREATE_CREW_TV_TABLE = "CREATE TABLE " + MovieDBContract.CrewTVDataEntry.TABLE_CREW_TV_DATA + " (" +
                MovieDBContract.CrewTVDataEntry._ID + " TEXT, " +
                MovieDBContract.CrewTVDataEntry.COLUMN_CREW_NAME + " TEXT, " +
                MovieDBContract.CrewTVDataEntry.COLUMN_CREW_POSITION + " TEXT, " +
                MovieDBContract.CrewTVDataEntry.COLUMN_TV_ID + " TEXT, " +
                MovieDBContract.CrewTVDataEntry.COLUMN_PEOPLE_ID + " TEXT, " +
                MovieDBContract.CrewTVDataEntry.COLUMN_IMAGE + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_CREW_TV_TABLE);

        final String SQLITE_CREATE_CAST_TV_TABLE = "CREATE TABLE " + MovieDBContract.CastTVDataEntry.TABLE_CAST_TV_DATA + " (" +
                MovieDBContract.CastTVDataEntry._ID + " TEXT, " +
                MovieDBContract.CastTVDataEntry.COLUMN_CAST_NAME + " TEXT, " +
                MovieDBContract.CastTVDataEntry.COLUMN_CHARACTER_NAME + " TEXT, " +
                MovieDBContract.CastTVDataEntry.COLUMN_TV_ID + " TEXT, " +
                MovieDBContract.CastTVDataEntry.COLUMN_PEOPLE_ID + " TEXT, " +
                MovieDBContract.CastTVDataEntry.COLUMN_IMAGE + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_CAST_TV_TABLE);

        final String SQLITE_CREATE_VIDEO_TV_TABLE = "CREATE TABLE " + MovieDBContract.VideoTVDataEntry.TABLE_VIDEO_TV_DATA + " (" +
                MovieDBContract.VideoTVDataEntry._ID + " TEXT, " +
                MovieDBContract.VideoTVDataEntry.COLUMN_VIDEO_URL + " TEXT, " +
                MovieDBContract.VideoTVDataEntry.COLUMN_VIDEO_THUMBNAIL_URL + " TEXT, " +
                MovieDBContract.VideoTVDataEntry.COLUMN_TV_ID + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_VIDEO_TV_TABLE);

        final String SQLITE_CREATE_GENRE_TV_POPULAR_TABLE = "CREATE TABLE " + MovieDBContract.GenreTVPopularEntry.TABLE_GENRE_TV_POPULAR_DATA + " (" +
                MovieDBContract.GenreTVPopularEntry._ID + " TEXT, " +
                MovieDBContract.GenreTVPopularEntry.COLUMN_TV_ID+ " TEXT, " +
                MovieDBContract.GenreTVPopularEntry.COLUMN_GENRE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_GENRE_TV_POPULAR_TABLE);

        final String SQLITE_CREATE_GENRE_TV_TOP_RATE_TABLE = "CREATE TABLE " + MovieDBContract.GenreTVTopRateEntry.TABLE_GENRE_TV_TOP_RATE_DATA + " (" +
                MovieDBContract.GenreTVTopRateEntry._ID + " TEXT, " +
                MovieDBContract.GenreTVTopRateEntry.COLUMN_TV_ID + " TEXT, " +
                MovieDBContract.GenreTVTopRateEntry.COLUMN_GENRE_ID + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_GENRE_TV_TOP_RATE_TABLE);

        final String SQLITE_CREATE_GENRE_TV_TABLE = "CREATE TABLE " + MovieDBContract.GenreTVDataEntry.TABLE_GENRE_TV_DATA + " (" +
                MovieDBContract.GenreTVDataEntry._ID + " TEXT, " +
                MovieDBContract.GenreTVDataEntry.COLUMN_GENRE_NAME + " TEXT" +
                ");";

        db.execSQL(SQLITE_CREATE_GENRE_TV_TABLE);

        final String SQLITE_CREATE_PEOPLE_CREW_TABLE = "CREATE TABLE " + MovieDBContract.PeopleCrewDataEntry.TABLE_PEOPLE_CREW_DATA + " (" +
                MovieDBContract.PeopleCrewDataEntry._ID + " TEXT, " +
                MovieDBContract.PeopleCrewDataEntry.COLUMN_MOVIE_NAME + " TEXT, " +
                MovieDBContract.PeopleCrewDataEntry.COLUMN_CREW_POSITION + " TEXT, " +
                MovieDBContract.PeopleCrewDataEntry.COLUMN_MOVIE_ID + " TEXT, " +
                MovieDBContract.PeopleCrewDataEntry.COLUMN_PEOPLE_ID + " TEXT, " +
                MovieDBContract.PeopleCrewDataEntry.COLUMN_IMAGE + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_PEOPLE_CREW_TABLE);

        final String SQLITE_CREATE_PEOPLE_CAST_TABLE = "CREATE TABLE " + MovieDBContract.PeopleCastDataEntry.TABLE_PEOPLE_CAST_DATA + " (" +
                MovieDBContract.PeopleCastDataEntry._ID + " TEXT, " +
                MovieDBContract.PeopleCastDataEntry.COLUMN_MOVIE_NAME + " TEXT, " +
                MovieDBContract.PeopleCastDataEntry.COLUMN_CHARACTER_NAME + " TEXT, " +
                MovieDBContract.PeopleCastDataEntry.COLUMN_MOVIE_ID + " TEXT, " +
                MovieDBContract.PeopleCastDataEntry.COLUMN_PEOPLE_ID +" TEXT, " +
                MovieDBContract.PeopleCastDataEntry.COLUMN_IMAGE + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_PEOPLE_CAST_TABLE);

        final String SQLITE_CREATE_PEOPLE_CREW_TV_TABLE = "CREATE TABLE " + MovieDBContract.PeopleCrewTVDataEntry.TABLE_PEOPLE_CREW_TV_DATA + " (" +
                MovieDBContract.PeopleCrewTVDataEntry._ID + " TEXT, " +
                MovieDBContract.PeopleCrewTVDataEntry.COLUMN_TV_NAME + " TEXT, " +
                MovieDBContract.PeopleCrewTVDataEntry.COLUMN_CREW_POSITION + " TEXT, " +
                MovieDBContract.PeopleCrewTVDataEntry.COLUMN_TV_ID + " TEXT, " +
                MovieDBContract.PeopleCrewTVDataEntry.COLUMN_PEOPLE_ID + " TEXT, " +
                MovieDBContract.PeopleCrewTVDataEntry.COLUMN_IMAGE + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_PEOPLE_CREW_TV_TABLE);

        final String SQLITE_CREATE_PEOPLE_CAST_TV_TABLE = "CREATE TABLE " + MovieDBContract.PeopleCastTVDataEntry.TABLE_PEOPLE_CAST_TV_DATA + " (" +
                MovieDBContract.PeopleCastTVDataEntry._ID + " TEXT, " +
                MovieDBContract.PeopleCastTVDataEntry.COLUMN_TV_NAME + " TEXT, " +
                MovieDBContract.PeopleCastTVDataEntry.COLUMN_CHARACTER_NAME + " TEXT, " +
                MovieDBContract.PeopleCastTVDataEntry.COLUMN_TV_ID + " TEXT, " +
                MovieDBContract.PeopleCastTVDataEntry.COLUMN_PEOPLE_ID + " TEXT, " +
                MovieDBContract.PeopleCastTVDataEntry.COLUMN_IMAGE + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_PEOPLE_CAST_TV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.MovieDataEntry.TABLE_MOVIE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.PopularDataEntry.TABLE_POPULAR_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.TopRateDataEntry.TABLE_TOP_RATE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.FavoriteDataEntry.TABLE_FAVORITE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.WatchlistDataEntry.TABLE_WATCHLIST_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.PlanToWatchlistDataEntry.TABLE_PLAN_TO_WATCH_LIST_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.CrewDataEntry.TABLE_CREW_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.CastDataEntry.TABLE_CAST_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.ReviewDataEntry.TABLE_REVIEW_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.VideoDataEntry.TABLE_VIDEO_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.GenreDataEntry.TABLE_GENRE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.GenreMoviePopularEntry.TABLE_GENRE_MOVIE_POPULAR_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.GenreMovieTopRateEntry.TABLE_GENRE_MOVIE_TOP_RATE_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.TVDataEntry.TABLE_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.PopularTVDataEntry.TABLE_POPULAR_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.TopRateTVDataEntry.TABLE_TOP_RATE_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.FavoriteTVDataEntry.TABLE_FAVORITE_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.WatchlistTVDataEntry.TABLE_WATCHLIST_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.PlanToWatchlistTVDataEntry.TABLE_PLAN_TO_WATCH_LIST_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.CrewTVDataEntry.TABLE_CREW_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.CastTVDataEntry.TABLE_CAST_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.VideoTVDataEntry.TABLE_VIDEO_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.GenreTVPopularEntry.TABLE_GENRE_TV_POPULAR_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.GenreTVTopRateEntry.TABLE_GENRE_TV_TOP_RATE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.GenreTVDataEntry.TABLE_GENRE_TV_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.PeopleCastDataEntry.TABLE_PEOPLE_CAST_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.PeopleCrewDataEntry.TABLE_PEOPLE_CREW_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.PeopleCastTVDataEntry.TABLE_PEOPLE_CAST_TV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.PeopleCastTVDataEntry.TABLE_PEOPLE_CAST_TV_DATA);

        onCreate(db);
    }
}
