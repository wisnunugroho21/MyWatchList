package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVGenre extends BaseData implements Parcelable
{
    String name;

    public TVGenre(String id, String name)
    {
        super(id);
        this.name = name;
    }

    public TVGenre(Parcel in)
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

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new TVGenre(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new TVGenre[size];
        }
    };
}
