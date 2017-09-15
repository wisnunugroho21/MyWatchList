package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.TVDataDB;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameIDDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class DatabaseTVGetter implements IMovieDBGetter
{
    DataDB<TVData> tvDB;
    DataDB<String> tvListDB;
    OnDataObtainedListener<ArrayList<TVData>> onDataObtainedListener;

    public DatabaseTVGetter(DataDB<String> tvListDB, Context context, OnDataObtainedListener<ArrayList<TVData>> onDataObtainedListener) {
        this.tvDB = new TVDataDB(context);
        this.tvListDB = tvListDB;
        this.onDataObtainedListener = onDataObtainedListener;
    }

    @Override
    public void getData()
    {
        DatabaseMovieDBGetterAsyncTask databaseMovieDBGetterAsyncTask = new DatabaseMovieDBGetterAsyncTask();
        databaseMovieDBGetterAsyncTask.execute();
    }

    private class DatabaseMovieDBGetterAsyncTask extends AsyncTask<Void, Void, ArrayList<TVData>>
    {
        @Override
        protected ArrayList<TVData> doInBackground(Void... params) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<TVData> tvDatas = tvDB.getAllData();
            ArrayList<String> idTV = tvListDB.getAllData();

            ArrayList<TVData> expectedTvList =  SameDataFinder.getDataSameList(new SameIDDataFinder<TVData>
                    (new BaseDataCompare<TVData>(), tvDatas, idTV));

            return expectedTvList;
        }

        @Override
        protected void onPostExecute(ArrayList<TVData> tvDatas)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(tvDatas);
            }
        }
    }
}
