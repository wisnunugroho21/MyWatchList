package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleCrewTVData extends DependencyData implements Parcelable, MovieInfoData {

    String movieName;
    String crewPosition;
    String tvID;
    String peopleID;
    String imageCrew;

    public PeopleCrewTVData(String id, String movieName, String crewPosition, String tvID,
                      String peopleID, String imageCrew) {
        super(id);
        this.movieName = movieName;
        this.crewPosition = crewPosition;
        this.tvID = tvID;
        this.peopleID = peopleID;
        this.imageCrew = imageCrew;
    }

    public PeopleCrewTVData(Parcel in)
    {
        super(in.readString());
        this.movieName =  in.readString();
        this.crewPosition = in.readString();
        this.tvID = in.readString();
        this.peopleID = in.readString();
        this.imageCrew = in.readString();
    }

    public String getMovieName() {
        return movieName;
    }

    public String getCrewPosition() {
        return crewPosition;
    }

    public String getTvID() {
        return tvID;
    }

    public String getPeopleID() {
        return peopleID;
    }

    public String getImageCrew() {
        return imageCrew;
    }

    @Override
    public String getImage() {
        return "http://image.tmdb.org/t/p/w92" + imageCrew;
    }

    @Override
    public String getIDDependent() {
        return peopleID;
    }

    @Override
    public String getFirstText() {
        return getMovieName();
    }

    @Override
    public String getSecondText() {
        return crewPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getId());
        dest.writeString(this.movieName);
        dest.writeString(this.crewPosition);
        dest.writeString(this.tvID);
        dest.writeString(this.peopleID);
        dest.writeString(this.imageCrew);
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new PeopleCrewTVData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new PeopleCrewTVData[size];
        }
    };
}
