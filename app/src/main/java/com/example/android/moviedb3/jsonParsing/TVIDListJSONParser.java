package com.example.android.moviedb3.jsonParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVIDListJSONParser implements JSONParser<ArrayList<String>>
{
    @Override
    public ArrayList<String> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<String> tvIDArrayList = new ArrayList<>();

        try
        {
            JSONArray results = jsonObject.getJSONArray("results");

            for(int a = 0; a < results.length(); a++)
            {
                JSONObject result = results.getJSONObject(a);

                String tvID = result.getString("id");
                tvIDArrayList.add(tvID);
            }

            return tvIDArrayList;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}

