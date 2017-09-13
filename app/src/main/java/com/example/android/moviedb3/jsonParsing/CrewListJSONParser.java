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

        if(jsonObject == null)
        {
            return null;
        }

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

                String peopleID = cast.getString("id");
                String crewName = cast.getString("name");
                if(crewName == null || crewName.equals("null") || crewName.isEmpty())
                {
                    crewName = "";
                }

                String crewPosition = cast.getString("job");
                if(crewPosition == null || crewPosition.equals("null") || crewPosition.isEmpty())
                {
                    crewPosition = "";
                }

                String imageCrew = cast.getString("profile_path");
                if(imageCrew == null || imageCrew.equals("null") || imageCrew.isEmpty())
                {
                    imageCrew = "";
                }

                CrewData crewData = new CrewData(id, crewName, crewPosition, movieID, peopleID, imageCrew);
                crewDataArrayList.add(crewData);
            }

            return crewDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
