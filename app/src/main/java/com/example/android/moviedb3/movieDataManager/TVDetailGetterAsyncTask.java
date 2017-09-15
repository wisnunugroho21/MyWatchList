package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonParsing.MovieDetailJSONParser;
import com.example.android.moviedb3.jsonParsing.TVDetailJSONParser;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterDefaultThread;

/**
 * Created by nugroho on 15/09/17.
 */

public class TVDetailGetterAsyncTask
{
    String tvID;
    Context context;
    OnDataObtainedListener<TVData> onDataObtainedListener;

    public TVDetailGetterAsyncTask(String tvID, Context context, OnDataObtainedListener<TVData> onDataObtainedListener) {
        this.tvID = tvID;
        this.context = context;
        this.onDataObtainedListener = onDataObtainedListener;
    }

    public void GetData()
    {
        NetworkTVDetailAsyncTask networkTVDetailAsyncTask = new NetworkTVDetailAsyncTask(tvID, context, onDataObtainedListener);
        networkTVDetailAsyncTask.execute();
    }

    private class NetworkTVDetailAsyncTask extends AsyncTask<Void, Void, TVData>
    {
        String tvID;
        Context context;
        OnDataObtainedListener<TVData> onDataObtainedListener;

        public NetworkTVDetailAsyncTask(String tvID, Context context, OnDataObtainedListener<TVData> onDataObtainedListener) {
            this.tvID = tvID;
            this.context = context;
            this.onDataObtainedListener = onDataObtainedListener;
        }

        @Override
        protected TVData doInBackground(Void... params)
        {
            String tvURL = MovieDataURL.GetTVURL(tvID, context);
            return NetworkDataGetter.GetDataDefaultThread(new NetworkDataGetterDefaultThread<TVData>(new TVDetailJSONParser()), tvURL);
        }

        @Override
        protected void onPostExecute(TVData tvData)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(tvData);
            }
        }
    }
}
