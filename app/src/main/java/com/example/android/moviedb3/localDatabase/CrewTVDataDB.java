package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.CastTVData;
import com.example.android.moviedb3.movieDB.CrewTVData;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class CrewTVDataDB extends DataDB<CrewTVData>
{
    String crewName;
    String crewPosition;
    int movieID;

    public CrewTVDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<CrewTVData> getAllData() {

        ArrayList<CrewTVData> crewDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.CrewTVDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.CrewTVDataEntry.COLUMN_TV_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.CrewTVDataEntry._ID));
            String crewName = cursor.getString(cursor.getColumnIndex(MovieDBContract.CrewTVDataEntry.COLUMN_CREW_NAME));
            String crewPosition = cursor.getString(cursor.getColumnIndex(MovieDBContract.CrewTVDataEntry.COLUMN_CREW_POSITION));
            String tvID = cursor.getString(cursor.getColumnIndex(MovieDBContract.CrewTVDataEntry.COLUMN_TV_ID));
            String peopleID = cursor.getString(cursor.getColumnIndex(MovieDBContract.CrewTVDataEntry.COLUMN_PEOPLE_ID));

            CrewTVData crewData = new CrewTVData(id, crewName, crewPosition, tvID, peopleID);
            crewDataArrayList.add(crewData);

        } while (cursor.moveToNext());

        cursor.close();

        return crewDataArrayList;
    }

    @Override
    public void addData(CrewTVData crewTVData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.CrewTVDataEntry._ID, crewTVData.getId());
        contentValues.put(MovieDBContract.CrewTVDataEntry.COLUMN_CREW_NAME, crewTVData.getCrewName());
        contentValues.put(MovieDBContract.CrewTVDataEntry.COLUMN_CREW_POSITION, crewTVData.getCrewPosition());
        contentValues.put(MovieDBContract.CrewTVDataEntry.COLUMN_TV_ID, crewTVData.getTvID());
        contentValues.put(MovieDBContract.CrewTVDataEntry.COLUMN_PEOPLE_ID, crewTVData.getPeopleID());

        context.getContentResolver().insert(MovieDBContract.CrewTVDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.CrewTVDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.CrewTVDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}


