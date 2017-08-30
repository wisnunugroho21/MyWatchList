package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nugroho on 27/07/17.
 */

public class MovieDetailJSONParser implements JSONParser<MovieData>
{
    @Override
    public MovieData Parse(JSONObject jsonObject) {

        try
        {
            String movieID = jsonObject.getString("id");
            String originalTitle = jsonObject.getString("original_title");
            String moviePosterURL = jsonObject.getString("poster_path");
            String backdropImageURL = jsonObject.getString("backdrop_path");

            ArrayList<String> genreList = new ArrayList<>();
            JSONArray genres = jsonObject.getJSONArray("genres");

            for (int a = 0; a < genres.length(); a++)
            {
                JSONObject genre = genres.getJSONObject(a);

                String titleGenre = genre.getString("name");

                genreList.add(titleGenre + " ");
            }

            int duration = jsonObject.getInt("runtime");

            String releaseDateString = jsonObject.getString("release_date");
            StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
            Calendar releaseDate = stringToDateSetter.getDateTime(releaseDateString);

            double voteRating = jsonObject.getDouble("vote_average");
            String tagline = jsonObject.getString("tagline");
            String plotSynopsis = jsonObject.getString("overview");

            return new MovieData(movieID, originalTitle, moviePosterURL, backdropImageURL, genreList,
                    duration, releaseDate, voteRating, tagline, plotSynopsis);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
