package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.CastTVData;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class CastTVDataDB extends DataDB<CastTVData>
{
    public CastTVDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<CastTVData> getAllData() {

        ArrayList<CastTVData> castDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.CastTVDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.CastTVDataEntry.COLUMN_TV_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastTVDataEntry._ID));
            String castName = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastTVDataEntry.COLUMN_CAST_NAME));
            String characterName = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastTVDataEntry.COLUMN_CHARACTER_NAME));
            String tvID = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastTVDataEntry.COLUMN_TV_ID));
            String peopleID = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastTVDataEntry.COLUMN_PEOPLE_ID));
            String image = cursor.getString(cursor.getColumnIndex(MovieDBContract.CastTVDataEntry.COLUMN_IMAGE));

            CastTVData castData = new CastTVData(id, castName, characterName, tvID, peopleID, image);
            castDataArrayList.add(castData);

        } while (cursor.moveToNext());

        cursor.close();

        return castDataArrayList;
    }

    @Override
    public void addData(CastTVData castTVData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.CastTVDataEntry._ID, castTVData.getId());
        contentValues.put(MovieDBContract.CastTVDataEntry.COLUMN_CAST_NAME, castTVData.getCastName());
        contentValues.put(MovieDBContract.CastTVDataEntry.COLUMN_CHARACTER_NAME, castTVData.getCharacterName());
        contentValues.put(MovieDBContract.CastTVDataEntry.COLUMN_TV_ID, castTVData.getTvID());
        contentValues.put(MovieDBContract.CastTVDataEntry.COLUMN_PEOPLE_ID, castTVData.getPeopleID());
        contentValues.put(MovieDBContract.CastTVDataEntry.COLUMN_IMAGE, castTVData.getImageCast());

        context.getContentResolver().insert(MovieDBContract.CastTVDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.CastTVDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.CastTVDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}


