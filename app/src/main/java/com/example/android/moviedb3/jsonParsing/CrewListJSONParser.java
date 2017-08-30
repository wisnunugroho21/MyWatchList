package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.CrewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 11/08/17.
 */

public class CrewListJSONParser implements JSONParser<ArrayList<CrewData>>
{
    @Override
    public ArrayList<CrewData> Parse(JSONObject jsonObject) {
        ArrayList<CrewData> crewDataArrayList = new ArrayList<>();
        String movieID;

        try
        {
            JSONArray crewList = jsonObject.getJSONArray("crew");
            movieID = jsonObject.getString("id");
            int crewLength;

            crewLength = crewList.length();

            for(int a = 0; a < crewLength;  a++)
            {
                JSONObject cast = crewList.getJSONObject(a);

                String id = String.valueOf((int )(Math.random() * Integer.MAX_VALUE + 1000000));
                String crewName = cast.getString("name");
                String crewPosition = cast.getString("job");

                CrewData crewData = new CrewData(id, crewName, crewPosition, movieID);
                crewDataArrayList.add(crewData);
            }

            return crewDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
