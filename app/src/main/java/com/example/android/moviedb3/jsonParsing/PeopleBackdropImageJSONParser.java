package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nugroho on 15/09/17.
 */

public class PeopleBackdropImageJSONParser  implements JSONParser<PeopleData>
{
    PeopleData peopleData;

    public PeopleBackdropImageJSONParser(PeopleData peopleData) {
        this.peopleData = peopleData;
    }

    @Override
    public PeopleData Parse(JSONObject jsonObject)
    {
        if(jsonObject == null)
        {
            return null;
        }

        if(peopleData != null)
        {
            try
            {
                JSONArray results = jsonObject.getJSONArray("results");
                if(results.length() > 0)
                {
                    for(int a = 0; a < results.length(); a++)
                    {
                        JSONObject result = results.getJSONObject(a);
                        double aspect_ratio = result.getDouble("aspect_ratio");

                        if(aspect_ratio > 1.7)
                        {
                            String imageBackdropPath = result.getString("file_path");
                            peopleData.setBackdropImageURL(imageBackdropPath);
                            break;
                        }
                    }
                }

                return peopleData;

            } catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        else
        {
            return null;
        }

    }
}
