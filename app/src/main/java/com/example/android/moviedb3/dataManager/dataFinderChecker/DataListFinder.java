package com.example.android.moviedb3.dataManager.dataFinderChecker;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 29/08/17.
 */

public class DataListFinder<Data extends BaseData> implements IDataFindCheck<ArrayList<Data>> {
    DataDB<Data> dataDB;
    String idData;

    public DataListFinder(DataDB<Data> dataDB, String idData) {
        this.dataDB = dataDB;
        this.idData = idData;
    }

    @Override
    public ArrayList<Data> CheckData() {
        ArrayList<Data> dataDBArrayList = dataDB.getAllData();
        ArrayList<Data> sameDataArrayList = new ArrayList<>();

        if(dataDBArrayList != null)
        {
            for (Data data : dataDBArrayList)
            {
                if (data.getId().equals(idData))
                {
                    sameDataArrayList.add(data);
                }
            }
        }

        return sameDataArrayList;
    }
}

    /*public void CheckData()
    {
        DataListCheckerAsyncTask dataListCheckerAsyncTask = new DataListCheckerAsyncTask();
        dataListCheckerAsyncTask.execute();
    }

    public class DataListCheckerAsyncTask extends AsyncTask<Void, Void, ArrayList<Data>>
    {
        @Override
        protected ArrayList<Data> doInBackground(Void... params)
        {
            ArrayList<Data> dataDBArrayList = dataDB.getAllData();
            ArrayList<Data> sameDataArrayList = new ArrayList<>();

            for (Data data:dataDBArrayList)
            {
                if(data.getId().equals(idData))
                {
                    sameDataArrayList.add(data);
                }
            }

            return sameDataArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Data> datas)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(datas);
            }
        }
    }*/

