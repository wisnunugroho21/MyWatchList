package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;

import java.util.ArrayList;

/**
 * Created by nugroho on 27/07/17.
 */

public class WatchlistDataDB extends DataDB<String>
{
    public WatchlistDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<String> getAllData()
    {
        ArrayList<String> movieIDList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.WatchlistDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.WatchlistDataEntry.COLUMN_MOVIE_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.WatchlistDataEntry.COLUMN_MOVIE_ID));
            movieIDList.add(movieID);

        } while (cursor.moveToNext());

        cursor.close();

        return movieIDList;
    }

    @Override
    public void addData(String s) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieDBContract.WatchlistDataEntry.COLUMN_MOVIE_ID, s);

        context.getContentResolver().insert(MovieDBContract.WatchlistDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.WatchlistDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.WatchlistDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}
