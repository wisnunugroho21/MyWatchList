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

    public CastData(String id, String castName, String characterName, String movieID) {
        super(id);
        this.castName = castName;
        this.characterName = characterName;
        this.movieID = movieID;
    }

    public CastData(Parcel in)
    {
        super(in.readString());
        this.castName =  in.readString();
        this.characterName = in.readString();
        this.movieID = in.readString();
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
