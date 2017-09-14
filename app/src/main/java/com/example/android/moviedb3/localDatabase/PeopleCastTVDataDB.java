package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.PeopleCastData;
import com.example.android.moviedb3.movieDB.PeopleCastTvData;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleCastTVDataDB extends DataDB<PeopleCastTvData>
{
    public PeopleCastTVDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<PeopleCastTvData> getAllData() {

        ArrayList<PeopleCastTvData> castDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.PeopleCastTVDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.PeopleCastTVDataEntry.COLUMN_TV_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastTVDataEntry._ID));
            String tvName = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastTVDataEntry.COLUMN_TV_NAME));
            String characterName = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastTVDataEntry.COLUMN_CHARACTER_NAME));
            String tvID = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastTVDataEntry.COLUMN_TV_ID));
            String peopleID = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastTVDataEntry.COLUMN_PEOPLE_ID));
            String image = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastTVDataEntry.COLUMN_IMAGE));

            PeopleCastTvData peopleCastTvData = new PeopleCastTvData(id, tvName, characterName, tvID, peopleID, image);
            castDataArrayList.add(peopleCastTvData);

        } while (cursor.moveToNext());

        cursor.close();

        return castDataArrayList;
    }

    @Override
    public void addData(PeopleCastTvData peopleCastTvData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.PeopleCastTVDataEntry._ID, peopleCastTvData.getId());
        contentValues.put(MovieDBContract.PeopleCastTVDataEntry.COLUMN_TV_NAME, peopleCastTvData.getMovieName());
        contentValues.put(MovieDBContract.PeopleCastTVDataEntry.COLUMN_CHARACTER_NAME, peopleCastTvData.getCharacterName());
        contentValues.put(MovieDBContract.PeopleCastTVDataEntry.COLUMN_TV_ID, peopleCastTvData.getTvID());
        contentValues.put(MovieDBContract.PeopleCastTVDataEntry.COLUMN_PEOPLE_ID, peopleCastTvData.getPeopleID());
        contentValues.put(MovieDBContract.PeopleCastTVDataEntry.COLUMN_IMAGE, peopleCastTvData.getImageCast());

        context.getContentResolver().insert(MovieDBContract.PeopleCastTVDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.PeopleCastTVDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.PeopleCastTVDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}