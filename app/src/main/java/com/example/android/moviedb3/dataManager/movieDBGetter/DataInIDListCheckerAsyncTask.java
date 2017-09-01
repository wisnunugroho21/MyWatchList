package com.example.android.moviedb3.dataManager.movieDBGetter;

import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager.dataFinderChecker.DataFindCheck;
import com.example.android.moviedb3.dataManager.dataFinderChecker.SameID_IDListFindCheck;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;

/**
 * Created by nugroho on 01/09/17.
 */

public class DataInIDListCheckerAsyncTask
{
    OnDataObtainedListener<Boolean> onDataObtainedListener;
    DataDB<String> dataDB;
    String idData;

    public DataInIDListCheckerAsyncTask(OnDataObtainedListener<Boolean> onDataObtainedListener, DataDB<String> dataDB, String idData) {
        this.onDataObtainedListener = onDataObtainedListener;
        this.dataDB = dataDB;
        this.idData = idData;
    }

    public void Execute()
    {
        CheckMovieInIDList checkMovieInIDList = new CheckMovieInIDList();
        checkMovieInIDList.execute();
    }

    private class CheckMovieInIDList extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... params)
        {
            return DataFindCheck.CheckData(new SameID_IDListFindCheck(dataDB, idData));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            onDataObtainedListener.onDataObtained(aBoolean);
        }
    }


}
