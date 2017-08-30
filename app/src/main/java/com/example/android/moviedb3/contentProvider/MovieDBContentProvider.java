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

    public static final int FAVORITE = 400;
    public static final int FAVORITE_ID = 401;

    public static final int WATCHLIST = 500;
    public static final int WATCHLIST_ID = 501;

    public static final int CAST = 600;
    public static final int CAST_ID = 601;

    public static final int CREW = 700;
    public static final int CREW_ID = 701;

    public static final int REVIEW = 800;
    public static final int REVIEW_ID = 801;

    public static final int VIDEO = 900;
    public static final int VIDEO_ID = 901;

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

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.FAVORITE_DATA_PATH, FAVORITE);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.FAVORITE_DATA_PATH + "/#", FAVORITE_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.WATCHLIST_DATA_PATH, WATCHLIST);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.WATCHLIST_DATA_PATH + "/#", WATCHLIST_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CREW_DATA_PATH, CREW);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CREW_DATA_PATH + "/#", CREW_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CAST_DATA_PATH, CAST);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.CAST_DATA_PATH + "/#", CAST_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.REVIEW_DATA_PATH, REVIEW);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.REVIEW_DATA_PATH + "/#", REVIEW_ID);

        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.VIDEO_DATA_PATH, VIDEO);
        uriMatcher_.addURI(MovieDBContract.AUTHORITY, MovieDBContract.VIDEO_DATA_PATH + "/#", VIDEO_ID);

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
