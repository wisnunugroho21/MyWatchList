package com.example.android.moviedb3.movieDB;

import android.os.Parcelable;

/**
 * Created by nugroho on 27/08/17.
 */

public abstract class DependencyData extends BaseData
{
    public DependencyData(String id) {
        super(id);
    }

    public abstract String getIDDependent();
}
