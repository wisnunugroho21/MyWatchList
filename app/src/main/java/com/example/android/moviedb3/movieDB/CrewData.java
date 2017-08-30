package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nugroho on 09/08/17.
 */

public class CrewData extends DependencyData implements Parcelable, MovieInfoData {

    String crewName;
    String crewPosition;
    String movieID;

    public CrewData(String id, String crewName, String crewPosition, String movieID) {
        super(id);
        this.crewName = crewName;
        this.crewPosition = crewPosition;
        this.movieID = movieID;
    }

    public CrewData(Parcel in)
    {
        super(in.readString());
        this.crewName =  in.readString();
        this.crewPosition = in.readString();
        this.movieID = in.readString();
    }

    public String getCrewName() {
        return crewName;
    }

    public String getCrewPosition() {
        return crewPosition;
    }

    public String getMovieID() {
        return movieID;
    }

    @Override
    public String getIDDependent() {
        return movieID;
    }

    @Override
    public String getFirstText() {
        return getCrewName();
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
        dest.writeString(this.crewName);
        dest.writeString(this.crewPosition);
        dest.writeString(this.movieID);
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new CrewData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new CrewData[size];
        }
    };
}
