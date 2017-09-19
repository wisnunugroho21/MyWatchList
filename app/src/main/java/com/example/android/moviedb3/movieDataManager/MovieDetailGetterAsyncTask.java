package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonParsing.MovieDetailJSONParser;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterDefaultThread;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataListGetterDefaultThread;

/**
 * Created by nugroho on 15/09/17.
 */

public class MovieDetailGetterAsyncTask
{
    String movieID;
    Context context;
    OnDataObtainedListener<MovieData> onDataObtainedListener;
    NetworkMovieDetailAsyncTask networkMovieDetailAsyncTask;

    public MovieDetailGetterAsyncTask(String movieID, Context context, OnDataObtainedListener<MovieData> onDataObtainedListener) {
        this.movieID = movieID;
        this.context = context;
        this.onDataObtainedListener = onDataObtainedListener;
    }

    public void GetData()
    {
        if(networkMovieDetailAsyncTask == null)
        {
            networkMovieDetailAsyncTask = new NetworkMovieDetailAsyncTask(movieID, context, onDataObtainedListener);
            networkMovieDetailAsyncTask.execute();
        }

        else
        {
            networkMovieDetailAsyncTask.cancel(true);
            networkMovieDetailAsyncTask = null;
            GetData();
        }
    }

    public void CancelGettingData()
    {
        networkMovieDetailAsyncTask.cancel(true);
        networkMovieDetailAsyncTask = null;
    }

    private class NetworkMovieDetailAsyncTask extends AsyncTask<Void, Void, MovieData>
    {
        String movieID;
        Context context;
        OnDataObtainedListener<MovieData> onDataObtainedListener;

        public NetworkMovieDetailAsyncTask(String movieID, Context context, OnDataObtainedListener<MovieData> onDataObtainedListener) {
            this.movieID = movieID;
            this.context = context;
            this.onDataObtainedListener = onDataObtainedListener;
        }

        @Override
        protected MovieData doInBackground(Void... params)
        {
            String movieURL = MovieDataURL.GetMovieURL(movieID, context);
            return NetworkDataGetter.GetDataDefaultThread(new NetworkDataGetterDefaultThread<MovieData>(new MovieDetailJSONParser()), movieURL);
        }

        @Override
        protected void onPostExecute(MovieData movieData)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(movieData);
            }
        }
    }
}
