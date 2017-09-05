package com.example.android.moviedb3.dataManager_desperate.DataCheck;

import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;

/**
 * Created by nugroho on 01/09/17.
 */

public class DataCheckAsyncTask
{
    public static <Data>void Execute(OnDataObtainedListener<Data> onDataObtainedListener, IDataCheck<Data> dataCheck, String idData)
    {
        CheckMovieInIDList<Data> checkMovieInIDList = new CheckMovieInIDList<>(onDataObtainedListener, dataCheck, idData);
        checkMovieInIDList.execute();
    }

    private static class CheckMovieInIDList<Data> extends AsyncTask<Void, Void, Data>
    {
        OnDataObtainedListener<Data> onDataObtainedListener;
        IDataCheck<Data> dataCheck;
        String idData;

        public CheckMovieInIDList(OnDataObtainedListener<Data> onDataObtainedListener, IDataCheck<Data> dataCheck, String idData) {
            this.onDataObtainedListener = onDataObtainedListener;
            this.dataCheck = dataCheck;
        }

        @Override
        protected Data doInBackground(Void... params)
        {
//            return DataCheck.CheckData(dataCheck, idData);
            return null;
        }

        @Override
        protected void onPostExecute(Data data) {
            super.onPostExecute(data);
        }
    }


}
