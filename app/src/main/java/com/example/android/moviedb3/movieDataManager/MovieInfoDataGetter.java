package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;


import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.JSONParser;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.DependencyData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameIDDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameDataFinder;

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
    String peopleID;
    Context context;

    public MovieInfoDataGetter(OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener, DataDB<Data> dataDB, JSONParser<ArrayList<Data>> jsonParser,
                               String URL, String peopleID, Context context) {
        this.onDataObtainedListener = onDataObtainedListener;
        this.dataDB = dataDB;
        this.jsonParser = jsonParser;
        this.URL = URL;
        this.peopleID = peopleID;
        this.context = context;
    }

    public void getData()
    {
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<Data>>(jsonParser, new MovieInfoObtainedListener()), URL);
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
            ArrayList<Data> databaseDataArrayList = dataDB.getAllData();

            for (Data data:datas)
            {
                for (Data databaseData:databaseDataArrayList)
                {
                    if(data.getIDDependent().equals(databaseData.getIDDependent()))
                    {
                        dataDB.removeData(databaseData.getId());
                    }
                }
            }

            for (Data data:datas)
            {
                dataDB.addData(data);
            }

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
            return SameDataFinder.getDataSameList
                    (new SameIDDataFinder<Data>(new BaseDataCompare<Data>(), dataDB.getAllData(), peopleID));
        }

        @Override
        protected void onPostExecute(ArrayList<Data> datas)
        {
            onDataObtainedListener.onDataObtained(datas);
        }
    }

}
