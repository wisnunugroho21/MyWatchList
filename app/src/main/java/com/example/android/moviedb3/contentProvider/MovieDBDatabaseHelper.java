package com.example.android.moviedb3.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.moviedb3.movieDB.CastData;

import java.util.Calendar;

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
                MovieDBContract.CrewDataEntry.COLUMN_PEOPLE_ID + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_CREW_TABLE);

        final String SQLITE_CREATE_CAST_TABLE = "CREATE TABLE " + MovieDBContract.CastDataEntry.TABLE_CAST_DATA + " (" +
                MovieDBContract.CastDataEntry._ID + " TEXT, " +
                MovieDBContract.CastDataEntry.COLUMN_CAST_NAME + " TEXT, " +
                MovieDBContract.CastDataEntry.COLUMN_CHARACTER_NAME + " TEXT, " +
                MovieDBContract.CastDataEntry.COLUMN_MOVIE_ID + " TEXT, " +
                MovieDBContract.CastDataEntry.COLUMN_PEOPLE_ID + " TEXT " +
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
                MovieDBContract.PeopleDataEntry.COLUMN_KNOWN_ROLES + " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_PEOPLE_TABLE);
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

        onCreate(db);
    }
}
