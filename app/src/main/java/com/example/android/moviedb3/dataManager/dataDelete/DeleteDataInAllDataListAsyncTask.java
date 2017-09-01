package com.example.android.moviedb3.dataManager.dataDelete;

import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager.dataFinderChecker.DataFindCheck;
import com.example.android.moviedb3.dataManager.dataFinderChecker.SameID_DataListFindCheck;
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 01/09/17.
 */

public class DeleteDataInAllDataListAsyncTask<Data extends BaseData> extends AsyncTask<Void, Void, Void>
{
    String idData;
    ArrayList<DataDB<Data>> dataDBArrayList;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    public DeleteDataInAllDataListAsyncTask(String idData, ArrayList<DataDB<Data>> dataDBArrayList, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener) {
        this.idData = idData;
        this.dataDBArrayList = dataDBArrayList;
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        for (DataDB<Data> dataDB : dataDBArrayList)
        {
            if(DataFindCheck.CheckData(new SameID_DataListFindCheck<Data>(dataDB, idData)))
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