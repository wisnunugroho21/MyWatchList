package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nugroho on 11/08/17.
 */

public class VideoData extends DependencyData implements Parcelable
{
    String videoURL;
    String videoThumbnailURL;
    String movieID;

    public VideoData(String id, String videoURL, String videoThumbnailURL, String movieID)
    {
        super(id);
        this.videoURL = videoURL;
        this.videoThumbnailURL = videoThumbnailURL;
        this.movieID = movieID;
    }

    public VideoData(Parcel in)
    {
        super(in.readString());
        this.videoURL = in.readString();
        this.videoThumbnailURL = in.readString();
        this.movieID = in.readString();
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getVideoThumbnailURL() {
        return videoThumbnailURL;
    }

    public String getMovieID() {
        return movieID;
    }

    @Override
    public String getIDDependent() {
        return movieID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getId());
        dest.writeString(this.videoURL);
        dest.writeString(this.videoThumbnailURL);
        dest.writeString(this.movieID);
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new VideoData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new VideoData[size];
        }
    };


}
