package com.example.android.moviedb3.jsonParsing;

import org.json.JSONObject;

/**
 * Created by nugroho on 03/07/17.
 */

public interface JSONParser<DataType>
{
    DataType Parse(JSONObject jsonObject);
}
