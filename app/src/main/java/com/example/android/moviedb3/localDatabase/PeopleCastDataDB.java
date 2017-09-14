package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.PeopleCastData;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleCastDataDB extends DataDB<PeopleCastData>
{
    public PeopleCastDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<PeopleCastData> getAllData() {

        ArrayList<PeopleCastData> castDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.PeopleCastDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.PeopleCastDataEntry.COLUMN_MOVIE_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastDataEntry._ID));
            String movieName = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastDataEntry.COLUMN_MOVIE_NAME));
            String characterName = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastDataEntry.COLUMN_CHARACTER_NAME));
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastDataEntry.COLUMN_MOVIE_ID));
            String peopleID = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastDataEntry.COLUMN_PEOPLE_ID));
            String image = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCastDataEntry.COLUMN_IMAGE));

            PeopleCastData peopleCastData = new PeopleCastData(id, movieName, characterName, movieID, peopleID, image);
            castDataArrayList.add(peopleCastData);

        } while (cursor.moveToNext());

        cursor.close();

        return castDataArrayList;
    }

    @Override
    public void addData(PeopleCastData peopleCastData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.PeopleCastDataEntry._ID, peopleCastData.getId());
        contentValues.put(MovieDBContract.PeopleCastDataEntry.COLUMN_MOVIE_NAME, peopleCastData.getMovieName());
        contentValues.put(MovieDBContract.PeopleCastDataEntry.COLUMN_CHARACTER_NAME, peopleCastData.getCharacterName());
        contentValues.put(MovieDBContract.PeopleCastDataEntry.COLUMN_MOVIE_ID, peopleCastData.getMovieID());
        contentValues.put(MovieDBContract.PeopleCastDataEntry.COLUMN_PEOPLE_ID, peopleCastData.getPeopleID());
        contentValues.put(MovieDBContract.PeopleCastDataEntry.COLUMN_IMAGE, peopleCastData.getImageCast());

        context.getContentResolver().insert(MovieDBContract.PeopleCastDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.PeopleCastDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.PeopleCastDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}

