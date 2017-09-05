package com.example.android.moviedb3.dataManager_desperate;

import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;

/**
 * Created by nugroho on 31/08/17.
 */

public class LoadingDataAsyncTask<Data> extends AsyncTask<Void, Void, Data>
{
    Data data;
    OnDataObtainedListener<Data> dataOnDataObtainedListener;

    public LoadingDataAsyncTask(Data data, OnDataObtainedListener<Data> dataOnDataObtainedListener)
    {
        this.data = data;
        this.dataOnDataObtainedListener = dataOnDataObtainedListener;
    }

    @Override
    protected Data doInBackground(Void... params)
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onPostExecute(Data data)
    {
        if(dataOnDataObtainedListener != null)
        {
            dataOnDataObtainedListener.onDataObtained(data);
        }
    }
}
