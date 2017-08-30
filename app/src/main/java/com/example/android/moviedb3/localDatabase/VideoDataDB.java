package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.VideoData;

import java.util.ArrayList;

/**
 * Created by nugroho on 23/08/17.
 */

public class VideoDataDB extends DataDB<VideoData>
{
    String videoURL;
    String videoThumbnailURL;
    int movieID;

    public VideoDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<VideoData> getAllData() {

        ArrayList<VideoData> videoDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.VideoDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.VideoDataEntry.COLUMN_MOVIE_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.VideoDataEntry._ID));
            String videoURL = cursor.getString(cursor.getColumnIndex(MovieDBContract.VideoDataEntry.COLUMN_VIDEO_URL));
            String videoThumbnailURL = cursor.getString(cursor.getColumnIndex(MovieDBContract.VideoDataEntry.COLUMN_VIDEO_THUMBNAIL_URL));
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.VideoDataEntry.COLUMN_MOVIE_ID));

            VideoData videoData = new VideoData(id, videoURL, videoThumbnailURL, movieID);
            videoDataArrayList.add(videoData);

        } while (cursor.moveToNext());

        cursor.close();

        return videoDataArrayList;
    }

    @Override
    public void addData(VideoData videoData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.VideoDataEntry._ID, videoData.getId());
        contentValues.put(MovieDBContract.VideoDataEntry.COLUMN_VIDEO_URL, videoData.getVideoURL());
        contentValues.put(MovieDBContract.VideoDataEntry.COLUMN_VIDEO_THUMBNAIL_URL, videoData.getVideoThumbnailURL());
        contentValues.put(MovieDBContract.VideoDataEntry.COLUMN_MOVIE_ID, videoData.getMovieID());

        context.getContentResolver().insert(MovieDBContract.VideoDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.VideoDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.VideoDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}
