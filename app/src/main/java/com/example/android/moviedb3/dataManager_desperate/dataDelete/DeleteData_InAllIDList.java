package com.example.android.moviedb3.dataManager_desperate.dataDelete;

import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager_desperate.dataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_IDListCheck;
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.localDatabase.DataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class DeleteData_InAllIDList implements IDataDelete
{
    String idData;
    ArrayList<DataDB<String>> IDListDataDB;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    @Override
    public void DeleteData()
    {
        DeleteDataInAllIDListAsyncTask deleteDataInAllIDListAsyncTask = new DeleteDataInAllIDListAsyncTask(idData, IDListDataDB, onAsyncTaskCompleteListener);
        deleteDataInAllIDListAsyncTask.execute();
    }

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
                if(DataCheck.CheckData(new SameID_IDListCheck(dataDB, idData)))
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
