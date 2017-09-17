package com.example.android.moviedb3.jsonParsing;

import android.util.Log;

import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nugroho on 17/09/17.
 */

public class MovieSearchListJSONParser implements JSONParser<ArrayList<MovieData>>
{
    @Override
    public ArrayList<MovieData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        try
        {
            JSONArray results = jsonObject.getJSONArray("results");
            ArrayList<MovieData> movieDataArrayList = new ArrayList<>();

            if(results != null)
            {
                for (int a = 0; a < results.length(); a++)
                {
                    JSONObject result = results.getJSONObject(a);
                    String movieID = result.getString("id");

                    String originalTitle = result.getString("title");
                    if(originalTitle == null || originalTitle.equals("null") || originalTitle.isEmpty())
                    {
                        originalTitle = result.getString("original_title");
                    }

                    String moviePosterURL = result.getString("poster_path");
                    if(moviePosterURL == null || moviePosterURL.equals("null") || moviePosterURL.isEmpty())
                    {
                        moviePosterURL = "";
                    }

                    String voteRatingString = result.getString("vote_average");
                    double voteRating;
                    if(voteRatingString == null || voteRatingString.equals("null") || voteRatingString.isEmpty())
                    {
                        voteRating = 0;
                    }
                    else
                    {
                        voteRating = Double.valueOf(voteRatingString);
                    }

                    movieDataArrayList.add(new MovieData(movieID, originalTitle, moviePosterURL, voteRating));
                }
            }

            return movieDataArrayList;
        }
        catch (JSONException e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }
}
