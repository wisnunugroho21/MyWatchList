package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.movieDB.dateToString.DateToNumericDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVDataDB extends DataDB<TVData>
{
    public TVDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<TVData> getAllData()
    {
        ArrayList<TVData> tvList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.TVDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.TVDataEntry._ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String tvID = cursor.getString(cursor.getColumnIndex(MovieDBContract.TVDataEntry._ID));
            String originalTitle = cursor.getString(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_ORIGINAL_TITLE));
            String moviePosterURL = cursor.getString(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_MOVIE_POSTER_URL));
            String backdropImageURL = cursor.getString(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_BACKDROP_IMAGE_URL));
            String genre = cursor.getString(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_GENRE));
            int episode = cursor.getInt(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_EPISODE));
            int season = cursor.getInt(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_SEASON));

            StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
            String calendarString = cursor.getString(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_FIRST_RELEASE_DATE));
            Calendar firstReleaseDate = stringToDateSetter.getDateTime(calendarString);

            double voteRating = cursor.getDouble(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_VOTE_RATING));
            String plotSynopsis = cursor.getString(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_PLOT_SYNOPSIS));
            String availableOnNetwork = cursor.getString(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_AVAILABLE_ON_NETWORK));
            String seriesStatus = cursor.getString(cursor.getColumnIndex(MovieDBContract.TVDataEntry.COLUMN_SERIES_STATUS));

            TVData tvData = new TVData(tvID, originalTitle, moviePosterURL, backdropImageURL, genre, episode, season, firstReleaseDate, voteRating, plotSynopsis, availableOnNetwork, seriesStatus);
            tvList.add(tvData);

        } while (cursor.moveToNext());

        cursor.close();

        return tvList;
    }

    @Override
    public void addData(TVData tvData) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.TVDataEntry._ID, tvData.getId());
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_ORIGINAL_TITLE, tvData.getOriginalTitle());
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_MOVIE_POSTER_URL, tvData.getTvPosterURL());
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_BACKDROP_IMAGE_URL, tvData.getBackdropImageURL());
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_GENRE, tvData.getGenre());
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_EPISODE, tvData.getEpisode());
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_SEASON, tvData.getSeason());

        DateToStringSetter dateToStringSetter = new DateToNumericDateStringSetter();
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_FIRST_RELEASE_DATE, dateToStringSetter.getDateString(tvData.getFirstReleaseDate()));

        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_VOTE_RATING, tvData.getVoteRating());
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_PLOT_SYNOPSIS, tvData.getPlotSypnosis());
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_AVAILABLE_ON_NETWORK, tvData.getAvailableOnNetwork());
        contentValues.put(MovieDBContract.TVDataEntry.COLUMN_SERIES_STATUS, tvData.getSeriesStatus());

        context.getContentResolver().insert(MovieDBContract.TVDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.TVDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.TVDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}
