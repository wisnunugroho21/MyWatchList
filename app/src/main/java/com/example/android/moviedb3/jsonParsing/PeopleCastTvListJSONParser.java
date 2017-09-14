package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.PeopleCastData;
import com.example.android.moviedb3.movieDB.PeopleCastTvData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 14/09/17.
 */

public class PeopleCastTvListJSONParser implements JSONParser<ArrayList<PeopleCastTvData>>
{
    @Override
    public ArrayList<PeopleCastTvData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<PeopleCastTvData> peopleCastDataArrayList = new ArrayList<>();
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

                String tvID = cast.getString("id");
                String tvTitle = cast.getString("name");
                if(tvTitle == null || tvTitle.equals("null") || tvTitle.isEmpty())
                {
                    tvTitle = "";
                }

                String castCharacter = cast.getString("character");
                if(castCharacter == null || castCharacter.equals("null") || castCharacter.isEmpty())
                {
                    castCharacter = "";
                }

                String imageTV = cast.getString("poster_path");
                if(imageTV == null || imageTV.equals("null") || imageTV.isEmpty())
                {
                    imageTV = "";
                }

                PeopleCastTvData peopleCastTvData = new PeopleCastTvData(id, tvTitle, castCharacter, tvID, peopleID, imageTV);
                peopleCastDataArrayList.add(peopleCastTvData);
            }

            return peopleCastDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
