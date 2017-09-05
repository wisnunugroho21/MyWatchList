package com.example.android.moviedb3.dataManager_desperate.dataDelete;

import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager_desperate.dataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_DataListCheck;
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class DeleteData_InAllDataList<Data extends BaseData> implements IDataDelete
{
    String idData;
    ArrayList<DataDB<Data>> dataDBArrayList;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    @Override
    public void DeleteData()
    {
        DeleteDataInAllDataListAsyncTask<Data> deleteDataInAllDataListAsyncTask = new DeleteDataInAllDataListAsyncTask<Data>(idData, dataDBArrayList, onAsyncTaskCompleteListener);
        deleteDataInAllDataListAsyncTask.execute();
    }

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
                if(DataCheck.CheckData(new SameID_DataListCheck<Data>(dataDB, idData)))
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
}
