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
    String peopleID;
    String imageCrew;

    public CrewData(String id, String crewName, String crewPosition, String movieID, String peopleID,
                    String imageCrew) {
        super(id);
        this.crewName = crewName;
        this.crewPosition = crewPosition;
        this.movieID = movieID;
        this.peopleID = peopleID;
        this.imageCrew = imageCrew;
    }

    public CrewData(Parcel in)
    {
        super(in.readString());
        this.crewName =  in.readString();
        this.crewPosition = in.readString();
        this.movieID = in.readString();
        this.peopleID = in.readString();
        this.imageCrew = in.readString();
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
        dest.writeString(this.peopleID);
        dest.writeString(this.imageCrew);
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
