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
 * Created by nugroho on 11/09/17.
 */

public class TVData extends BaseData implements Parcelable
{
    private String originalTitle;
    private String tvPosterURL;
    private String backdropImageURL;
    private String genre;
    private int episode;
    private int season;
    private Calendar firstReleaseDate;
    private double voteRating;
    private String plotSypnosis;
    private String availableOnNetwork;
    private String seriesStatus;

    public TVData(String id, String originalTitle, String tvPosterURL, String backdropImageURL, String genre,
                  int episode, int season, Calendar firstReleaseDate, double voteRating, String plotSypnosis, String availableOnNetwork, String seriesStatus) {
        super(id);
        this.originalTitle = originalTitle;
        this.tvPosterURL = tvPosterURL;
        this.backdropImageURL = backdropImageURL;
        this.genre = genre;
        this.episode = episode;
        this.season = season;
        this.firstReleaseDate = firstReleaseDate;
        this.voteRating = voteRating;
        this.plotSypnosis = plotSypnosis;
        this.availableOnNetwork = availableOnNetwork;
        this.seriesStatus = seriesStatus;
    }

    public TVData(String id, String originalTitle, String tvPosterURL, String backdropImageURL, ArrayList<String> genreList,
                  int episode, int season, Calendar firstReleaseDate, double voteRating, String plotSypnosis, ArrayList<String> availableOnNetworkList, String seriesStatus) {
        super(id);
        this.originalTitle = originalTitle;
        this.tvPosterURL = tvPosterURL;
        this.backdropImageURL = backdropImageURL;

        this.genre = "";
        for (String mGenre:genreList)
        {
            this.genre += mGenre;
        }

        this.episode = episode;
        this.season = season;
        this.firstReleaseDate = firstReleaseDate;
        this.voteRating = voteRating;
        this.plotSypnosis = plotSypnosis;

        this.availableOnNetwork = "";
        for (String mNetwork:availableOnNetworkList)
        {
            this.availableOnNetwork += mNetwork;
        }


        this.seriesStatus = seriesStatus;
    }

    public TVData(Parcel in)
    {
        super(in.readString());
        this.originalTitle = in.readString();
        this.tvPosterURL = in.readString();
        this.backdropImageURL = in.readString();
        this.genre = in.readString();
        this.episode = in.readInt();
        this.season = in.readInt();

        StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
        this.firstReleaseDate = stringToDateSetter.getDateTime(in.readString());

        this.voteRating = in.readDouble();
        this.plotSypnosis = in.readString();
        this.availableOnNetwork = in.readString();
        this.seriesStatus = in.readString();
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getMoviePosterFullURL() {
        return "http://image.tmdb.org/t/p/w342/" + tvPosterURL;
    }

    public String getTvPosterURL() {
        return tvPosterURL;
    }

    public String getPlotSypnosis() {
        return plotSypnosis;
    }

    public double getVoteRating() {
        return voteRating;
    }

    public Calendar getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public String getSmallMoviePosterURL() {
        return "http://image.tmdb.org/t/p/w185/" + tvPosterURL;
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

    public int getEpisode() {
        return episode;
    }

    public int getSeason() {
        return season;
    }

    public String getAvailableOnNetwork() {
        return availableOnNetwork;
    }

    public String getSeriesStatus() {
        return seriesStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.getId());
        dest.writeString(this.getOriginalTitle());
        dest.writeString(this.tvPosterURL);
        dest.writeString(this.backdropImageURL);
        dest.writeString(this.getGenre());
        dest.writeInt(this.getEpisode());
        dest.writeInt(this.getSeason());

        DateToStringSetter dateToStringSetter = new DateToNumericDateStringSetter();
        dest.writeString(dateToStringSetter.getDateString(this.getFirstReleaseDate()));

        dest.writeDouble(this.getVoteRating());
        dest.writeString(this.getPlotSypnosis());
        dest.writeString(this.getAvailableOnNetwork());
        dest.writeString(this.getSeriesStatus());
    }

    public static final Creator CREATOR = new Creator()
    {
        @Override
        public Object createFromParcel(Parcel source) {
            return new TVData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new TVData[size];
        }
    };


}
