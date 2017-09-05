package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.GenreDataDB;
import com.example.android.moviedb3.movieDB.GenreData;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class DatabaseGenreGetter implements IMovieDBGetter
{
    Context context;
    OnDataObtainedListener<ArrayList<GenreData>> onDataObtainedListener;

    public DatabaseGenreGetter(Context context, OnDataObtainedListener<ArrayList<GenreData>> onDataObtainedListener)
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

    private class DatabaseGenreGetterAsyncTask extends AsyncTask<Void, Void, ArrayList<GenreData>>
    {
        @Override
        protected ArrayList<GenreData> doInBackground(Void... params)
        {
            DataDB<GenreData> genreDataDataDB = new GenreDataDB(context);
            return genreDataDataDB.getAllData();
        }

        @Override
        protected void onPostExecute(ArrayList<GenreData> genreDataArrayList)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(genreDataArrayList);
            }
        }
    }
}
