package com.example.android.moviedb3.dataManager.movieDBGetter;

/**
 * Created by nugroho on 30/08/17.
 */

public class DBGetter
{
    public static void GetData(IMovieDBGetter movieDBGetter)
    {
        movieDBGetter.getData();
    }
}