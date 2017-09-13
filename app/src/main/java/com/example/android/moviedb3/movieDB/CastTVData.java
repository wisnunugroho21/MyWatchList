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
    String imageCast;

    public CastTVData(String id, String castName, String characterName,
                      String tvID, String peopleID, String imageCast) {
        super(id);
        this.castName = castName;
        this.characterName = characterName;
        this.tvID = tvID;
        this.peopleID = peopleID;
        this.imageCast = imageCast;
    }

    public CastTVData(Parcel in)
    {
        super(in.readString());
        this.castName =  in.readString();
        this.characterName = in.readString();
        this.tvID = in.readString();
        this.peopleID = in.readString();
        this.imageCast = in.readString();
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

    public String getImageCast() {
        return imageCast;
    }

    @Override
    public String getImage() {
        return "http://image.tmdb.org/t/p/w92" + imageCast;
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
        dest.writeString(this.imageCast);
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
