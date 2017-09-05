/*
package com.example.android.moviedb3.dataManager_desperate.DataCheck;

import java.util.ArrayList;

*/
/**
 * Created by nugroho on 27/08/17.
 *//*


public class SameID_IDListCheck implements IDataCheck<String> {
    ArrayList<String> comparisonIDArrayList;

    public SameID_IDListCheck(ArrayList<String> comparisonIDArrayList) {
        this.comparisonIDArrayList = comparisonIDArrayList;
    }

    @Override
    public String CheckData(String idData) {

        if(comparisonIDArrayList != null && !comparisonIDArrayList.isEmpty())
        {
            for (String id : comparisonIDArrayList)
            {
                if (id.equals(idData))
                {
                    return id; //ternyata movieID masih ada di list favorite, top rate, dll
                }
            }
        }

        return null;
    }
}

    */
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
    }*//*


*/
