package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.GenreMovieData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */


public class GenreMovieTopRateDB extends DataDB<GenreMovieData>
{
    public GenreMovieTopRateDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<GenreMovieData> getAllData() {

        ArrayList<GenreMovieData> genreMovieDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.GenreMovieTopRateEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.GenreMovieTopRateEntry._ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreMovieTopRateEntry._ID));
            String genreID = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreMovieTopRateEntry.COLUMN_GENRE_ID));
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreMovieTopRateEntry.COLUMN_MOVIE_ID));

            GenreMovieData genreMovieData = new GenreMovieData(id, movieID, genreID);
            genreMovieDataArrayList.add(genreMovieData);

        } while (cursor.moveToNext());

        cursor.close();

        return genreMovieDataArrayList;
    }

    @Override
    public void addData(GenreMovieData genreMovieData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.GenreMovieTopRateEntry._ID, genreMovieData.getId());
        contentValues.put(MovieDBContract.GenreMovieTopRateEntry.COLUMN_GENRE_ID, genreMovieData.getIdGenre());
        contentValues.put(MovieDBContract.GenreMovieTopRateEntry.COLUMN_MOVIE_ID, genreMovieData.getIdMovie());

        context.getContentResolver().insert(MovieDBContract.GenreMovieTopRateEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.GenreMovieTopRateEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.GenreMovieTopRateEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}

