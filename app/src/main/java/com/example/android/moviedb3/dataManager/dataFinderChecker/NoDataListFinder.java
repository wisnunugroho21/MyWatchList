package com.example.android.moviedb3.dataManager.dataFinderChecker;

import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.BaseData;

import java.util.ArrayList;

/**
 * Created by nugroho on 27/08/17.
 */

public class NoDataListFinder<Data extends BaseData> implements IDataFindCheck<ArrayList<String>> {
    ArrayList<String> idList;
    DataDB<Data> dataDB;

    public NoDataListFinder(ArrayList<String> idList, DataDB<Data> dataDB) {
        this.idList = idList;
        this.dataDB = dataDB;
    }

    @Override
    public ArrayList<String> CheckData() {
        ArrayList<Data> dataArrayList = dataDB.getAllData();
        ArrayList<String> idDatabaseList = new ArrayList<>();
        ArrayList<String> idDifferenceList = new ArrayList<>();

        if(dataArrayList != null)
        {
            for (Data data : dataArrayList) {
                idDatabaseList.add(data.getId());
            }

            for (String idDatabase : idDatabaseList) {
                boolean isSame = false;

                for (String id : idList) {
                    if (id.equals(idDatabase)) {
                        isSame = true;
                        break;
                    }
                }

                if (!isSame) {
                    idDifferenceList.add(idDatabase);
                }
            }
        }

        return idDifferenceList;
    }
}

    /*public void CheckData()
    {
        DataListCheckerAsyncTask<Data> dataListCheckerAsyncTask = new DataListCheckerAsyncTask<>(idList, dataDB, onDataObtainedListener);
        dataListCheckerAsyncTask.execute();
    }

    private static class DataListCheckerAsyncTask<Data extends BaseData> extends AsyncTask<Void, Void, ArrayList<String>>
    {
        ArrayList<String> idList;
        DataDB<Data> dataDB;
        OnDataObtainedListener<ArrayList<String>> onDataObtainedListener;

        public DataListCheckerAsyncTask(ArrayList<String> idList, DataDB<Data> dataDB, OnDataObtainedListener<ArrayList<String>> onDataObtainedListener)
        {
            this.idList = idList;
            this.dataDB = dataDB;
            this.onDataObtainedListener = onDataObtainedListener;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params)
        {
            ArrayList<Data> dataArrayList = dataDB.getAllData();
            ArrayList<String> idDatabaseList = new ArrayList<>();
            ArrayList<String> idDeletedLiest = new ArrayList<>();

            for (Data data:dataArrayList)
            {
                idDatabaseList.add(data.getId());
            }

            for (String idDatabase:idDatabaseList)
            {
                boolean isSame = false;

                for (String id:idList)
                {
                    if(id.equals(idDatabase))
                    {
                        isSame = true;
                        break;
                    }
                }

                if(!isSame)
                {
                    idDeletedLiest.add(idDatabase);
                }
            }

            return idDeletedLiest;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings)
        {
            onDataObtainedListener.onDataObtained(strings);
        }
    }*/

