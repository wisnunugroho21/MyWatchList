package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nugroho on 11/08/17.
 */

public class CastData extends DependencyData implements Parcelable, MovieInfoData {

    String castName;
    String characterName;
    String movieID;
    String peopleID;
    String imageCast;

    public CastData(String id, String castName, String characterName, String movieID, String peopleID,
                    String imageCast) {
        super(id);
        this.castName = castName;
        this.characterName = characterName;
        this.movieID = movieID;
        this.peopleID = peopleID;
        this.imageCast = imageCast;
    }

    public CastData(Parcel in)
    {
        super(in.readString());
        this.castName =  in.readString();
        this.characterName = in.readString();
        this.movieID = in.readString();
        this.peopleID = in.readString();
        this.imageCast = in.readString();
    }

    public String getCastName() {
        return castName;
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
        return movieID;
    }

    @Override
    public String getFirstText() {
        return castName;
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
        dest.writeString(this.castName);
        dest.writeString(this.characterName);
        dest.writeString(this.movieID);
        dest.writeString(this.peopleID);
        dest.writeString(this.imageCast);
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new CastData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new CastData[size];
        }
    };
}
