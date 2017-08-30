package com.example.android.moviedb3.jsonParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 27/07/17.
 */

public class MovieIDListJSONParser implements JSONParser<ArrayList<String>>
{
    @Override
    public ArrayList<String> Parse(JSONObject jsonObject) {
        ArrayList<String> movieIDArrayList = new ArrayList<>();

        try
        {
            JSONArray results = jsonObject.getJSONArray("results");

            for(int a = 0; a < results.length(); a++)
            {
                JSONObject result = results.getJSONObject(a);

                String movieID = result.getString("id");
                movieIDArrayList.add(movieID);
            }

            return movieIDArrayList;
        }
        catch (JSONException  e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
