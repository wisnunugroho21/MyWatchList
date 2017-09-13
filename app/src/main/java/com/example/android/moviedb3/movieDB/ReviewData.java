package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nugroho on 26/07/17.
 */

public class ReviewData extends DependencyData implements Parcelable, MovieInfoData
{
    private String name;
    private String reviewContent;
    private String movieID;
    private String imageReviewer;

    public ReviewData(String id, String name, String reviewContent, String movieID,
                      String imageReviewer) {

        super(id);
        this.name = name;
        this.reviewContent = reviewContent;
        this.movieID = movieID;
        this.imageReviewer = imageReviewer;
    }

    public ReviewData(Parcel in)
    {
        super(in.readString());
        this.name = in.readString();
        this.reviewContent = in.readString();
        this.movieID = in.readString();
        this.imageReviewer = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getMovieID() {
        return movieID;
    }

    public String getImageReviewer() {
        return imageReviewer;
    }

    @Override
    public String getImage() {
        return "image.tmdb.org/t/p/w92" + imageReviewer;
    }

    @Override
    public String getIDDependent() {
        return movieID;
    }

    @Override
    public String getFirstText() {
        return name;
    }

    @Override
    public String getSecondText() {
        return reviewContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getId());
        dest.writeString(this.name);
        dest.writeString(this.reviewContent);
        dest.writeString(this.movieID);
        dest.writeString(this.imageReviewer);
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new ReviewData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new ReviewData[size];
        }
    };

}
