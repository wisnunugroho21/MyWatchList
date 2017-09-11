package com.example.android.moviedb3.jsonParsing;

import android.database.SQLException;
import android.util.Log;

import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nugroho on 27/07/17.
 */

public class MovieDetailJSONParser implements JSONParser<MovieData>
{
    @Override
    public MovieData Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        try
        {
            String movieID = jsonObject.getString("id");

            String originalTitle = jsonObject.getString("title");
            if(originalTitle == null || originalTitle.equals("null") || originalTitle.isEmpty())
            {
                originalTitle = "";
            }

            String moviePosterURL = jsonObject.getString("poster_path");
            if(moviePosterURL == null || moviePosterURL.equals("null") || moviePosterURL.isEmpty())
            {
                moviePosterURL = "";
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

            String durationString = jsonObject.getString("runtime");
            int duration;
            if(durationString == null || durationString.equals("null") || durationString.isEmpty())
            {
                duration = 0;
            }
            else
            {
                duration = Integer.valueOf(durationString);
            }

            String releaseDateString = jsonObject.getString("release_date");
            Calendar releaseDate;
            if(releaseDateString == null || releaseDateString.isEmpty() || releaseDateString.equals("null"))
            {
                releaseDate = new GregorianCalendar(2000, 1, 1);
            }
            else
            {
                StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
                releaseDate = stringToDateSetter.getDateTime(releaseDateString);
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

            String tagline = jsonObject.getString("tagline");
            if(tagline == null || tagline.equals("null") || tagline.isEmpty())
            {
                tagline = "";
            }

            String plotSynopsis = jsonObject.getString("overview");
            if(plotSynopsis == null || plotSynopsis.equals("null") || plotSynopsis.isEmpty())
            {
                plotSynopsis = "";
            }

            return new MovieData(movieID, originalTitle, moviePosterURL, backdropImageURL, genreList,
                    duration, releaseDate, voteRating, tagline, plotSynopsis);

        }
        catch (JSONException e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }
}
