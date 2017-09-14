package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.moviedb3.movieDB.dateToString.DateToNumericDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import java.util.Calendar;

/**
 * Created by nugroho on 06/09/17.
 */

public class PeopleData extends BaseData implements Parcelable
{
    String name;
    String placeOfBirth;
    private Calendar birthtdayDate;
    String biography;
    String profileImage;
    String othersName;
    String knownRoles;

    public PeopleData(String id, String name, String placeOfBirth, Calendar birthtdayDate, String biography, String profileImage, String othersName, String knownRoles) {
        super(id);
        this.name = name;
        this.placeOfBirth = placeOfBirth;
        this.birthtdayDate = birthtdayDate;
        this.biography = biography;
        this.profileImage = profileImage;
        this.othersName = othersName;
        this.knownRoles = knownRoles;
    }

    public PeopleData(String id, String knownRoles) {
        super(id);
        this.knownRoles = knownRoles;
    }

    public PeopleData(String id, String name, String placeOfBirth, Calendar birthtdayDate, String biography, String profileImage, String othersName) {
        super(id);
        this.name = name;
        this.placeOfBirth = placeOfBirth;
        this.birthtdayDate = birthtdayDate;
        this.biography = biography;
        this.profileImage = profileImage;
        this.othersName = othersName;
    }

    public PeopleData(Parcel in)
    {
        super(in.readString());

        this.name = in.readString();
        this.placeOfBirth = in.readString();

        StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
        this.birthtdayDate = stringToDateSetter.getDateTime(in.readString());

        this.biography = in.readString();
        this.profileImage = in.readString();
        this.othersName = in.readString();
        this.knownRoles = in.readString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setBirthtdayDate(Calendar birthtdayDate) {
        this.birthtdayDate = birthtdayDate;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setOthersName(String othersName) {
        this.othersName = othersName;
    }

    public void setKnownRoles(String knownRoles) {
        this.knownRoles = knownRoles;
    }

    public String getName() {
        return name;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public Calendar getBirthtdayDate() {
        return birthtdayDate;
    }

    public String getBiography() {
        return biography;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getOthersName() {
        return othersName;
    }

    public String getKnownRoles() {
        return knownRoles;
    }

    public String getSmallProfileImage()
    {
        return "http://image.tmdb.org/t/p/w185/" + profileImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.getId());
        dest.writeString(this.getName());
        dest.writeString(this.getPlaceOfBirth());

        DateToStringSetter dateToStringSetter = new DateToNumericDateStringSetter();
        dest.writeString(dateToStringSetter.getDateString(this.getBirthtdayDate()));

        dest.writeString(this.getBiography());
        dest.writeString(this.getProfileImage());
        dest.writeString(this.getOthersName());
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new PeopleData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new PeopleData[size];
        }
    };
}
