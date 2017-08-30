package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.ReviewData;

import java.util.ArrayList;

/**
 * Created by nugroho on 23/08/17.
 */

public class ReviewDataDB extends DataDB<ReviewData>
{
    public ReviewDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<ReviewData> getAllData() {

        ArrayList<ReviewData> reviewDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.ReviewDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.ReviewDataEntry.COLUMN_MOVIE_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.ReviewDataEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(MovieDBContract.ReviewDataEntry.COLUMN_REVIEWER_NAME));
            String reviewContent = cursor.getString(cursor.getColumnIndex(MovieDBContract.ReviewDataEntry.COLUMN_REVIEW_CONTENT));
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.ReviewDataEntry.COLUMN_MOVIE_ID));

            ReviewData reviewData = new ReviewData(id, name, reviewContent, movieID);
            reviewDataArrayList.add(reviewData);

        } while (cursor.moveToNext());

        cursor.close();

        return reviewDataArrayList;
    }

    @Override
    public void addData(ReviewData reviewData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.ReviewDataEntry._ID, reviewData.getId());
        contentValues.put(MovieDBContract.ReviewDataEntry.COLUMN_REVIEWER_NAME, reviewData.getName());
        contentValues.put(MovieDBContract.ReviewDataEntry.COLUMN_REVIEW_CONTENT, reviewData.getReviewContent());
        contentValues.put(MovieDBContract.ReviewDataEntry.COLUMN_MOVIE_ID, reviewData.getMovieID());

        context.getContentResolver().insert(MovieDBContract.ReviewDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {

        Uri uri = MovieDBContract.ReviewDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.ReviewDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}

