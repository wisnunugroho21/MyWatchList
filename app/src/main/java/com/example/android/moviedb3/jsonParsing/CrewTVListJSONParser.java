package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.CrewTVData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class CrewTVListJSONParser implements JSONParser<ArrayList<CrewTVData>>
{
    @Override
    public ArrayList<CrewTVData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<CrewTVData> crewDataArrayList = new ArrayList<>();
        String tvID;

        try
        {
            JSONArray crewList = jsonObject.getJSONArray("crew");
            tvID = jsonObject.getString("id");
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

                CrewTVData crewData = new CrewTVData(id, crewName, crewPosition, tvID, peopleID, imageCrew);
                crewDataArrayList.add(crewData);
            }

            return crewDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
