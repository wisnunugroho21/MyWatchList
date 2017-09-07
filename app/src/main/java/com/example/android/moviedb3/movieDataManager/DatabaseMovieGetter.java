package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;


import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameIDDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 31/08/17.
 */

public class DatabaseMovieGetter implements IMovieDBGetter
{
    DataDB<MovieData> movieDB;
    DataDB<String> movieListDB;
    OnDataObtainedListener<ArrayList<MovieData>> onDataObtainedListener;

    public DatabaseMovieGetter(DataDB<String> movieListDB, Context context, OnDataObtainedListener<ArrayList<MovieData>> onDataObtainedListener) {
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

            return SameDataFinder.getDataSameList(new SameIDDataFinder<MovieData>
                    (new BaseDataCompare<MovieData>(), movieDatas, idMovies));
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