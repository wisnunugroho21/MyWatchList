package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class WatchlistTvDataDB extends DataDB<String>
{
    public WatchlistTvDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<String> getAllData() {

        ArrayList<String> tvIDList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.WatchlistTVDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.WatchlistTVDataEntry.COLUMN_TV_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String tvID = cursor.getString(cursor.getColumnIndex(MovieDBContract.WatchlistTVDataEntry.COLUMN_TV_ID));
            tvIDList.add(tvID);

        } while (cursor.moveToNext());

        cursor.close();

        return tvIDList;
    }

    @Override
    public void addData(String s) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieDBContract.WatchlistTVDataEntry.COLUMN_TV_ID, s);

        context.getContentResolver().insert(MovieDBContract.WatchlistTVDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.WatchlistTVDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.WatchlistTVDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}


