package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.movieDB.dateToString.DateToNumericDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nugroho on 06/09/17.
 */

public class PeopleDataDB extends DataDB<PeopleData>
{
    public PeopleDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<PeopleData> getAllData()
    {
        ArrayList<PeopleData> peopleDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.PeopleDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.PeopleDataEntry._ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleDataEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleDataEntry.COLUMN_PEOPLE_NAME));
            String placeOfBirth = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleDataEntry.COLUMN_PLACE_OF_BIRTH));

            StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
            String calendarString = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleDataEntry.COLUMN_BIRTHDAY));
            Calendar birthdayDate = stringToDateSetter.getDateTime(calendarString);

            String biography = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleDataEntry.COLUMN_BIOGRAPHY));
            String profileImage = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleDataEntry.COLUMN_PROFILE_IMAGE));
            String othersName = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleDataEntry.COLUMN_OTHER_NAME));
            String knownRoles = cursor.getString(cursor.getColumnIndex(MovieDBContract.PeopleDataEntry.COLUMN_KNOWN_ROLES));

            PeopleData peopleData = new PeopleData(id, name, placeOfBirth, birthdayDate, biography, profileImage, othersName);
            peopleDataArrayList.add(peopleData);

        } while (cursor.moveToNext());

        cursor.close();

        return peopleDataArrayList;
    }

    @Override
    public void addData(PeopleData peopleData) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.PeopleDataEntry._ID, peopleData.getId());
        contentValues.put(MovieDBContract.PeopleDataEntry.COLUMN_PEOPLE_NAME, peopleData.getName());
        contentValues.put(MovieDBContract.PeopleDataEntry.COLUMN_PLACE_OF_BIRTH, peopleData.getPlaceOfBirth());

        DateToStringSetter dateToStringSetter = new DateToNumericDateStringSetter();
        contentValues.put(MovieDBContract.PeopleDataEntry.COLUMN_BIRTHDAY, dateToStringSetter.getDateString(peopleData.getBirthtdayDate()));

        contentValues.put(MovieDBContract.PeopleDataEntry.COLUMN_BIOGRAPHY, peopleData.getBiography());
        contentValues.put(MovieDBContract.PeopleDataEntry.COLUMN_PROFILE_IMAGE, peopleData.getProfileImage());
        contentValues.put(MovieDBContract.PeopleDataEntry.COLUMN_OTHER_NAME, peopleData.getOthersName());

        context.getContentResolver().insert(MovieDBContract.PeopleDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.PeopleDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.PeopleDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}
