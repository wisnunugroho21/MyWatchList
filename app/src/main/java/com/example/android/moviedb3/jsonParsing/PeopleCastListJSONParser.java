package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.PeopleCastData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleCastListJSONParser implements JSONParser<ArrayList<PeopleCastData>>
{
    @Override
    public ArrayList<PeopleCastData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<PeopleCastData> peopleCastDataArrayList = new ArrayList<>();
        String peopleID;

        try
        {
            JSONArray castList = jsonObject.getJSONArray("cast");
            peopleID = jsonObject.getString("id");
            int castLenght;

            castLenght = castList.length();

            for(int a = 0; a < castLenght;  a++)
            {
                JSONObject cast = castList.getJSONObject(a);

                String id = String.valueOf((int )(Math.random() * Integer.MAX_VALUE + 1000000));

                String movieID = cast.getString("id");
                String movieTitle = cast.getString("title");
                if(movieTitle == null || movieTitle.equals("null") || movieTitle.isEmpty())
                {
                    movieTitle = "";
                }

                String castCharacter = cast.getString("character");
                if(castCharacter == null || castCharacter.equals("null") || castCharacter.isEmpty())
                {
                    castCharacter = "";
                }

                String imageMovie = cast.getString("poster_path");
                if(imageMovie == null || imageMovie.equals("null") || imageMovie.isEmpty())
                {
                    imageMovie = "";
                }

                PeopleCastData peopleCastData = new PeopleCastData(id, movieTitle, castCharacter, movieID, peopleID, imageMovie);
                peopleCastDataArrayList.add(peopleCastData);
            }

            return peopleCastDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
