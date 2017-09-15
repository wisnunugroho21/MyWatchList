package com.example.android.moviedb3.movieDB;

import android.os.Parcelable;

/**
 * Created by nugroho on 23/08/17.
 */

public interface MovieInfoData extends Parcelable
{
    String getFirstText();
    String getSecondText();
    String getImage();
}
