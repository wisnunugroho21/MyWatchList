package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.GenreData;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreListJSONParser implements JSONParser<ArrayList<GenreData>>
{
    @Override
    public ArrayList<GenreData> Parse(JSONObject jsonObject) {
        return null;
    }
}
