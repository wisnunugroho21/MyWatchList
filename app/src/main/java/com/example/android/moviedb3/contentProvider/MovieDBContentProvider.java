package com.example.android.moviedb3.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by nugroho on 29/07/17.
 */

public class MovieDBContentProvider extends ContentProvider
{
    public static final int MOVIE = 100;
    public static final int MOVIE_ID = 101;

    public static final int POPULAR = 200;
    public static final int POPULAR_ID = 201;

    public static final int TOP_RATE = 300;
    public static final int TOP_RATE_ID = 301;

    public static final int NOW_SHOWING = 1000;
    public static final int NOW_SHOWING_ID = 1001;

    public static final int COMING_SOON = 1100;
    public static final int COMING_SOON_ID = 1101;

    public static final int FAVORITE = 400;
    public static final int FAVORITE_ID = 401;

    public static final int WATCHLIST = 500;
    public static final int WATCHLIST_ID = 501;

    public static final int PLAN_TO_WATCH = 1200;
    public static final int PLAN_TO_WATCH_ID = 1201;

    public static final int CAST = 600;
    public static final int CAST_ID = 601;

    public static final int CREW = 700;
    public static final int CREW_ID = 701;

    public static final int REVIEW = 800;
    public static final int REVIEW_ID = 801;

    public static final int VIDEO = 900;
    public static final int VIDEO_ID = 901;

    public static final int GENRE = 1300;
    public static final int GENRE_ID = 1301;

    public static final int GENRE_MOVIE_POPULAR = 1400;
    public static final int GENRE_MOVIE_POPULAR_ID = 1401;

    public static final int GENRE_MOVIE_TOP_RATE = 1500;
    public static final int GENRE_MOVIE_TOP_RATE_ID = 1501;

    public static final int PEOPLE = 1600;
    public static final int PEOPLE_ID = 1601;

    public static final int TV = 1700;
    public static final int TV_ID = 1701;

    public static final int POPULAR_TV = 1800;
    public static final int POPULAR_TV_ID = 1801;

    public static final int TOP_RATE_TV = 1900;
    public static final int TOP_RATE_TV_ID = 1901;

    public static final int AIR_TODAY_TV = 2000;
    public static final int AIR_TODAY_TV_ID = 2001;

    public static final int ON_THE_AIR_TV = 2100;
    public static final int ON_THE_AIR_TV_ID = 2101;

    public static final int FAVORITE_TV = 2200;
    public static final int FAVORITE_TV_ID = 2201;

    public static final int WATCHLIST_TV = 2300;
    public static final int WATCHLIST_TV_ID = 2301;

    public static final int PLAN_TO_WATCH_TV = 2400;
    public static final int PLAN_TO_WATCH_TV_ID = 2401;

    public static final int CAST_TV = 2500;
    public static final int CAST_TV_ID = 2501;

    public static final int CREW_TV = 2600;
    public static final int CREW_TV_ID = 2601;

    public static final int VIDEO_TV = 2700;
    public static final int VIDEO_TV_ID = 2701;

    public static final int GENRE_TV_POPULAR = 2800;
    public static final int GENRE_TV_POPULAR_ID = 2801;

    public static final int GENRE_TV_TOP_RATE = 2900;
    public static final int GENRE_TV_TOP_RATE_ID = 2901;

    public static final int GENRE_TV = 3000;
    public static final int GENRE_TV_ID = 3001;

    public static final int PEOPLE_CAST = 3100;
    public static final int PEOPLE_CAST_ID = 3101;

    public static final int PEOPLE_CREW = 3200;
    public static final int PEOPLE_CREW_ID = 3201;

    public static final int PEOPLE_CAST_TV = 3300;
    public static final int PEOPLE_CAST_TV_ID = 3301;

    public static final int PEOPLE_CREW_TV = 3400;
    public static final int PEOPLE_CREW_TV_ID = 3401;


