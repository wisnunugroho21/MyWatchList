package com.example.android.moviedb3.dataManager.movieDBGetter;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.dataManager.dataGetter.NetworkDataGetterSyncTask;
import com.example.android.moviedb3.dataManager.dataReplace.ReplaceData;
import com.example.android.moviedb3.dataManager.dataReplace.SameDependencyDataListReplace;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.JSONParser;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.DependencyData;

import java.util.ArrayList;

/**
 * Created by nugroho on 30/08/17.
 */

public class MovieInfoDataGetter<Data extends DependencyData> implements IMovieDBGetter
{
    OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener;
    DataDB<Data> dataDB;
    JSONParser<ArrayList<Data>> jsonParser;
    String URL;
    String movieID;
    Context context;

    public MovieInfoDataGetter(OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener, DataDB<Data> dataDB, JSONParser<ArrayList<Data>> jsonParser,
                               String URL, String movieID, Context context) {
        this.onDataObtainedListener = onDataObtainedListener;
        this.dataDB = dataDB;
        this.jsonParser = jsonParser;
        this.URL = URL;
        this.movieID = movieID;
        this.context = context;
    }

    public void getData()
    {
        NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<Data>>(jsonParser, new MovieInfoObtainedListener()), URL);
    }

    private class MovieInfoObtainedListener implements OnDataObtainedListener<ArrayList<Data>>
    {
        @Override
        public void onDataObtained(ArrayList<Data> datas)
        {
            if(datas == null || !NetworkConnectionChecker.IsConnect(context))
            {
                MovieInfoDatabaseGetter movieInfoGetter = new MovieInfoDatabaseGetter();
                movieInfoGetter.execute();
            }

            else
            {
                DataReplaceAsyncTask dataReplaceAsyncTask = new DataReplaceAsyncTask(datas, dataDB);
                dataReplaceAsyncTask.execute();
            }
        }
    }

    private class DataReplaceAsyncTask extends AsyncTask<Void, Void, Void>
    {
        ArrayList<Data> datas;
        DataDB<Data> dataDB;

        public DataReplaceAsyncTask(ArrayList<Data> datas, DataDB<Data> dataDB) {
            this.datas = datas;
            this.dataDB = dataDB;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ReplaceData.Replace(new SameDependencyDataListReplace<Data>(datas, dataDB));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            MovieInfoDatabaseGetter movieInfoDatabaseGetter = new MovieInfoDatabaseGetter();
            movieInfoDatabaseGetter.execute();
        }
    }

    private class MovieInfoDatabaseGetter extends AsyncTask<Void, Void, ArrayList<Data>>
    {
        @Override
        protected ArrayList<Data> doInBackground(Void... params)
        {
            ArrayList<Data> dataArrayList = dataDB.getAllData();
            ArrayList<Data> expectedDataArrayList = new ArrayList<>();

            if(dataArrayList != null)
            {
                for (Data data:dataArrayList)
                {
                    if(movieID.equals(data.getIDDependent()))
                    {
                        expectedDataArrayList.add(data);
                    }
                }
            }

            return expectedDataArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Data> datas)
        {
            onDataObtainedListener.onDataObtained(datas);
        }
    }

}
