package com.example.android.moviedb3.jsonParsing;

import android.util.Log;

import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVDetailJSONParser implements JSONParser<TVData>
{
    @Override
    public TVData Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        try
        {
            String tvID = jsonObject.getString("id");

            String originalTitle = jsonObject.getString("name");
            if(originalTitle == null || originalTitle.equals("null") || originalTitle.isEmpty())
            {
                originalTitle = jsonObject.getString("original_name");
            }

            String tvPosterURL = jsonObject.getString("poster_path");
            if(tvPosterURL == null || tvPosterURL.equals("null") || tvPosterURL.isEmpty())
            {
                tvPosterURL = "";
            }

            String backdropImageURL = jsonObject.getString("backdrop_path");
            if(backdropImageURL == null || backdropImageURL.equals("null") || backdropImageURL.isEmpty())
            {
                backdropImageURL = "";
            }

            ArrayList<String> genreList = new ArrayList<>();
            JSONArray genres = jsonObject.getJSONArray("genres");

            for (int a = 0; a < genres.length(); a++)
            {
                if(genres.get(a) == null)
                {
                    genreList.add("");
                }

                else
                {
                    JSONObject genre = genres.getJSONObject(a);
                    String titleGenre = genre.getString("name");

                    if(titleGenre == null || titleGenre.equals("null") || titleGenre.isEmpty())
                    {
                        genreList.add("");
                    }

                    else
                    {
                        genreList.add(titleGenre + " ");
                    }
                }
            }

            String episodeString = jsonObject.getString("number_of_episodes");
            int episode;
            if(episodeString == null || episodeString.equals("null") || episodeString.isEmpty())
            {
                episode = 0;
            }
            else
            {
                episode = Integer.valueOf(episodeString);
            }

            String seasonString = jsonObject.getString("number_of_seasons");
            int season;
            if(seasonString == null || seasonString.equals("null") || seasonString.isEmpty())
            {
                season = 0;
            }
            else
            {
                season = Integer.valueOf(seasonString);
            }

            String releaseDateString = jsonObject.getString("first_air_date");
            Calendar firstReleaseDate;
            if(releaseDateString == null || releaseDateString.isEmpty() || releaseDateString.equals("null"))
            {
                firstReleaseDate = new GregorianCalendar(2000, 1, 1);
            }
            else
            {
                StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
                firstReleaseDate = stringToDateSetter.getDateTime(releaseDateString);
            }

            String voteRatingString = jsonObject.getString("vote_average");
            double voteRating;
            if(voteRatingString == null || voteRatingString.equals("null") || voteRatingString.isEmpty())
            {
                voteRating = 0;
            }
            else
            {
                voteRating = Double.valueOf(voteRatingString);
            }

            String plotSynopsis = jsonObject.getString("overview");
            if(plotSynopsis == null || plotSynopsis.equals("null") || plotSynopsis.isEmpty())
            {
                plotSynopsis = "";
            }

            ArrayList<String> networkList = new ArrayList<>();
            JSONArray networks = jsonObject.getJSONArray("networks");

            for (int a = 0; a < networks.length(); a++)
            {
                if(networks.get(a) == null)
                {
                    networkList.add("");
                }

                else
                {
                    JSONObject network = networks.getJSONObject(a);
                    String titleNetwork = network.getString("name");

                    if(titleNetwork == null || titleNetwork.equals("null") || titleNetwork.isEmpty())
                    {
                        networkList.add("");
                    }

                    else
                    {
                        networkList.add(titleNetwork + ", ");
                    }
                }
            }

            String seriesStatus = jsonObject.getString("status");
            if(seriesStatus == null || seriesStatus.equals("null") || seriesStatus.isEmpty())
            {
                seriesStatus = "";
            }

            return new TVData(tvID, originalTitle, tvPosterURL, backdropImageURL, genreList,
                    episode, season, firstReleaseDate, voteRating, plotSynopsis, networkList, seriesStatus);
        }
        catch (JSONException e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }
}

