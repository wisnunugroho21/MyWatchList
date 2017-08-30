package com.example.android.moviedb3.movieDB;

import com.example.android.moviedb3.BuildConfig;

/**
 * Created by nugroho on 26/08/17.
 */

public class MovieDataURL {

    public static String API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    public static String GetReviewURL(String movieID)
    {
        return "https://api.themoviedb.org/3/movie/" + movieID + "/reviews?api_key=" + API_KEY;
    }

    public static String GetCastURL(String movieID)
    {
        return "https://api.themoviedb.org/3/movie/" + movieID + "/credits?api_key=" + API_KEY;
    }

    public static String GetCrewURL(String movieID)
    {
        return "https://api.themoviedb.org/3/movie/" + movieID + "/credits?api_key=" + API_KEY;
    }

    public static String GetVideoURL(String movieID)
    {
        return "https://api.themoviedb.org/3/movie/"+ movieID +"/videos?api_key=" + API_KEY;
    }

    public static String GetPopularURL()
    {
        return "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
    }

    public static String GetMovieURL(String idMovie)
    {
        return "https://api.themoviedb.org/3/movie/" + idMovie + "?api_key=" + API_KEY;
    }

}
