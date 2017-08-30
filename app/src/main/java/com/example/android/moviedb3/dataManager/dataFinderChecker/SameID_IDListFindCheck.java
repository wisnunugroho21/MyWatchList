package com.example.android.moviedb3.dataManager.dataFinderChecker;

import com.example.android.moviedb3.localDatabase.DataDB;

import java.util.ArrayList;

/**
 * Created by nugroho on 27/08/17.
 */

public class SameID_IDListFindCheck implements IDataFindCheck<Boolean> {
    DataDB<String> dataDB;
    String idData;

    public SameID_IDListFindCheck(DataDB<String> dataDB, String idData) {
        this.dataDB = dataDB;
        this.idData = idData;
    }

    @Override
    public Boolean CheckData() {
        ArrayList<String> idList = dataDB.getAllData();

        if(idList != null)
        {
            for (String id : idList)
            {
                if (id.equals(idData))
                {
                    return true; //ternyata movieID masih ada di list favorite, top rate, dll
                }
            }
        }

        return false;
    }
}

    /*public void CheckData()
    {
        DataListCheckerAsyncTask dataListCheckerAsyncTask = new DataListCheckerAsyncTask();
        dataListCheckerAsyncTask.execute();
    }

    public class DataListCheckerAsyncTask extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... params)
        {
            ArrayList<String> idList = dataDB.getAllData();

            for (String id:idList)
            {
                if(id.equals(idData))
                {
                    return true; //ternyata movieID masih ada di list favorite, top rate, dll
                }
            }

//            parentDataDB.removeData(params[0]);
            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(b);
//                onAsyncTaskCompleteListener.onComplete(aBoolean);
            }
        }
    }*/

