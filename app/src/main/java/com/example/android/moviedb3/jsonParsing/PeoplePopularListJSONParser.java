package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.PeopleData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 06/09/17.
 */

public class PeoplePopularListJSONParser implements JSONParser<ArrayList<PeopleData>>
{
    @Override
    public ArrayList<PeopleData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<PeopleData> peopleDataArrayList = new ArrayList<>();

        try
        {
            JSONArray results = jsonObject.getJSONArray("results");

            for(int a = 0; a < results.length(); a++)
            {
                JSONObject result = results.getJSONObject(a);

                String id = result.getString("id");
                JSONArray known_for_array_list = result.getJSONArray("known_for");

                String known_for = "";

                for(int b = 0; b < known_for_array_list.length(); b++)
                {
                    JSONObject known_for_json_object = known_for_array_list.getJSONObject(b);
                    known_for += known_for_json_object.getString("title");
                }

                PeopleData peopleData = new PeopleData(id, known_for);
                peopleDataArrayList.add(peopleData);
            }

            return peopleDataArrayList;

        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
