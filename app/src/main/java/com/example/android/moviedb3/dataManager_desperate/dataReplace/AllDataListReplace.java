package com.example.android.moviedb3.dataManager_desperate.dataReplace;

import android.os.AsyncTask;

import com.example.android.moviedb3.localDatabase.DataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 24/08/17.
 */

public class AllDataListReplace<Data> implements IReplaceData
{
    private ArrayList<Data> datas;
    private DataDB<Data> dataDB;

    public AllDataListReplace(ArrayList<Data> datas, DataDB<Data> dataDB) {
        this.datas = datas;
        this.dataDB = dataDB;
    }

    public void ReplaceDatabase()
    {
        SetDataInDatabaseAsyncTask<Data> dataSetDataInDatabaseAsyncTask = new SetDataInDatabaseAsyncTask<>(datas, dataDB);
        dataSetDataInDatabaseAsyncTask.execute();
    }

    private class SetDataInDatabaseAsyncTask<Data> extends AsyncTask<Void, Void, Void>
    {
        ArrayList<Data> datas;
        DataDB<Data> dataDB;

        public SetDataInDatabaseAsyncTask(ArrayList<Data> datas, DataDB<Data> dataDB) {
            this.datas = datas;
            this.dataDB = dataDB;
        }

        @Override
        protected Void doInBackground(Void... params) {

            if(datas != null)
            {
                dataDB.removeAllData();

                for (Data data:datas)
                {
                    dataDB.addData(data);
                }
            }

            return null;
        }
    }
}
