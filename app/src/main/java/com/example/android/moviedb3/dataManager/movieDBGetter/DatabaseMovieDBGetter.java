package com.example.android.moviedb3.dataManager.movieDBGetter;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.movieDB.MovieData;

import java.util.ArrayList;

/**
 * Created by nugroho on 31/08/17.
 */

public class DatabaseMovieDBGetter implements IMovieDBGetter
{
    DataDB<MovieData> movieDB;
    DataDB<String> movieListDB;
    OnDataObtainedListener<ArrayList<MovieData>> onDataObtainedListener;

    public DatabaseMovieDBGetter(DataDB<String> movieListDB, Context context, OnDataObtainedListener<ArrayList<MovieData>> onDataObtainedListener) {
        this.movieDB = new MovieDataDB(context);
        this.movieListDB = movieListDB;
        this.onDataObtainedListener = onDataObtainedListener;
    }

    @Override
    public void getData()
    {
        DatabaseMovieDBGetterAsyncTask databaseMovieDBGetterAsyncTask = new DatabaseMovieDBGetterAsyncTask();
        databaseMovieDBGetterAsyncTask.execute();
    }

    private class DatabaseMovieDBGetterAsyncTask extends AsyncTask<Void, Void, ArrayList<MovieData>>
    {
        @Override
        protected ArrayList<MovieData> doInBackground(Void... params) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<MovieData> movieDatas = movieDB.getAllData();
            ArrayList<String> idMovies = movieListDB.getAllData();

            ArrayList<MovieData> expectedMovieDatas = new ArrayList<>();

            if(movieDatas != null && idMovies != null)
            {
                for (String idMovie:idMovies)
                {
                    for (MovieData movieData:movieDatas)
                    {
                        if(idMovie.equals(movieData.getId()))
                        {
                            expectedMovieDatas.add(movieData);
                            break;
                        }
                    }
                }
            }

            return expectedMovieDatas;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieDatas)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(movieDatas);
            }
        }
    }
}