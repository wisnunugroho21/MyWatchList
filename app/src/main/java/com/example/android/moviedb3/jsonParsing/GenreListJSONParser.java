package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.GenreData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreListJSONParser implements JSONParser<ArrayList<GenreData>>
{
    @Override
    public ArrayList<GenreData> Parse(JSONObject jsonObject) {

        ArrayList<GenreData> genreDataArrayList = new ArrayList<>();

        try
        {
            JSONArray genres = jsonObject.getJSONArray("genres");

            for (int a = 0; a < genres.length(); a++)
            {
                JSONObject genre = genres.getJSONObject(a);

                String id = genre.getString("id");
                String name = genre.getString("name");

                GenreData genreData = new GenreData(id, name);
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
