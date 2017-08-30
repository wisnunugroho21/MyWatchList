package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CrewData;

import java.util.ArrayList;

/**
 * Created by nugroho on 23/08/17.
 */

public class CrewDataDB extends DataDB<CrewData>
{
    String crewName;
    String crewPosition;
    int movieID;

    public CrewDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<CrewData> getAllData() {

        ArrayList<CrewData> crewDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.CrewDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.CrewDataEntry.COLUMN_MOVIE_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.CrewDataEntry._ID));
            String crewName = cursor.getString(cursor.getColumnIndex(MovieDBContract.CrewDataEntry.COLUMN_CREW_NAME));
            String crewPosition = cursor.getString(cursor.getColumnIndex(MovieDBContract.CrewDataEntry.COLUMN_CREW_POSITION));
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.CrewDataEntry.COLUMN_MOVIE_ID));

            CrewData crewData = new CrewData(id, crewName, crewPosition, movieID);
            crewDataArrayList.add(crewData);

        } while (cursor.moveToNext());

        cursor.close();

        return crewDataArrayList;
    }

    @Override
    public void addData(CrewData crewData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.CrewDataEntry._ID, crewData.getId());
        contentValues.put(MovieDBContract.CrewDataEntry.COLUMN_CREW_NAME, crewData.getCrewName());
        contentValues.put(MovieDBContract.CrewDataEntry.COLUMN_CREW_POSITION, crewData.getCrewPosition());
        contentValues.put(MovieDBContract.CrewDataEntry.COLUMN_MOVIE_ID, crewData.getMovieID());

        context.getContentResolver().insert(MovieDBContract.CrewDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.CrewDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.CrewDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}

