package com.example.android.moviedb3.dataManager.dataDelete;

import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager.dataFinderChecker.DataFindCheck;
import com.example.android.moviedb3.dataManager.dataFinderChecker.SameID_IDListFindCheck;
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.localDatabase.DataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 01/09/17.
 */

public class DeleteDataInAllIDListAsyncTask extends AsyncTask<Void, Void, Void>
{
    String idData;
    ArrayList<DataDB<String>> IDListDataDB;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    public DeleteDataInAllIDListAsyncTask(String idData, ArrayList<DataDB<String>> IDListDataDB, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener) {
        this.idData = idData;
        this.IDListDataDB = IDListDataDB;
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        for (DataDB<String> dataDB : IDListDataDB)
        {
            if(DataFindCheck.CheckData(new SameID_IDListFindCheck(dataDB, idData)))
            {
                dataDB.removeData(idData);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        onAsyncTaskCompleteListener.onComplete(true);
    }
}
