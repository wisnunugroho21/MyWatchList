package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.GenreTvData;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class GenreTVPopularDB extends DataDB<GenreTvData>
{
    public GenreTVPopularDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<GenreTvData> getAllData() {

        ArrayList<GenreTvData> genreTvDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.GenreTVPopularEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.GenreTVPopularEntry._ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreTVPopularEntry._ID));
            String genreID = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreTVPopularEntry.COLUMN_GENRE_ID));
            String tvID = cursor.getString(cursor.getColumnIndex(MovieDBContract.GenreTVPopularEntry.COLUMN_TV_ID));

            GenreTvData genreTvData = new GenreTvData(id, tvID, genreID);
            genreTvDataArrayList.add(genreTvData);

        } while (cursor.moveToNext());

        cursor.close();

        return genreTvDataArrayList;
    }

    @Override
    public void addData(GenreTvData genreTvData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.GenreTVPopularEntry._ID, genreTvData.getId());
        contentValues.put(MovieDBContract.GenreTVPopularEntry.COLUMN_GENRE_ID, genreTvData.getIdGenre());
        contentValues.put(MovieDBContract.GenreTVPopularEntry.COLUMN_TV_ID, genreTvData.getIdTV());

        context.getContentResolver().insert(MovieDBContract.GenreTVPopularEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.GenreTVPopularEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.GenreTVPopularEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}

