package com.example.android.moviedb3.jsonParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nugroho on 27/07/17.
 */

public class VideoURLJSONParser implements JSONParser<String>
{
    @Override
    public String Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        try
        {
            JSONArray results = jsonObject.getJSONArray("results");
            JSONObject result = results.getJSONObject(0);

            return result.getString("key");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
