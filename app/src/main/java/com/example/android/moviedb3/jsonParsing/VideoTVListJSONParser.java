package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.movieDB.VideoTVData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class VideoTVListJSONParser implements JSONParser<ArrayList<VideoTVData>>
{
    @Override
    public ArrayList<VideoTVData> Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        ArrayList<VideoTVData> videoDataArrayList = new ArrayList<>();
        String tvID;

        try
        {
            JSONArray videoList = jsonObject.getJSONArray("results");
            tvID = jsonObject.getString("id");
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

                String videoURL;
                String videoThumbnailURL;

                String videoID = video.getString("key");

                if(videoID == null || videoID.equals("null") || videoID.isEmpty())
                {
                    videoURL = "https://www.youtube.com/";
                    videoThumbnailURL = "";
                }
                else
                {
                    videoURL = "https://www.youtube.com/watch?v=" + video.getString("key");
                    videoThumbnailURL = "https://img.youtube.com/vi/" + video.getString("key") + "/0.jpg";
                }

                VideoTVData videoData = new VideoTVData(id, videoURL, videoThumbnailURL, tvID);
                videoDataArrayList.add(videoData);
            }

            return videoDataArrayList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
