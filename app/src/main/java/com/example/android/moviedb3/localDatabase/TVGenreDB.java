package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.TVGenre;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVGenreDB extends DataDB<TVGenre>
{
    public TVGenreDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<TVGenre> getAllData() {

        ArrayList<TVGenre> genreDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.GenreTVDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.GenreTVDataEntry._ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreTVDataEntry._ID));
            String genreName = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreTVDataEntry.COLUMN_GENRE_NAME));

            TVGenre genreData = new TVGenre(id, genreName);
            genreDataArrayList.add(genreData);

        } while (cursor.moveToNext());

        cursor.close();

        return genreDataArrayList;
    }

    @Override
    public void addData(TVGenre tvGenre) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.GenreTVDataEntry._ID, tvGenre.getId());
        contentValues.put(MovieDBContract.GenreTVDataEntry.COLUMN_GENRE_NAME, tvGenre.getName());

        context.getContentResolver().insert(MovieDBContract.GenreTVDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.GenreTVDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.GenreTVDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}
