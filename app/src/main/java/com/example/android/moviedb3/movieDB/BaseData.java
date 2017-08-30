package com.example.android.moviedb3.movieDB;

/**
 * Created by nugroho on 26/07/17.
 */

public abstract class BaseData {

    private String id;

    public BaseData(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
