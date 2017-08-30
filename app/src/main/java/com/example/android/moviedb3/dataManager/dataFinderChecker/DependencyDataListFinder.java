package com.example.android.moviedb3.dataManager.dataFinderChecker;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.DependencyData;

import java.util.ArrayList;

/**
 * Created by nugroho on 27/08/17.
 */

public class DependencyDataListFinder<Data extends DependencyData> implements IDataFindCheck<ArrayList<Data>> {
    DataDB<Data> dataDB;
    String idData;

    public DependencyDataListFinder(DataDB<Data> dataDB, String idData) {
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
                if (data.getIDDependent().equals(idData))
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
                if(data.getIDDependent().equals(idData))
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

   /* ArrayList<DataDB> dataDBs;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    public DependencyDataListFinder(ArrayList<DataDB> dataDBs)
    {
        this.dataDBs = dataDBs;
    }

    public void CheckData(String idData, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener)
    {
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;

        SameID_IDListFindCheck deleteDataInAllList = new SameID_IDListFindCheck();
        deleteDataInAllList.execute(idData);
    }

    public class SameID_IDListFindCheck extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... params)
        {
            boolean isDataHasBeenDeleted = false;

            for (DataDB dataDB:dataDBs)
            {
                ArrayList<DependencyData> arrayList = dataDB.getAllData();

                for (DependencyData data:arrayList)
                {
                    if(data.getIDDependent().equals(params[0]))
                    {
//                        dataDB.removeData(data.getId());
                        isDataHasBeenDeleted = true;
                    }
                }
            }

            return isDataHasBeenDeleted;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            if(onAsyncTaskCompleteListener != null)
            {
                onAsyncTaskCompleteListener.onComplete(aBoolean);
            }
        }
    }*/

