package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.TVGenre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVGenreListJSONParser implements JSONParser<ArrayList<TVGenre>>
{
    @Override
    public ArrayList<TVGenre> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<TVGenre> genreDataArrayList = new ArrayList<>();

        try
        {
            JSONArray genres = jsonObject.getJSONArray("genres");

            for (int a = 0; a < genres.length(); a++)
            {
                JSONObject genre = genres.getJSONObject(a);

                String id = genre.getString("id");
                String name = genre.getString("name");

                TVGenre genreData = new TVGenre(id, name);
                genreDataArrayList.add(genreData);
            }

            return genreDataArrayList;

        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
