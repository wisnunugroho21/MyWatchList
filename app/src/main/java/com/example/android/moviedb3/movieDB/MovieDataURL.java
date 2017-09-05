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

    public static String GetTopRateURL()
    {
        return "https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;
    }

    public static String GetNowShowingURL()
    {
        return "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY;
    }

    public static String GetComingSoonURL()
    {
        return "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY;
    }

    public static String GetMovieURL(String idMovie)
    {
        return "https://api.themoviedb.org/3/movie/" + idMovie + "?api_key=" + API_KEY;
    }

    public static String GetGenreListURL()
    {
        return "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY;
    }

    public static String GetGenreTopRateMovieListURL(String idGenre)
    {
        return "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&sort_by=vote_average.desc&with_genres=" + idGenre;
    }

    public static String GetGenrePopularMovieListURL(String idGenre)
    {
        return "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&sort_by=popularity.desc&with_genres=" + idGenre;
    }



}
