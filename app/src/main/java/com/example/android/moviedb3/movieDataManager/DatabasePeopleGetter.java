package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Contacts;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.GenreDataDB;
import com.example.android.moviedb3.localDatabase.PeopleDataDB;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.PeopleData;

import java.util.ArrayList;

/**
 * Created by nugroho on 13/09/17.
 */

public class DatabasePeopleGetter implements IMovieDBGetter
{
    Context context;
    OnDataObtainedListener<ArrayList<PeopleData>> onDataObtainedListener;

    public DatabasePeopleGetter(Context context, OnDataObtainedListener<ArrayList<PeopleData>> onDataObtainedListener)
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

    private class DatabaseGenreGetterAsyncTask extends AsyncTask<Void, Void, ArrayList<PeopleData>>
    {
        @Override
        protected ArrayList<PeopleData> doInBackground(Void... params)
        {
            DataDB<PeopleData> peopleDataDataDB = new PeopleDataDB(context);
            return peopleDataDataDB.getAllData();
        }

        @Override
        protected void onPostExecute(ArrayList<PeopleData> peopleDataArrayList)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(peopleDataArrayList);
            }
        }
    }
}
