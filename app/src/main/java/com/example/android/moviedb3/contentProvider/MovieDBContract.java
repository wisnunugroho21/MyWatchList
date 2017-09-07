package com.example.android.moviedb3.contentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.android.moviedb3.movieDB.BaseData;

/**
 * Created by nugroho on 26/07/17.
 */

public class MovieDBContract
{
    public static final String AUTHORITY = "com.example.android.moviedb3";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String MOVIE_DATA_PATH = "movieData";
    public static final String POPULAR_DATA_PATH = "popularData";
    public static final String TOP_RATE_DATA_PATH = "topRateData";
    public static final String NOW_SHOWING_DATA_PATH = "nowShowingData";
    public static final String COMING_SOON_DATA_PATH = "comingSoonData";
    public static final String FAVORITE_DATA_PATH = "favoriteData";
    public static final String WATCHLIST_DATA_PATH = "watchlistData";
    public static final String PLAN_TO_WATCH_LIST_DATA_PATH = "PlanToWatchListData";
    public static final String REVIEW_DATA_PATH = "reviewData";
    public static final String CAST_DATA_PATH = "castData";
    public static final String CREW_DATA_PATH = "crewData";
    public static final String VIDEO_DATA_PATH = "videoData";
    public static final String GENRE_DATA_PATH = "genreData";
    public static final String GENRE_MOVIE_POPULAR_DATA_PATH = "genreMoviePopularData";
    public static final String GENRE_MOVIE_TOP_RATE_DATA_PATH = "genreMovieTopRateData";
    public static final String PEOPLE_DATA_PATH = "peopleData";

    public static final class MovieDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_DATA_PATH).build();
        public static final String TABLE_MOVIE_DATA = "MovieData";
        public static final String COLUMN_ORIGINAL_TITLE= "originalTitle";
        public static final String COLUMN_MOVIE_POSTER_URL = "moviePosterURL";
        public static final String COLUMN_BACKDROP_IMAGE_URL = "backdropImageURL";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_VOTE_RATING = "voteRating";
        public static final String COLUMN_TAGLINE = "tagLine";
        public static final String COLUMN_PLOT_SYNOPSIS = "plotSynopsis";
    }

    public static final class PopularDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(POPULAR_DATA_PATH).build();
        public static final String TABLE_POPULAR_DATA = "PopularData";
        public static final String COLUMN_MOVIE_ID = "MovieId";
    }

    public static final class TopRateDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TOP_RATE_DATA_PATH).build();
        public static final String TABLE_TOP_RATE_DATA = "TopRateData";
        public static final String COLUMN_MOVIE_ID = "MovieId";
    }

    public static final class NowShowingDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(NOW_SHOWING_DATA_PATH).build();
        public static final String TABLE_NOW_SHOWING_DATA = "NowShowingData";
        public static final String COLUMN_MOVIE_ID = "MovieId";
    }

    public static final class ComingSoonDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(COMING_SOON_DATA_PATH).build();
        public static final String TABLE_COMING_SOON_DATA = "ComingSoonData";
        public static final String COLUMN_MOVIE_ID = "MovieId";
    }

    public static final class FavoriteDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(FAVORITE_DATA_PATH).build();
        public static final String TABLE_FAVORITE_DATA = "FavoriteData";
        public static final String COLUMN_MOVIE_ID = "MovieId";
    }

    public static class WatchlistDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(WATCHLIST_DATA_PATH).build();
        public static final String TABLE_WATCHLIST_DATA = "WatchlistData";
        public static final String COLUMN_MOVIE_ID = "MovieId";
    }

    public static class PlanToWatchlistDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PLAN_TO_WATCH_LIST_DATA_PATH).build();
        public static final String TABLE_PLAN_TO_WATCH_LIST_DATA = "PlanToWatchlistData";
        public static final String COLUMN_MOVIE_ID = "MovieId";
    }

    public static class CrewDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(CREW_DATA_PATH).build();
        public static final String TABLE_CREW_DATA = "CrewData";
        public static final String COLUMN_CREW_NAME = "CrewName";
        public static final String COLUMN_CREW_POSITION = "CrewPosition";
        public static final String COLUMN_MOVIE_ID = "MovieId";
        public static final String COLUMN_PEOPLE_ID = "PeopleID";
    }

    public static class CastDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(CAST_DATA_PATH).build();
        public static final String TABLE_CAST_DATA = "CastData";
        public static final String COLUMN_CAST_NAME = "CastName";
        public static final String COLUMN_CHARACTER_NAME = "CharacterName";
        public static final String COLUMN_MOVIE_ID = "MovieId";
        public static final String COLUMN_PEOPLE_ID = "PeopleID";
    }

    public static class ReviewDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(REVIEW_DATA_PATH).build();
        public static final String TABLE_REVIEW_DATA = "ReviewData";
        public static final String COLUMN_REVIEWER_NAME = "ReviewerName";
        public static final String COLUMN_REVIEW_CONTENT = "ReviewContent";
        public static final String COLUMN_MOVIE_ID = "MovieId";
    }

    public static class VideoDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(VIDEO_DATA_PATH).build();
        public static final String TABLE_VIDEO_DATA = "VideoData";
        public static final String COLUMN_VIDEO_URL = "VideoURL";
        public static final String COLUMN_VIDEO_THUMBNAIL_URL = "VideoThumbnailURL";
        public static final String COLUMN_MOVIE_ID = "MovieId";
    }

    public static class GenreDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(GENRE_DATA_PATH).build();
        public static final String TABLE_GENRE_DATA = "GenreData";
        public static final String COLUMN_GENRE_NAME = "GenreName";
    }

    public static class GenreMoviePopularEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(GENRE_MOVIE_POPULAR_DATA_PATH).build();
        public static final String TABLE_GENRE_MOVIE_POPULAR_DATA = "GenreMoviePopularData";
        public static final String COLUMN_MOVIE_ID = "MovieId";
        public static final String COLUMN_GENRE_ID = "GenreName";
    }

    public static class GenreMovieTopRateEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(GENRE_MOVIE_TOP_RATE_DATA_PATH).build();
        public static final String TABLE_GENRE_MOVIE_TOP_RATE_DATA = "GenreMovieTopRateData";
        public static final String COLUMN_MOVIE_ID = "MovieId";
        public static final String COLUMN_GENRE_ID = "GenreID";
    }

    public static class PeopleDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PEOPLE_DATA_PATH).build();
        public static final String TABLE_PEOPLE_DATA = "PeopleData";
        public static final String COLUMN_PEOPLE_NAME = "PeopleName";
        public static final String COLUMN_PLACE_OF_BIRTH = "PlaceOfBirth";
        public static final String COLUMN_BIRTHDAY = "Birthday";
        public static final String COLUMN_BIOGRAPHY = "Biography";
        public static final String COLUMN_PROFILE_IMAGE = "ProfileImage";
        public static final String COLUMN_OTHER_NAME = "OtherName";
        public static final String COLUMN_KNOWN_ROLES = "KnownRoles";
    }

}
