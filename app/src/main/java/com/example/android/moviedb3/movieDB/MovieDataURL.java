package com.example.android.moviedb3.movieDB;

import android.content.Context;

import com.example.android.moviedb3.BuildConfig;
import com.example.android.moviedb3.R;
import com.example.android.moviedb3.sharedPreferences.DefaultStringStatePreference;
import com.example.android.moviedb3.sharedPreferences.PreferencesUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by nugroho on 26/08/17.
 */

public class MovieDataURL
{
    public static String API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    public static String GetReviewURL(String movieID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/movie/" + movieID + "/reviews?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetCastURL(String movieID)
    {
        return "https://api.themoviedb.org/3/movie/" + movieID + "/credits?api_key=" + API_KEY;
    }

    public static String GetCrewURL(String movieID)
    {
        return "https://api.themoviedb.org/3/movie/" + movieID + "/credits?api_key=" + API_KEY;
    }

    public static String GetVideoURL(String movieID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/movie/"+ movieID +"/videos?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetPopularURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        String regionID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.region_key), context.getString(R.string.region_usa_value));

        return "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=" + languageID + "&region=" + regionID;
    }

    public static String GetTopRateURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        String regionID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.region_key), context.getString(R.string.region_usa_value));

        return "https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY + "&language=" + languageID + "&region=" + regionID;
    }

    public static String GetNowShowingURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        String regionID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.region_key), context.getString(R.string.region_usa_value));

        return "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=" + languageID + "&region=" + regionID;
    }

    public static String GetComingSoonURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        String regionID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.region_key), context.getString(R.string.region_usa_value));

        return "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=" + languageID + "&region=" + regionID;
    }

    public static String GetMovieURL(String movieID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/movie/" + movieID + "?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetGenreListURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetGenreTopRateMovieListURL(String idGenre, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        String regionID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.region_key), context.getString(R.string.region_usa_value));

        return "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=" + languageID + "&region=" + regionID + "&sort_by=vote_average.desc&with_genres=" + idGenre;
    }

    public static String GetGenrePopularMovieListURL(String idGenre, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        String regionID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.region_key), context.getString(R.string.region_usa_value));

        return "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=" + languageID + "&region=" + regionID + "&sort_by=popularity.desc&with_genres=" + idGenre;
    }

    public static String GetPeopleDetailURL(String idPeople, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        String url = "https://api.themoviedb.org/3/person/" + idPeople +"?api_key=" + API_KEY + "&language=" + languageID;
        return url;
    }

    public static String GetPopularPeopleListURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/person/popular?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetBackdropImagePeopleURL(String peopleID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/person/" + peopleID + "/tagged_images?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetPeopleMovieCastURL(String peopleID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/person/" + peopleID + "/movie_credits?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetPeopleMovieCrewURL(String peopleID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/person/" + peopleID + "/movie_credits?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetPeopleTVCastURL(String peopleID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/person/" + peopleID + "/tv_credits?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetPeopleTVCrewURL(String peopleID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/person/" + peopleID + "/tv_credits?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetCastTVURL(String tvID)
    {
        return "https://api.themoviedb.org/3/tv/" + tvID + "/credits?api_key=" + API_KEY;
    }

    public static String GetCrewTVURL(String tvID)
    {
        return "https://api.themoviedb.org/3/tv/" + tvID + "/credits?api_key=" + API_KEY;
    }

    public static String GetVideoTVURL(String tvID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/tv/"+ tvID +"/videos?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetPopularTVURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/tv/popular?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetTopRateTVURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/tv/top_rated?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetAiringTodayTVURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/tv/airing_today?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetOnTheAirTVURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/tv/on_the_air?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetTVURL(String tvID, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/tv/" + tvID + "?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetTVGenreListURL(Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/genre/tv/list?api_key=" + API_KEY + "&language=" + languageID;
    }

    public static String GetGenreTopRateTVListURL(String idGenre, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        String regionID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.region_key), context.getString(R.string.region_usa_value));

        return "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=" + languageID + "&sort_by=vote_average.desc&with_genres=" + idGenre;
    }

    public static String GetGenrePopularTVListURL(String idGenre, Context context)
    {
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        String regionID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.region_key), context.getString(R.string.region_usa_value));

        return "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=" + languageID + "&sort_by=popularity.desc&with_genres=" + idGenre;
    }

    public static String GetMovieSearchResult(String query, Context context)
    {
        String newQuery = "";
        try
        {
            newQuery = URLEncoder.encode(query, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=" + languageID + "&query=" + newQuery;
    }

    public static String GetTVSearchResult(String query, Context context)
    {
        String newQuery = "";
        try
        {
            newQuery = URLEncoder.encode(query, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        String languageID = PreferencesUtils.GetData(new DefaultStringStatePreference(context), context.getString(R.string.content_language_key), context.getString(R.string.content_language_english_value));
        return "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=" + languageID + "&query=" + newQuery;
    }




}
