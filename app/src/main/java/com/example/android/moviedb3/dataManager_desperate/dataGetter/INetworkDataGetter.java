package com.example.android.moviedb3.dataManager_desperate.dataGetter;

import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;

/**
 * Created by nugroho on 23/08/17.
 */

public abstract class INetworkDataGetter<Data> extends AsyncTask<String, Void, Data>
{
    OnDataObtainedListener<Data> onDataObtainedListener;

    public INetworkDataGetter(OnDataObtainedListener<Data> onDataObtainedListener) {
        this.onDataObtainedListener = onDataObtainedListener;
    }

    @Override
    protected void onPostExecute(Data data) {
        super.onPostExecute(data);

        onDataObtainedListener.onDataObtained(data);
    }
}
