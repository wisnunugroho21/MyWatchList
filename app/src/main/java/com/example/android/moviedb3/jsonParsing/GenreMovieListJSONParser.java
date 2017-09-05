package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.GenreMovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class GenreMovieListJSONParser implements JSONParser<ArrayList<GenreMovieData>>
{
    String genreID;

    public GenreMovieListJSONParser(String genreID) {
        this.genreID = genreID;
    }

    @Override
    public ArrayList<GenreMovieData> Parse(JSONObject jsonObject) {

        ArrayList<GenreMovieData> genreMovieDataArrayList = new ArrayList<>();

        try
        {
            JSONArray results = jsonObject.getJSONArray("results");

            for(int a = 0; a < results.length(); a++)
            {
                JSONObject result = results.getJSONObject(a);
                String movieID = result.getString("id");

                String id = String.valueOf((int )(Math.random() * Integer.MAX_VALUE + 1000000));
                GenreMovieData genreMovieData = new GenreMovieData(id, movieID, genreID);
                genreMovieDataArrayList.add(genreMovieData);
            }

            return genreMovieDataArrayList;

        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
