package com.example.android.moviedb3.movieDB;

/**
 * Created by nugroho on 03/09/17.
 */

public class GenreMovieData extends IntermedieteData
{
    String idMovie;
    String idGenre;

    public GenreMovieData(String id, String idMovie, String idGenre) {
        super(id);
        this.idMovie = idMovie;
        this.idGenre = idGenre;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public String getIdGenre() {
        return idGenre;
    }

    @Override
    public String getFirstID() {
        return idMovie;
    }

    @Override
    public String getSecondID() {
        return idGenre;
    }
}
