package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleCastData extends DependencyData implements Parcelable, MovieInfoData {

    String movieName;
    String characterName;
    String movieID;
    String peopleID;
    String imageCast;

    public PeopleCastData(String id, String movieName, String characterName, String movieID, String peopleID,
                    String imageCast) {
        super(id);
        this.movieName = movieName;
        this.characterName = characterName;
        this.movieID = movieID;
        this.peopleID = peopleID;
        this.imageCast = imageCast;
    }

    public PeopleCastData(Parcel in)
    {
        super(in.readString());
        this.movieName =  in.readString();
        this.characterName = in.readString();
        this.movieID = in.readString();
        this.peopleID = in.readString();
    }

    public String getMovieName() {
        return movieName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getMovieID() {
        return movieID;
    }

    public String getPeopleID() {
        return peopleID;
    }

    public String getImageCast() {
        return imageCast;
    }

    @Override
    public String getImage() {
        return "http://image.tmdb.org/t/p/w92" +  imageCast;
    }

    @Override
    public String getIDDependent() {
        return peopleID;
    }

    @Override
    public String getFirstText() {
        return movieName;
    }

    @Override
    public String getSecondText() {
        return characterName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getId());
        dest.writeString(this.movieName);
        dest.writeString(this.characterName);
        dest.writeString(this.movieID);
        dest.writeString(this.peopleID);
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new PeopleCastData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new PeopleCastData[size];
        }
    };
}
