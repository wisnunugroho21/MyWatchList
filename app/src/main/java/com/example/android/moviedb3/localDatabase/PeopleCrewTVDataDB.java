package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.PeopleCastTvData;
import com.example.android.moviedb3.movieDB.PeopleCrewTVData;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleCrewTVDataDB extends DataDB<PeopleCrewTVData>
{
    public PeopleCrewTVDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<PeopleCrewTVData> getAllData() {

        ArrayList<PeopleCrewTVData> peopleCrewTVDatas = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.PeopleCrewTVDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.PeopleCrewTVDataEntry.COLUMN_TV_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewTVDataEntry._ID));
            String tvName = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_TV_NAME));
            String crewPosition = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_CREW_POSITION));
            String tvID = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_TV_ID));
            String peopleID = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_PEOPLE_ID));
            String image = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_IMAGE));

            PeopleCrewTVData peopleCrewTVData = new PeopleCrewTVData(id, tvName, crewPosition, tvID, peopleID, image);
            peopleCrewTVDatas.add(peopleCrewTVData);

        } while (cursor.moveToNext());

        cursor.close();

        return peopleCrewTVDatas;
    }

    @Override
    public void addData(PeopleCrewTVData peopleCrewTVData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.PeopleCrewTVDataEntry._ID, peopleCrewTVData.getId());
        contentValues.put(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_TV_NAME, peopleCrewTVData.getMovieName());
        contentValues.put(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_CREW_POSITION, peopleCrewTVData.getCrewPosition());
        contentValues.put(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_TV_ID, peopleCrewTVData.getTvID());
        contentValues.put(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_PEOPLE_ID, peopleCrewTVData.getPeopleID());
        contentValues.put(MovieDBContract.PeopleCrewTVDataEntry.COLUMN_IMAGE, peopleCrewTVData.getImageCrew());

        context.getContentResolver().insert(MovieDBContract.PeopleCrewTVDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.PeopleCrewTVDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.PeopleCrewTVDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}