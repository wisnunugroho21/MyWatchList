package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.CastTVData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class CastTVListJSONParser implements JSONParser<ArrayList<CastTVData>>
{
    @Override
    public ArrayList<CastTVData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<CastTVData> castTVDataArrayList = new ArrayList<>();
        String tvID;

        try
        {
            JSONArray castList = jsonObject.getJSONArray("cast");
            tvID = jsonObject.getString("id");
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

                CastTVData castData = new CastTVData(id, castName, castCharacter, tvID, peopleID);
                castTVDataArrayList.add(castData);
            }

            return castTVDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
