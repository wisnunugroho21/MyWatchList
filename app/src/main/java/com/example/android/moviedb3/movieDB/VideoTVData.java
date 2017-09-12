package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nugroho on 11/09/17.
 */

public class VideoTVData extends DependencyData implements Parcelable
{
    String videoURL;
    String videoThumbnailURL;
    String tvID;

    public VideoTVData(String id, String videoURL, String videoThumbnailURL, String tvID)
    {
        super(id);
        this.videoURL = videoURL;
        this.videoThumbnailURL = videoThumbnailURL;
        this.tvID = tvID;
    }

    public VideoTVData(Parcel in)
    {
        super(in.readString());
        this.videoURL = in.readString();
        this.videoThumbnailURL = in.readString();
        this.tvID = in.readString();
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getVideoThumbnailURL() {
        return videoThumbnailURL;
    }

    public String getTvID() {
        return tvID;
    }

    @Override
    public String getIDDependent() {
        return tvID;
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
        dest.writeString(this.tvID);
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new VideoTVData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new VideoTVData[size];
        }
    };
}
