package com.example.android.moviedb3.jsonParsing;

import android.util.Log;

import com.example.android.moviedb3.movieDB.TVData;
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

public class TVSearchListJSONParser implements JSONParser<ArrayList<TVData>>
{
    @Override
    public ArrayList<TVData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        try
        {
            ArrayList<TVData> tvDataArrayList = new ArrayList<>();
            JSONArray results = jsonObject.getJSONArray("results");

            if(results != null )
            {
                for (int a = 0; a < results.length(); a++)
                {
                    JSONObject result = results.getJSONObject(a);
                    String tvID = result.getString("id");

                    String originalTitle = result.getString("name");
                    if(originalTitle == null || originalTitle.equals("null") || originalTitle.isEmpty())
                    {
                        originalTitle = result.getString("original_name");
                    }

                    String tvPosterURL = result.getString("poster_path");
                    if(tvPosterURL == null || tvPosterURL.equals("null") || tvPosterURL.isEmpty())
                    {
                        tvPosterURL = "";
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

                    tvDataArrayList.add(new TVData(tvID, originalTitle, tvPosterURL, voteRating));
                }

            }

            return tvDataArrayList;
        }
        catch (JSONException e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }
}
