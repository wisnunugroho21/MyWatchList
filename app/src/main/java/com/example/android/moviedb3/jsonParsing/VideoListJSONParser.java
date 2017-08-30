package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.VideoData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 11/08/17.
 */

public class VideoListJSONParser implements JSONParser<ArrayList<VideoData>>
{
    @Override
    public ArrayList<VideoData> Parse(JSONObject jsonObject) {
        ArrayList<VideoData> videoDataArrayList = new ArrayList<>();
        String movieID;

        try
        {
            JSONArray videoList = jsonObject.getJSONArray("results");
            movieID = jsonObject.getString("id");
            int videoListLength;

            if(videoList.length() > 3)
            {
                videoListLength = 3;
            }

            else
            {
                videoListLength = videoList.length();
            }

            for(int a = 0; a < videoListLength;  a++)
            {
                JSONObject video = videoList.getJSONObject(a);

                String id = String.valueOf((int )(Math.random() * Integer.MAX_VALUE + 1000000));
                String videoURL = "https://www.youtube.com/watch?v=" + video.getString("key");
                String videoThumbnailURL = "https://img.youtube.com/vi/" + video.getString("key") + "/0.jpg";

                VideoData videoData = new VideoData(id, videoURL, videoThumbnailURL, movieID);
                videoDataArrayList.add(videoData);
            }

            return videoDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
