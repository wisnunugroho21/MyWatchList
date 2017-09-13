package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.ReviewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 27/07/17.
 */

public class ReviewListJSONParser implements JSONParser<ArrayList<ReviewData>>
{
    @Override
    public ArrayList<ReviewData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<ReviewData> reviewDataArrayList = new ArrayList<>();

        try
        {
            JSONArray results = jsonObject.getJSONArray("results");
            String movieId = jsonObject.getString("id");

            for(int a = 0; a < results.length(); a++)
            {
                JSONObject result = results.getJSONObject(a);

                String id = String.valueOf((int )(Math.random() * Integer.MAX_VALUE + 1000000));

                String reviewerName = result.getString("author");
                if(reviewerName == null || reviewerName.equals("null") || reviewerName.isEmpty())
                {
                    reviewerName = "";
                }

                String reviewContent = result.getString("content");
                if(reviewContent == null || reviewContent.equals("null") || reviewContent.isEmpty())
                {
                    reviewContent = "";
                }

                ReviewData reviewData = new ReviewData(id, reviewerName, reviewContent, movieId, "");
                reviewDataArrayList.add(reviewData);
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return reviewDataArrayList;
    }
}
