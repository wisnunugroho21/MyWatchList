package com.example.android.moviedb3.movieDB;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.moviedb3.movieDB.dateToString.DateToNumericDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nugroho on 04/07/17.
 */

public class MovieData extends BaseData implements Parcelable
{
    private String originalTitle;
    private String moviePosterURL;
    private String backdropImageURL;
    private String genre;
    private int duration;
    private Calendar releaseDate;
    private double voteRating;
    private String tagline;
    private String plotSypnosis;

//    private String releaseDateString;


    public MovieData(String id, String originalTitle, String moviePosterURL, String backdropImageURL,
                     ArrayList<String> genreList, int duration, Calendar releaseDate,
                     double voteRating, String tagline, String plotSypnosis)
    {
        super(id);

        this.originalTitle = originalTitle;
        this.moviePosterURL = moviePosterURL;
        this.backdropImageURL = backdropImageURL;

        this.genre = "";
        for (String mGenre:genreList)
        {
            this.genre += mGenre;
        }

        this.duration = duration;
        this.releaseDate = releaseDate;
        this.voteRating = voteRating;
        this.tagline = tagline;
        this.plotSypnosis = plotSypnosis;
    }

    public MovieData(String id, String originalTitle, String moviePosterURL, String backdropImageURL,
                    String genreList, int duration, Calendar releaseDate,
                    double voteRating, String tagline, String plotSypnosis)
    {
        super(id);

        this.originalTitle = originalTitle;
        this.moviePosterURL = moviePosterURL;
        this.backdropImageURL = backdropImageURL;
        this.genre = genreList;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.voteRating = voteRating;
        this.tagline = tagline;
        this.plotSypnosis = plotSypnosis;
    }

    public MovieData(String id, String originalTitle, String moviePosterURL, double voteRating) {
        super(id);
        this.originalTitle = originalTitle;
        this.moviePosterURL = moviePosterURL;
        this.genre = "";
        this.voteRating = voteRating;
    }

    public MovieData(Parcel in)
    {
        super(in.readString());
        this.originalTitle = in.readString();
        this.moviePosterURL = in.readString();
        this.backdropImageURL = in.readString();
        this.genre = in.readString();
        this.duration = in.readInt();

        StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
        this.releaseDate = stringToDateSetter.getDateTime(in.readString());

        this.voteRating = in.readDouble();
        this.tagline = in.readString();
        this.plotSypnosis = in.readString();
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getMoviePosterFullURL() {
        return "http://image.tmdb.org/t/p/w342/" + moviePosterURL;
    }

    public String getMoviePosterURL() {
        return moviePosterURL;
    }

    public String getPlotSypnosis() {
        return plotSypnosis;
    }

    public double getVoteRating() {
        return voteRating;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public String getSmallMoviePosterURL() {
        return "http://image.tmdb.org/t/p/w185/" + moviePosterURL;
    }

    public String getBackdropImageFullURL() {
        return "http://image.tmdb.org/t/p/w500/" + backdropImageURL;
    }

    public String getBackdropImageURL() {
        return backdropImageURL;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public String getTagline() {
        return tagline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.getId());
        dest.writeString(this.getOriginalTitle());
        dest.writeString(this.moviePosterURL);
        dest.writeString(this.backdropImageURL);
        dest.writeString(this.getGenre());
        dest.writeInt(this.getDuration());

        DateToStringSetter dateToStringSetter = new DateToNumericDateStringSetter();
        dest.writeString(dateToStringSetter.getDateString(this.getReleaseDate()));

        dest.writeDouble(this.getVoteRating());
        dest.writeString(this.getTagline());
        dest.writeString(this.getPlotSypnosis());
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new MovieData[size];
        }
    };


}