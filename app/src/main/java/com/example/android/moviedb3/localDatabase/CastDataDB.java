package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.CastData;

import java.util.ArrayList;

/**
 * Created by nugroho on 23/08/17.
 */

public class CastDataDB extends DataDB<CastData>
{
    public CastDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<CastData> getAllData() {

        ArrayList<CastData> castDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.CastDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.CastDataEntry.COLUMN_MOVIE_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastDataEntry._ID));
            String castName = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastDataEntry.COLUMN_CAST_NAME));
            String characterName = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastDataEntry.COLUMN_CHARACTER_NAME));
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastDataEntry.COLUMN_MOVIE_ID));
            String peopleID = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastDataEntry.COLUMN_PEOPLE_ID));

            CastData castData = new CastData(id, castName, characterName, movieID, peopleID);
            castDataArrayList.add(castData);

        } while (cursor.moveToNext());

        cursor.close();

        return castDataArrayList;
    }

    @Override
    public void addData(CastData castData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.CastDataEntry._ID, castData.getId());
        contentValues.put(MovieDBContract.CastDataEntry.COLUMN_CAST_NAME, castData.getCastName());
        contentValues.put(MovieDBContract.CastDataEntry.COLUMN_CHARACTER_NAME, castData.getCharacterName());
        contentValues.put(MovieDBContract.CastDataEntry.COLUMN_MOVIE_ID, castData.getMovieID());
        contentValues.put(MovieDBContract.CastDataEntry.COLUMN_PEOPLE_ID, castData.getPeopleID());

        context.getContentResolver().insert(MovieDBContract.CastDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.CastDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.CastDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}

