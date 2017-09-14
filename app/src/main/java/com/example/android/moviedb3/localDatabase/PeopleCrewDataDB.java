package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.PeopleCrewData;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleCrewDataDB extends DataDB<PeopleCrewData>
{
    int movieID;

    public PeopleCrewDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<PeopleCrewData> getAllData() {

        ArrayList<PeopleCrewData> crewDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.PeopleCrewDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.PeopleCrewDataEntry.COLUMN_MOVIE_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewDataEntry._ID));
            String crewName = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewDataEntry.COLUMN_MOVIE_NAME));
            String crewPosition = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewDataEntry.COLUMN_CREW_POSITION));
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewDataEntry.COLUMN_MOVIE_ID));
            String peopleID = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewDataEntry.COLUMN_PEOPLE_ID));
            String image = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewDataEntry.COLUMN_IMAGE));

            PeopleCrewData peopleCrewData = new PeopleCrewData(id, crewName, crewPosition, movieID, peopleID, image);
            crewDataArrayList.add(peopleCrewData);

        } while (cursor.moveToNext());

        cursor.close();

        return crewDataArrayList;
    }

    @Override
    public void addData(PeopleCrewData crewData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.PeopleCrewDataEntry._ID, crewData.getId());
        contentValues.put(MovieDBContract.PeopleCrewDataEntry.COLUMN_MOVIE_NAME, crewData.getMovieName());
        contentValues.put(MovieDBContract.PeopleCrewDataEntry.COLUMN_CREW_POSITION, crewData.getCrewPosition());
        contentValues.put(MovieDBContract.PeopleCrewDataEntry.COLUMN_MOVIE_ID, crewData.getMovieID());
        contentValues.put(MovieDBContract.PeopleCrewDataEntry.COLUMN_PEOPLE_ID, crewData.getPeopleID());
        contentValues.put(MovieDBContract.PeopleCrewDataEntry.COLUMN_IMAGE, crewData.getImageCrew());

        context.getContentResolver().insert(MovieDBContract.PeopleCrewDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.PeopleCrewDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.PeopleCrewDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}

