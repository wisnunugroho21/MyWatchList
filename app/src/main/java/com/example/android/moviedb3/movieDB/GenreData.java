package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.moviedb3.movieDB.dateToString.DateToNumericDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreData extends BaseData implements Parcelable
{
    String name;

    public GenreData(String id, String name)
    {
        super(id);
        this.name = name;
    }

    public GenreData(Parcel in)
    {
        super(in.readString());
        this.name = in.readString();
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.getId());
        dest.writeString(this.getName());
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new GenreData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new GenreData[size];
        }
    };


}
