package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.PeopleCrewData;
import com.example.android.moviedb3.movieDB.PeopleCrewTVData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleCrewTVListJSONParser implements JSONParser<ArrayList<PeopleCrewTVData>>
{
    @Override
    public ArrayList<PeopleCrewTVData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<PeopleCrewTVData> peopleCrewTVDataArrayList = new ArrayList<>();
        String peopleID ;

        try
        {
            JSONArray crewList = jsonObject.getJSONArray("crew");
            peopleID = jsonObject.getString("id");
            int crewLength;

            crewLength = crewList.length();

            for(int a = 0; a < crewLength;  a++)
            {
                JSONObject cast = crewList.getJSONObject(a);

                String id = String.valueOf((int )(Math.random() * Integer.MAX_VALUE + 1000000));

                String movieID = cast.getString("id");
                String tvName = cast.getString("name");
                if(tvName == null || tvName.equals("null") || tvName.isEmpty())
                {
                    tvName = "";
                }

                String crewPosition = cast.getString("job");
                if(crewPosition == null || crewPosition.equals("null") || crewPosition.isEmpty())
                {
                    crewPosition = "";
                }

                String imageMovie = cast.getString("poster_path");
                if(imageMovie == null || imageMovie.equals("null") || imageMovie.isEmpty())
                {
                    imageMovie = "";
                }

                PeopleCrewTVData crewData = new PeopleCrewTVData(id, tvName, crewPosition, movieID, peopleID, imageMovie);
                peopleCrewTVDataArrayList.add(crewData);
            }

            return peopleCrewTVDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
