package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.GenreDataDB;
import com.example.android.moviedb3.localDatabase.TVGenreDB;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.TVGenre;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class DatabaseTVGenreGetter implements IMovieDBGetter
{
    Context context;
    OnDataObtainedListener<ArrayList<TVGenre>> onDataObtainedListener;

    public DatabaseTVGenreGetter(Context context, OnDataObtainedListener<ArrayList<TVGenre>> onDataObtainedListener)
    {
        this.context = context;
        this.onDataObtainedListener = onDataObtainedListener;
    }

    @Override
    public void getData()
    {
        DatabaseGenreGetterAsyncTask databaseGenreGetterAsyncTask = new DatabaseGenreGetterAsyncTask();
        databaseGenreGetterAsyncTask.execute();
    }

    private class DatabaseGenreGetterAsyncTask extends AsyncTask<Void, Void, ArrayList<TVGenre>>
    {
        @Override
        protected ArrayList<TVGenre> doInBackground(Void... params)
        {
            DataDB<TVGenre> genreDataDataDB = new TVGenreDB(context);
            return genreDataDataDB.getAllData();
        }

        @Override
        protected void onPostExecute(ArrayList<TVGenre> genreDataArrayList)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(genreDataArrayList);
            }
        }
    }
}
