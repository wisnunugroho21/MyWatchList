package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.dateToString.DateToNumericDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nugroho on 23/08/17.
 */

public class MovieDataDB extends DataDB<MovieData>
{
    public MovieDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<MovieData> getAllData()
    {
        ArrayList<MovieData> movieList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.MovieDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.MovieDataEntry._ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieDataEntry._ID));
            String originalTitle = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieDataEntry.COLUMN_ORIGINAL_TITLE));
            String moviePosterURL = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieDataEntry.COLUMN_MOVIE_POSTER_URL));
            String backdropImageURL = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieDataEntry.COLUMN_BACKDROP_IMAGE_URL));
            String genre = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieDataEntry.COLUMN_GENRE));
            int duration = cursor.getInt(cursor.getColumnIndex(MovieDBContract.MovieDataEntry.COLUMN_DURATION));

            StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
            String calendarString = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieDataEntry.COLUMN_RELEASE_DATE));
            Calendar releaseDate = stringToDateSetter.getDateTime(calendarString);

            double voteRating = cursor.getDouble(cursor.getColumnIndex(MovieDBContract.MovieDataEntry.COLUMN_VOTE_RATING));
            String tagLine = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieDataEntry.COLUMN_TAGLINE));
            String plotSynopsis = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieDataEntry.COLUMN_PLOT_SYNOPSIS));

            MovieData movieData = new MovieData(movieID, originalTitle, moviePosterURL, backdropImageURL, genre, duration,
                    releaseDate, voteRating, tagLine, plotSynopsis);

            movieList.add(movieData);

        } while (cursor.moveToNext());

        cursor.close();

        return movieList;
    }

    @Override
    public void addData(MovieData movieData) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.MovieDataEntry._ID, movieData.getId());
        contentValues.put(MovieDBContract.MovieDataEntry.COLUMN_ORIGINAL_TITLE, movieData.getOriginalTitle());
        contentValues.put(MovieDBContract.MovieDataEntry.COLUMN_MOVIE_POSTER_URL, movieData.getMoviePosterURL());
        contentValues.put(MovieDBContract.MovieDataEntry.COLUMN_BACKDROP_IMAGE_URL, movieData.getBackdropImageURL());
        contentValues.put(MovieDBContract.MovieDataEntry.COLUMN_GENRE, movieData.getGenre());
        contentValues.put(MovieDBContract.MovieDataEntry.COLUMN_DURATION, movieData.getDuration());

        DateToStringSetter dateToStringSetter = new DateToNumericDateStringSetter();
        contentValues.put(MovieDBContract.MovieDataEntry.COLUMN_RELEASE_DATE, dateToStringSetter.getDateString(movieData.getReleaseDate()));

        contentValues.put(MovieDBContract.MovieDataEntry.COLUMN_VOTE_RATING, movieData.getVoteRating());
        contentValues.put(MovieDBContract.MovieDataEntry.COLUMN_TAGLINE, movieData.getTagline());
        contentValues.put(MovieDBContract.MovieDataEntry.COLUMN_PLOT_SYNOPSIS, movieData.getPlotSypnosis());

        context.getContentResolver().insert(MovieDBContract.MovieDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.MovieDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.MovieDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}
