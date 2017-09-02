package com.example.android.moviedb3.movieDB;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreData extends BaseData
{
    String name;

    public GenreData(String id, String name)
    {
        super(id);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