    private MovieDBDatabaseHelper movieDBDatabaseHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher_ = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.MOVIE_DATA_PATH, MOVIE);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.MOVIE_DATA_PATH + "/#", MOVIE_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.POPULAR_DATA_PATH, POPULAR);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.POPULAR_DATA_PATH + "/#", POPULAR_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.TOP_RATE_DATA_PATH, TOP_RATE);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.TOP_RATE_DATA_PATH + "/#", TOP_RATE_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.NOW_SHOWING_DATA_PATH, NOW_SHOWING);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.NOW_SHOWING_DATA_PATH + "/#", NOW_SHOWING_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.COMING_SOON_DATA_PATH, COMING_SOON);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.COMING_SOON_DATA_PATH + "/#", COMING_SOON_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.FAVORITE_DATA_PATH, FAVORITE);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.FAVORITE_DATA_PATH + "/#", FAVORITE_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.WATCHLIST_DATA_PATH, WATCHLIST);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.WATCHLIST_DATA_PATH + "/#", WATCHLIST_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PLAN_TO_WATCH_LIST_DATA_PATH, PLAN_TO_WATCH);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PLAN_TO_WATCH_LIST_DATA_PATH + "/#", PLAN_TO_WATCH_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CREW_DATA_PATH, CREW);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CREW_DATA_PATH + "/#", CREW_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CAST_DATA_PATH, CAST);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CAST_DATA_PATH + "/#", CAST_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.REVIEW_DATA_PATH, REVIEW);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.REVIEW_DATA_PATH + "/#", REVIEW_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.VIDEO_DATA_PATH, VIDEO);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.VIDEO_DATA_PATH + "/#", VIDEO_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_DATA_PATH, GENRE);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_DATA_PATH + "/#", GENRE_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_MOVIE_POPULAR_DATA_PATH, GENRE_MOVIE_POPULAR);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_MOVIE_POPULAR_DATA_PATH + "/#", GENRE_MOVIE_POPULAR_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_MOVIE_TOP_RATE_DATA_PATH, GENRE_MOVIE_TOP_RATE);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_MOVIE_TOP_RATE_DATA_PATH + "/#", GENRE_MOVIE_TOP_RATE_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_DATA_PATH, PEOPLE);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_DATA_PATH + "/#", PEOPLE_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.TV_DATA_PATH, TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.TV_DATA_PATH + "/#", TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.POPULAR_TV__DATA_PATH, POPULAR_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.POPULAR_TV__DATA_PATH + "/#", POPULAR_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.TOP_RATE_TV__DATA_PATH, TOP_RATE_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.TOP_RATE_TV__DATA_PATH + "/#", TOP_RATE_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.AIR_TODAY_TV__DATA_PATH, AIR_TODAY_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.AIR_TODAY_TV__DATA_PATH + "/#", AIR_TODAY_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.ON_THE_AIR_TV__DATA_PATH, ON_THE_AIR_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.ON_THE_AIR_TV__DATA_PATH + "/#", ON_THE_AIR_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.FAVORITE_TV__DATA_PATH, FAVORITE_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.FAVORITE_TV__DATA_PATH + "/#", FAVORITE_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.WATCHLIST_TV__DATA_PATH, WATCHLIST_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.WATCHLIST_TV__DATA_PATH + "/#", WATCHLIST_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PLAN_TO_WATCH_LIST_TV__DATA_PATH, PLAN_TO_WATCH_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PLAN_TO_WATCH_LIST_TV__DATA_PATH + "/#", PLAN_TO_WATCH_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CREW_TV__DATA_PATH, CREW_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CREW_TV__DATA_PATH + "/#", CREW_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CAST_TV__DATA_PATH, CAST_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CAST_TV__DATA_PATH + "/#", CAST_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.VIDEO_TV__DATA_PATH, VIDEO_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.VIDEO_TV__DATA_PATH + "/#", VIDEO_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_TV_POPULAR_DATA_PATH, GENRE_TV_POPULAR);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_TV_POPULAR_DATA_PATH + "/#", GENRE_TV_POPULAR_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_TV_TOP_RATE_DATA_PATH, GENRE_TV_TOP_RATE);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_TV_TOP_RATE_DATA_PATH + "/#", GENRE_TV_TOP_RATE_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_TV_DATA_PATH, GENRE_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.GENRE_TV_DATA_PATH + "/#", GENRE_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_CREW_DATA_PATH, PEOPLE_CREW);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_CREW_DATA_PATH + "/#", PEOPLE_CREW_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_CAST_DATA_PATH, PEOPLE_CAST);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_CAST_DATA_PATH + "/#", PEOPLE_CAST_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_CREW_TV__DATA_PATH, PEOPLE_CREW_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_CREW_TV__DATA_PATH + "/#", PEOPLE_CREW_TV_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_CAST_TV__DATA_PATH, PEOPLE_CAST_TV);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.PEOPLE_CAST_TV__DATA_PATH + "/#", PEOPLE_CAST_TV_ID);

        return uriMatcher_;
    }
    @Override
    public boolean onCreate() {
        movieDBDatabaseHelper = new MovieDBDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase sqliteDatabase = movieDBDatabaseHelper.getReadableDatabase();

        int uriMatch = uriMatcher.match(uri);
        Cursor returnCursor;

        switch (uriMatch)
        {
            case MOVIE :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.MovieDataEntry.TABLE_MOVIE_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case POPULAR :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.PopularDataEntry.TABLE_POPULAR_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case TOP_RATE :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.TopRateDataEntry.TABLE_TOP_RATE_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case NOW_SHOWING :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.NowShowingDataEntry.TABLE_NOW_SHOWING_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case COMING_SOON :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.ComingSoonDataEntry.TABLE_COMING_SOON_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case FAVORITE :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.FavoriteDataEntry.TABLE_FAVORITE_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case WATCHLIST :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.WatchlistDataEntry.TABLE_WATCHLIST_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case PLAN_TO_WATCH :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.PlanToWatchlistDataEntry.TABLE_PLAN_TO_WATCH_LIST_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case CREW :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.CrewDataEntry.TABLE_CREW_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case CAST :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.CastDataEntry.TABLE_CAST_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case REVIEW :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.ReviewDataEntry.TABLE_REVIEW_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case VIDEO :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.VideoDataEntry.TABLE_VIDEO_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);

                break;

            case GENRE :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.GenreDataEntry.TABLE_GENRE_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case GENRE_MOVIE_POPULAR :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.GenreMoviePopularEntry.TABLE_GENRE_MOVIE_POPULAR_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case GENRE_MOVIE_TOP_RATE :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.GenreMovieTopRateEntry.TABLE_GENRE_MOVIE_TOP_RATE_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case PEOPLE :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.PeopleDataEntry.TABLE_PEOPLE_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.TVDataEntry.TABLE_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case POPULAR_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.PopularTVDataEntry.TABLE_POPULAR_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case TOP_RATE_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.TopRateTVDataEntry.TABLE_TOP_RATE_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case AIR_TODAY_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.AirTodayTVDataEntry.TABLE_AIR_TODAY_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case ON_THE_AIR_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.OnTheAirTVDataEntry.TABLE_ON_THE_AIR_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case FAVORITE_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.FavoriteTVDataEntry.TABLE_FAVORITE_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case WATCHLIST_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.WatchlistTVDataEntry.TABLE_WATCHLIST_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case PLAN_TO_WATCH_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.PlanToWatchlistTVDataEntry.TABLE_PLAN_TO_WATCH_LIST_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case CREW_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.CrewTVDataEntry.TABLE_CREW_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case CAST_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.CastTVDataEntry.TABLE_CAST_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case VIDEO_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.VideoTVDataEntry.TABLE_VIDEO_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);

                break;

            case GENRE_TV_POPULAR :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.GenreTVPopularEntry.TABLE_GENRE_TV_POPULAR_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case GENRE_TV_TOP_RATE :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.GenreTVTopRateEntry.TABLE_GENRE_TV_TOP_RATE_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case GENRE_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.GenreTVDataEntry.TABLE_GENRE_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case PEOPLE_CREW :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.PeopleCrewDataEntry.TABLE_PEOPLE_CREW_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case PEOPLE_CAST :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.PeopleCastDataEntry.TABLE_PEOPLE_CAST_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case PEOPLE_CREW_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.PeopleCrewTVDataEntry.TABLE_PEOPLE_CREW_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case PEOPLE_CAST_TV :
                returnCursor = sqliteDatabase.query
                        (MovieDBContract.PeopleCastTVDataEntry.TABLE_PEOPLE_CAST_TV_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;


            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase sqliteDatabase = movieDBDatabaseHelper.getWritableDatabase();

        int uriMatch = uriMatcher.match(uri);
        Uri returnUri;
        long id;

        switch (uriMatch)
        {
            case MOVIE :
                id = sqliteDatabase.insert(
                        MovieDBContract.MovieDataEntry.TABLE_MOVIE_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.MovieDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case POPULAR :
                id = sqliteDatabase.insert(
                        MovieDBContract.PopularDataEntry.TABLE_POPULAR_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.PopularDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case TOP_RATE :
                id = sqliteDatabase.insert(
                        MovieDBContract.TopRateDataEntry.TABLE_TOP_RATE_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.TopRateDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case NOW_SHOWING :
                id = sqliteDatabase.insert(
                        MovieDBContract.NowShowingDataEntry.TABLE_NOW_SHOWING_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.NowShowingDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case COMING_SOON :
                id = sqliteDatabase.insert(
                        MovieDBContract.ComingSoonDataEntry.TABLE_COMING_SOON_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.ComingSoonDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case FAVORITE :
                id = sqliteDatabase.insert(
                        MovieDBContract.FavoriteDataEntry.TABLE_FAVORITE_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.FavoriteDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case WATCHLIST :
                id = sqliteDatabase.insert(
                        MovieDBContract.WatchlistDataEntry.TABLE_WATCHLIST_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.WatchlistDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case PLAN_TO_WATCH :
                id = sqliteDatabase.insert(
                        MovieDBContract.PlanToWatchlistDataEntry.TABLE_PLAN_TO_WATCH_LIST_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.PlanToWatchlistDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case CREW :
                id = sqliteDatabase.insert(
                        MovieDBContract.CrewDataEntry.TABLE_CREW_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.CrewDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case CAST :
                id = sqliteDatabase.insert(
                        MovieDBContract.CastDataEntry.TABLE_CAST_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.CastDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case REVIEW :
                id = sqliteDatabase.insert(
                        MovieDBContract.ReviewDataEntry.TABLE_REVIEW_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.ReviewDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case VIDEO :
                id = sqliteDatabase.insert(
                        MovieDBContract.VideoDataEntry.TABLE_VIDEO_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.VideoDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case GENRE :
                id = sqliteDatabase.insert(
                        MovieDBContract.GenreDataEntry.TABLE_GENRE_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.GenreDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case GENRE_MOVIE_POPULAR :
                id = sqliteDatabase.insert(
                        MovieDBContract.GenreMoviePopularEntry.TABLE_GENRE_MOVIE_POPULAR_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.GenreMoviePopularEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case GENRE_MOVIE_TOP_RATE :
                id = sqliteDatabase.insert(
                        MovieDBContract.GenreMovieTopRateEntry.TABLE_GENRE_MOVIE_TOP_RATE_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.GenreMovieTopRateEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case PEOPLE :
                id = sqliteDatabase.insert(
                        MovieDBContract.PeopleDataEntry.TABLE_PEOPLE_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.PeopleDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.TVDataEntry.TABLE_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.TVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case POPULAR_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.PopularTVDataEntry.TABLE_POPULAR_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.PopularTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case TOP_RATE_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.TopRateTVDataEntry.TABLE_TOP_RATE_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.TopRateTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case AIR_TODAY_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.AirTodayTVDataEntry.TABLE_AIR_TODAY_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.AirTodayTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case ON_THE_AIR_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.OnTheAirTVDataEntry.TABLE_ON_THE_AIR_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.OnTheAirTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case FAVORITE_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.FavoriteTVDataEntry.TABLE_FAVORITE_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.FavoriteTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case WATCHLIST_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.WatchlistTVDataEntry.TABLE_WATCHLIST_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.WatchlistTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case PLAN_TO_WATCH_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.PlanToWatchlistTVDataEntry.TABLE_PLAN_TO_WATCH_LIST_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.PlanToWatchlistTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case CREW_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.CrewTVDataEntry.TABLE_CREW_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.CrewTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case CAST_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.CastTVDataEntry.TABLE_CAST_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.CastTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case VIDEO_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.VideoTVDataEntry.TABLE_VIDEO_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.VideoTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case GENRE_TV_POPULAR :
                id = sqliteDatabase.insert(
                        MovieDBContract.GenreTVPopularEntry.TABLE_GENRE_TV_POPULAR_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.GenreTVPopularEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case GENRE_TV_TOP_RATE :
                id = sqliteDatabase.insert(
                        MovieDBContract.GenreTVTopRateEntry.TABLE_GENRE_TV_TOP_RATE_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.GenreTVTopRateEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case GENRE_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.GenreTVDataEntry.TABLE_GENRE_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.GenreTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case PEOPLE_CREW :
                id = sqliteDatabase.insert(
                        MovieDBContract.PeopleCrewDataEntry.TABLE_PEOPLE_CREW_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.PeopleCrewDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case PEOPLE_CAST :
                id = sqliteDatabase.insert(
                        MovieDBContract.PeopleCastDataEntry.TABLE_PEOPLE_CAST_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.PeopleCastDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case PEOPLE_CREW_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.PeopleCrewTVDataEntry.TABLE_PEOPLE_CREW_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.PeopleCrewTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case PEOPLE_CAST_TV :
                id = sqliteDatabase.insert(
                        MovieDBContract.PeopleCastTVDataEntry.TABLE_PEOPLE_CAST_TV_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            MovieDBContract.PeopleCastTVDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase sqliteDatabase = movieDBDatabaseHelper.getWritableDatabase();
        int uriMatch = uriMatcher.match(uri);

        int dataDeleted = 0;
        String idData;

        switch (uriMatch)
        {
            case MOVIE :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.MovieDataEntry.TABLE_MOVIE_DATA,
                        "1", null);
                break;

            case MOVIE_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.MovieDataEntry.TABLE_MOVIE_DATA,
                        MovieDBContract.MovieDataEntry._ID + "=?", new String[]{ idData });
                break;

            case POPULAR :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PopularDataEntry.TABLE_POPULAR_DATA,
                        "1", null);
                break;

            case POPULAR_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PopularDataEntry.TABLE_POPULAR_DATA,
                        MovieDBContract.PopularDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case TOP_RATE :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.TopRateDataEntry.TABLE_TOP_RATE_DATA,
                        "1", null);
                break;

            case TOP_RATE_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.TopRateDataEntry.TABLE_TOP_RATE_DATA,
                        MovieDBContract.TopRateDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case NOW_SHOWING :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.NowShowingDataEntry.TABLE_NOW_SHOWING_DATA,
                        "1", null);
                break;

            case NOW_SHOWING_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.NowShowingDataEntry.TABLE_NOW_SHOWING_DATA,
                        MovieDBContract.NowShowingDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case COMING_SOON :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.ComingSoonDataEntry.TABLE_COMING_SOON_DATA,
                        "1", null);
                break;

            case COMING_SOON_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.ComingSoonDataEntry.TABLE_COMING_SOON_DATA,
                        MovieDBContract.ComingSoonDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case FAVORITE :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.FavoriteDataEntry.TABLE_FAVORITE_DATA,
                        "1", null);
                break;

            case FAVORITE_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.FavoriteDataEntry.TABLE_FAVORITE_DATA,
                        MovieDBContract.FavoriteDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case WATCHLIST :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.WatchlistDataEntry.TABLE_WATCHLIST_DATA,
                        "1", null);
                break;

            case WATCHLIST_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.WatchlistDataEntry.TABLE_WATCHLIST_DATA,
                        MovieDBContract.WatchlistDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case PLAN_TO_WATCH :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PlanToWatchlistDataEntry.TABLE_PLAN_TO_WATCH_LIST_DATA,
                        "1", null);
                break;

            case PLAN_TO_WATCH_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PlanToWatchlistDataEntry.TABLE_PLAN_TO_WATCH_LIST_DATA,
                        MovieDBContract.PlanToWatchlistDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case CREW :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.CrewDataEntry.TABLE_CREW_DATA,
                        "1", null);
                break;

            case CREW_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.CrewDataEntry.TABLE_CREW_DATA,
                        MovieDBContract.CrewDataEntry._ID + "=?", new String[]{ idData });
                break;

            case CAST :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.CastDataEntry.TABLE_CAST_DATA,
                        "1", null);
                break;

            case CAST_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.CastDataEntry.TABLE_CAST_DATA,
                        MovieDBContract.CastDataEntry._ID + "=?", new String[]{ idData });
                break;

            case REVIEW :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.ReviewDataEntry.TABLE_REVIEW_DATA,
                        "1", null);
                break;

            case REVIEW_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.ReviewDataEntry.TABLE_REVIEW_DATA,
                        MovieDBContract.ReviewDataEntry._ID + "=?", new String[]{ idData });
                break;

            case VIDEO :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.VideoDataEntry.TABLE_VIDEO_DATA,
                        "1", null);
                break;

            case VIDEO_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.VideoDataEntry.TABLE_VIDEO_DATA,
                        MovieDBContract.VideoDataEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreDataEntry.TABLE_GENRE_DATA,
                        "1", null);
                break;

            case GENRE_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreDataEntry.TABLE_GENRE_DATA,
                        MovieDBContract.GenreDataEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_MOVIE_POPULAR :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreMoviePopularEntry.TABLE_GENRE_MOVIE_POPULAR_DATA,
                        "1", null);
                break;

            case GENRE_MOVIE_POPULAR_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreMoviePopularEntry.TABLE_GENRE_MOVIE_POPULAR_DATA,
                        MovieDBContract.GenreMoviePopularEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_MOVIE_TOP_RATE :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreMovieTopRateEntry.TABLE_GENRE_MOVIE_TOP_RATE_DATA,
                        "1", null);
                break;

            case GENRE_MOVIE_TOP_RATE_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreMovieTopRateEntry.TABLE_GENRE_MOVIE_TOP_RATE_DATA,
                        MovieDBContract.GenreMovieTopRateEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleDataEntry.TABLE_PEOPLE_DATA,
                        "1", null);
                break;

            case PEOPLE_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleDataEntry.TABLE_PEOPLE_DATA,
                        MovieDBContract.PeopleDataEntry._ID + "=?", new String[]{ idData });
                break;

            case TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.TVDataEntry.TABLE_TV_DATA,
                        "1", null);
                break;

            case TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.TVDataEntry.TABLE_TV_DATA,
                        MovieDBContract.TVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case POPULAR_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PopularTVDataEntry.TABLE_POPULAR_TV_DATA,
                        "1", null);
                break;

            case POPULAR_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PopularTVDataEntry.TABLE_POPULAR_TV_DATA,
                        MovieDBContract.PopularTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case TOP_RATE_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.TopRateTVDataEntry.TABLE_TOP_RATE_TV_DATA,
                        "1", null);
                break;

            case TOP_RATE_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.TopRateTVDataEntry.TABLE_TOP_RATE_TV_DATA,
                        MovieDBContract.TopRateTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case AIR_TODAY_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.AirTodayTVDataEntry.TABLE_AIR_TODAY_TV_DATA,
                        "1", null);
                break;

            case AIR_TODAY_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.AirTodayTVDataEntry.TABLE_AIR_TODAY_TV_DATA,
                        MovieDBContract.AirTodayTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case ON_THE_AIR_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.OnTheAirTVDataEntry.TABLE_ON_THE_AIR_TV_DATA,
                        "1", null);
                break;

            case ON_THE_AIR_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.OnTheAirTVDataEntry.TABLE_ON_THE_AIR_TV_DATA,
                        MovieDBContract.OnTheAirTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case FAVORITE_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.FavoriteTVDataEntry.TABLE_FAVORITE_TV_DATA,
                        "1", null);
                break;

            case FAVORITE_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.FavoriteTVDataEntry.TABLE_FAVORITE_TV_DATA,
                        MovieDBContract.FavoriteTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case WATCHLIST_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.WatchlistTVDataEntry.TABLE_WATCHLIST_TV_DATA,
                        "1", null);
                break;

            case WATCHLIST_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.WatchlistTVDataEntry.TABLE_WATCHLIST_TV_DATA,
                        MovieDBContract.WatchlistTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case PLAN_TO_WATCH_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PlanToWatchlistTVDataEntry.TABLE_PLAN_TO_WATCH_LIST_TV_DATA,
                        "1", null);
                break;

            case PLAN_TO_WATCH_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PlanToWatchlistTVDataEntry.TABLE_PLAN_TO_WATCH_LIST_TV_DATA,
                        MovieDBContract.PlanToWatchlistTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case CREW_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.CrewTVDataEntry.TABLE_CREW_TV_DATA,
                        "1", null);
                break;

            case CREW_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.CrewTVDataEntry.TABLE_CREW_TV_DATA,
                        MovieDBContract.CrewTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case CAST_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.CastTVDataEntry.TABLE_CAST_TV_DATA,
                        "1", null);
                break;

            case CAST_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.CastTVDataEntry.TABLE_CAST_TV_DATA,
                        MovieDBContract.CastTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case VIDEO_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.VideoTVDataEntry.TABLE_VIDEO_TV_DATA,
                        "1", null);
                break;

            case VIDEO_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.VideoTVDataEntry.TABLE_VIDEO_TV_DATA,
                        MovieDBContract.VideoTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_TV_POPULAR :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreTVPopularEntry.TABLE_GENRE_TV_POPULAR_DATA,
                        "1", null);
                break;

            case GENRE_TV_POPULAR_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreTVPopularEntry.TABLE_GENRE_TV_POPULAR_DATA,
                        MovieDBContract.GenreTVPopularEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_TV_TOP_RATE :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreTVTopRateEntry.TABLE_GENRE_TV_TOP_RATE_DATA,
                        "1", null);
                break;

            case GENRE_TV_TOP_RATE_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreTVTopRateEntry.TABLE_GENRE_TV_TOP_RATE_DATA,
                        MovieDBContract.GenreTVTopRateEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_TV :
            dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreTVDataEntry.TABLE_GENRE_TV_DATA,
                    "1", null);
            break;

            case GENRE_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.GenreTVDataEntry.TABLE_GENRE_TV_DATA,
                        MovieDBContract.GenreTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE_CAST :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleDataEntry.TABLE_PEOPLE_DATA,
                        "1", null);
                break;

            case PEOPLE_CAST_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleDataEntry.TABLE_PEOPLE_DATA,
                        MovieDBContract.PeopleDataEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE_CREW :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleCrewDataEntry.TABLE_PEOPLE_CREW_DATA,
                        "1", null);
                break;

            case PEOPLE_CREW_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleCrewDataEntry.TABLE_PEOPLE_CREW_DATA,
                        MovieDBContract.PeopleCrewDataEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE_CAST_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleCastTVDataEntry.TABLE_PEOPLE_CAST_TV_DATA,
                        "1", null);
                break;

            case PEOPLE_CAST_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleCastTVDataEntry.TABLE_PEOPLE_CAST_TV_DATA,
                        MovieDBContract.PeopleCastTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE_CREW_TV :
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleCrewTVDataEntry.TABLE_PEOPLE_CREW_TV_DATA,
                        "1", null);
                break;

            case PEOPLE_CREW_TV_ID:
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(MovieDBContract.PeopleCrewTVDataEntry.TABLE_PEOPLE_CREW_TV_DATA,
                        MovieDBContract.PeopleCrewTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        if(dataDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return dataDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqliteDatabase = movieDBDatabaseHelper.getWritableDatabase();
        int uriMatch = uriMatcher.match(uri);

        int dataUpdated = 0;
        String idData;

        switch (uriMatch)
        {
            case MOVIE_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.MovieDataEntry.TABLE_MOVIE_DATA,
                        values, MovieDBContract.MovieDataEntry._ID + "=?", new String[]{ idData });
                break;

            case POPULAR_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.PopularDataEntry.TABLE_POPULAR_DATA,
                        values, MovieDBContract.PopularDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case TOP_RATE_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.TopRateDataEntry.TABLE_TOP_RATE_DATA,
                        values, MovieDBContract.TopRateDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case NOW_SHOWING_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.NowShowingDataEntry.TABLE_NOW_SHOWING_DATA,
                        values, MovieDBContract.NowShowingDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case COMING_SOON_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.ComingSoonDataEntry.TABLE_COMING_SOON_DATA,
                        values, MovieDBContract.ComingSoonDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case FAVORITE_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.FavoriteDataEntry.TABLE_FAVORITE_DATA,
                        values, MovieDBContract.FavoriteDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case WATCHLIST_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.WatchlistDataEntry.TABLE_WATCHLIST_DATA,
                        values, MovieDBContract.WatchlistDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case PLAN_TO_WATCH_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.PlanToWatchlistDataEntry.TABLE_PLAN_TO_WATCH_LIST_DATA,
                        values, MovieDBContract.PlanToWatchlistDataEntry.COLUMN_MOVIE_ID + "=?", new String[]{ idData });
                break;

            case CAST_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.CastDataEntry.TABLE_CAST_DATA,
                        values, MovieDBContract.CastDataEntry._ID + "=?", new String[]{ idData });
                break;

            case CREW_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.CrewDataEntry.TABLE_CREW_DATA,
                        values, MovieDBContract.CrewDataEntry._ID + "=?", new String[]{ idData });
                break;

            case REVIEW_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.ReviewDataEntry.TABLE_REVIEW_DATA,
                        values, MovieDBContract.ReviewDataEntry._ID + "=?", new String[]{ idData });
                break;

            case VIDEO_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.VideoDataEntry.TABLE_VIDEO_DATA,
                        values, MovieDBContract.VideoDataEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.GenreDataEntry.TABLE_GENRE_DATA,
                        values, MovieDBContract.GenreDataEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_MOVIE_POPULAR_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.GenreMoviePopularEntry.TABLE_GENRE_MOVIE_POPULAR_DATA,
                        values, MovieDBContract.GenreMoviePopularEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_MOVIE_TOP_RATE_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.GenreMovieTopRateEntry.TABLE_GENRE_MOVIE_TOP_RATE_DATA,
                        values, MovieDBContract.GenreMovieTopRateEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.PeopleDataEntry.TABLE_PEOPLE_DATA,
                        values, MovieDBContract.PeopleDataEntry._ID + "=?", new String[]{ idData });
                break;

            case TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.TVDataEntry.TABLE_TV_DATA,
                        values, MovieDBContract.TVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case POPULAR_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.PopularTVDataEntry.TABLE_POPULAR_TV_DATA,
                        values, MovieDBContract.PopularTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case TOP_RATE_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.TopRateTVDataEntry.TABLE_TOP_RATE_TV_DATA,
                        values, MovieDBContract.TopRateTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case AIR_TODAY_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.AirTodayTVDataEntry.TABLE_AIR_TODAY_TV_DATA,
                        values, MovieDBContract.AirTodayTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case ON_THE_AIR_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.OnTheAirTVDataEntry.TABLE_ON_THE_AIR_TV_DATA,
                        values, MovieDBContract.OnTheAirTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case FAVORITE_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.FavoriteTVDataEntry.TABLE_FAVORITE_TV_DATA,
                        values, MovieDBContract.FavoriteTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case WATCHLIST_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.WatchlistTVDataEntry.TABLE_WATCHLIST_TV_DATA,
                        values, MovieDBContract.WatchlistTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case PLAN_TO_WATCH_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.PlanToWatchlistTVDataEntry.TABLE_PLAN_TO_WATCH_LIST_TV_DATA,
                        values, MovieDBContract.PlanToWatchlistTVDataEntry.COLUMN_TV_ID + "=?", new String[]{ idData });
                break;

            case CAST_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.CastTVDataEntry.TABLE_CAST_TV_DATA,
                        values, MovieDBContract.CastTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case CREW_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.CrewTVDataEntry.TABLE_CREW_TV_DATA,
                        values, MovieDBContract.CrewTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case VIDEO_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.VideoTVDataEntry.TABLE_VIDEO_TV_DATA,
                        values, MovieDBContract.VideoTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_TV_POPULAR_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.GenreTVPopularEntry.TABLE_GENRE_TV_POPULAR_DATA,
                        values, MovieDBContract.GenreTVPopularEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_TV_TOP_RATE_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.GenreTVTopRateEntry.TABLE_GENRE_TV_TOP_RATE_DATA,
                        values, MovieDBContract.GenreTVTopRateEntry._ID + "=?", new String[]{ idData });
                break;

            case GENRE_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.GenreTVDataEntry.TABLE_GENRE_TV_DATA,
                        values, MovieDBContract.GenreTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE_CAST_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.PeopleCastDataEntry.TABLE_PEOPLE_CAST_DATA,
                        values, MovieDBContract.PeopleCastDataEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE_CREW_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.PeopleCrewDataEntry.TABLE_PEOPLE_CREW_DATA,
                        values, MovieDBContract.PeopleCrewDataEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE_CAST_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.PeopleCastTVDataEntry.TABLE_PEOPLE_CAST_TV_DATA,
                        values, MovieDBContract.PeopleCastTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            case PEOPLE_CREW_TV_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(MovieDBContract.PeopleCrewTVDataEntry.TABLE_PEOPLE_CREW_TV_DATA,
                        values, MovieDBContract.PeopleCrewTVDataEntry._ID + "=?", new String[]{ idData });
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        if(dataUpdated != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return dataUpdated;
    }
}
