package com.example.android.moviedb3.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.moviedb3.contentProvider.MovieDBContract;
import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.movieDB.VideoTVData;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class VideoTVDataDB extends DataDB<VideoTVData>
{
    String videoURL;
    String videoThumbnailURL;
    int movieID;

    public VideoTVDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<VideoTVData> getAllData() {

        ArrayList<VideoTVData> videoDataArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MovieDBContract.VideoTVDataEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieDBContract.VideoTVDataEntry.COLUMN_TV_ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(MovieDBContract.VideoTVDataEntry._ID));
            String videoURL = cursor.getString(cursor.getColumnIndex(MovieDBContract.VideoTVDataEntry.COLUMN_VIDEO_URL));
            String videoThumbnailURL = cursor.getString(cursor.getColumnIndex(MovieDBContract.VideoTVDataEntry.COLUMN_VIDEO_THUMBNAIL_URL));
            String movieID = cursor.getString(cursor.getColumnIndex(MovieDBContract.VideoTVDataEntry.COLUMN_TV_ID));

            VideoTVData videoData = new VideoTVData(id, videoURL, videoThumbnailURL, movieID);
            videoDataArrayList.add(videoData);

        } while (cursor.moveToNext());

        cursor.close();

        return videoDataArrayList;
    }

    @Override
    public void addData(VideoTVData videoTVData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieDBContract.VideoTVDataEntry._ID, videoTVData.getId());
        contentValues.put(MovieDBContract.VideoTVDataEntry.COLUMN_VIDEO_URL, videoTVData.getVideoURL());
        contentValues.put(MovieDBContract.VideoTVDataEntry.COLUMN_VIDEO_THUMBNAIL_URL, videoTVData.getVideoThumbnailURL());
        contentValues.put(MovieDBContract.VideoTVDataEntry.COLUMN_TV_ID, videoTVData.getTvID());

        context.getContentResolver().insert(MovieDBContract.VideoTVDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = MovieDBContract.VideoTVDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = MovieDBContract.VideoTVDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}

