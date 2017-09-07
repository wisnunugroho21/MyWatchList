package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.CastData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 11/08/17.
 */

public class CastListJSONParser implements JSONParser<ArrayList<CastData>>
{
    @Override
    public ArrayList<CastData> Parse(JSONObject jsonObject) {

        ArrayList<CastData> castDataArrayList = new ArrayList<>();
        String movieID;

        try
        {
            JSONArray castList = jsonObject.getJSONArray("cast");
            movieID = jsonObject.getString("id");
            int castLenght;

            castLenght = castList.length();

            for(int a = 0; a < castLenght;  a++)
            {
                JSONObject cast = castList.getJSONObject(a);

                String id = String.valueOf((int )(Math.random() * Integer.MAX_VALUE + 1000000));

                String peopleID = cast.getString("id");
                String castName = cast.getString("name");
                if(castName == null || castName.equals("null") || castName.isEmpty())
                {
                    castName = "";
                }

                String castCharacter = cast.getString("character");
                if(castCharacter == null || castCharacter.equals("null") || castCharacter.isEmpty())
                {
                    castCharacter = "";
                }

                CastData castData = new CastData(id, castName, castCharacter, movieID, peopleID);
                castDataArrayList.add(castData);
            }

            return castDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
