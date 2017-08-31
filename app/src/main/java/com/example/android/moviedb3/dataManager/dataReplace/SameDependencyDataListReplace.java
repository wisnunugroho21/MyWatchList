package com.example.android.moviedb3.dataManager.dataReplace;

import android.os.AsyncTask;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.DependencyData;

import java.util.ArrayList;

/**
 * Created by nugroho on 28/08/17.
 */

public class SameDependencyDataListReplace<Data extends DependencyData> implements IReplaceData
{
    ArrayList<Data> datas;
    DataDB<Data> database;

    public SameDependencyDataListReplace(ArrayList<Data> datas, DataDB<Data> database) {
        this.datas = datas;
        this.database = database;
    }

    public void ReplaceDatabase()
    {
        SetDataInDatabaseAsyncTask<Data> setDataInDatabaseAsyncTask = new SetDataInDatabaseAsyncTask<>(datas, database);
        setDataInDatabaseAsyncTask.execute();
    }

    private class SetDataInDatabaseAsyncTask<Data extends DependencyData> extends AsyncTask<Void, Void, Void>
    {
        ArrayList<Data> datas;
        DataDB<Data> database;

        public SetDataInDatabaseAsyncTask(ArrayList<Data> datas, DataDB<Data> database)
        {
            this.datas = datas;
            this.database = database;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ArrayList<Data> dataDBList = database.getAllData();

            if(datas != null && !datas.isEmpty())
            {
                if(dataDBList != null)
                {
                    for (Data dataDB:dataDBList)
                    {
                        if(datas.get(0).getIDDependent().equals(dataDB.getIDDependent()))
                        {
                            database.removeData(dataDB.getId());
                        }
                    }
                }


                for (Data data:datas)
                {
                    database.addData(data);
                }
            }

            return null;
        }
    }
}
