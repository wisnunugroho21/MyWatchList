package com.example.android.moviedb3.movieDB;

/**
 * Created by nugroho on 12/09/17.
 */

public class GenreTvData extends IntermedieteData
{
    String idTV;
    String idGenre;

    public GenreTvData(String id, String idTV, String idGenre) {
        super(id);
        this.idTV = idTV;
        this.idGenre = idGenre;
    }

    public String getIdTV() {
        return idTV;
    }

    public String getIdGenre() {
        return idGenre;
    }

    @Override
    public String getFirstID() {
        return idTV;
    }

    @Override
    public String getSecondID() {
        return idGenre;
    }
}
