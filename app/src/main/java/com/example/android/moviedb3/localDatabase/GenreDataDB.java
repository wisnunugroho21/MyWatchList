package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.GenreData;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreDataDB extends DataDB<GenreData>
{
    public GenreDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<GenreData> getAllData() {

        ArrayList<GenreData> genreDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.GenreDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.GenreDataEntry._ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreDataEntry._ID));
            String genreName = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreDataEntry.COLUMN_GENRE_NAME));

            GenreData genreData = new GenreData(id, genreName);
            genreDataArrayList.add(genreData);

        } while (cursor.moveToNext());

        cursor.close();

        return genreDataArrayList;
    }

    @Override
    public void addData(GenreData genreData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.GenreDataEntry._ID, genreData.getId());
        contentValues.put(MovieDBContract.GenreDataEntry.COLUMN_GENRE_NAME, genreData.getName());

        context.getContentResolver().insert(MovieDBContract.GenreDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.GenreDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.GenreDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}
