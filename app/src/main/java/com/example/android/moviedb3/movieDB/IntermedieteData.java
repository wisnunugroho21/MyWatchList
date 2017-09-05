package com.example.android.moviedb3.movieDB;

/**
 * Created by nugroho on 03/09/17.
 */

public abstract class IntermedieteData extends BaseData
{
    public IntermedieteData(String id)
    {
        super(id);
    }

    public abstract String getFirstID();
    public abstract String getSecondID();
}
