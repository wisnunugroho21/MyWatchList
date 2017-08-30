package com.example.android.moviedb3.localDatabase;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by nugroho on 27/07/17.
 */

public abstract class DataDB<Data>
{
    Context context;

    public DataDB(Context context)
    {
        this.context = context;
    }

    public abstract ArrayList<Data> getAllData();
    public abstract void addData(Data data);
    public abstract void removeData(String idData);
    public abstract void removeAllData();
}
