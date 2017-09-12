package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nugroho on 11/09/17.
 */

public class CastTVData extends DependencyData implements Parcelable, MovieInfoData {

    String castName;
    String characterName;
    String tvID;
    String peopleID;

    public CastTVData(String id, String castName, String characterName, String tvID, String peopleID) {
        super(id);
        this.castName = castName;
        this.characterName = characterName;
        this.tvID = tvID;
        this.peopleID = peopleID;
    }

    public CastTVData(Parcel in)
    {
        super(in.readString());
        this.castName =  in.readString();
        this.characterName = in.readString();
        this.tvID = in.readString();
        this.peopleID = in.readString();
    }

    public String getCastName() {
        return castName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getTvID() {
        return tvID;
    }

    public String getPeopleID() {
        return peopleID;
    }

    @Override
    public String getIDDependent() {
        return tvID;
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
        dest.writeString(this.tvID);
        dest.writeString(this.peopleID);
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new CastTVData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new CastTVData[size];
        }
    };
}
