package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.GenreTvData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class GenreTVListJSONParser implements JSONParser<ArrayList<GenreTvData>>
{
    String genreID;

    public GenreTVListJSONParser(String genreID) {
        this.genreID = genreID;
    }

    @Override
    public ArrayList<GenreTvData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<GenreTvData> genreTvDataArrayList = new ArrayList<>();

        try
        {
            JSONArray results = jsonObject.getJSONArray("results");

            for(int a = 0; a < results.length(); a++)
            {
                JSONObject result = results.getJSONObject(a);
                String movieID = result.getString("id");

                String id = String.valueOf((int )(Math.random() * Integer.MAX_VALUE + 1000000));
                GenreTvData genreTvData = new GenreTvData(id, movieID, genreID);
                genreTvDataArrayList.add(genreTvData);
            }

            return genreTvDataArrayList;

        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
