package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.JSONParser;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CastTVData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.CrewTVData;
import com.example.android.moviedb3.movieDB.DependencyData;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameIDDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 13/09/17.
 */

public class PeopleInfoDataGetter<Data extends DependencyData> implements IMovieDBGetter
{
    OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener;
    DataDB<Data> dataDB;
    JSONParser<ArrayList<Data>> jsonParser;
    String URL;
    String peopleID;
    Context context;

    public PeopleInfoDataGetter(OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener, DataDB<Data> dataDB, JSONParser<ArrayList<Data>> jsonParser,
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
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<Data>>(jsonParser, new PeopleInfoObtainedListener()), URL);
    }

    private class PeopleInfoObtainedListener implements OnDataObtainedListener<ArrayList<Data>>
    {
        @Override
        public void onDataObtained(ArrayList<Data> datas)
        {
            if(datas == null || !NetworkConnectionChecker.IsConnect(context))
            {
                PeopleInfoDatabaseGetter movieInfoGetter = new PeopleInfoDatabaseGetter();
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

            if(databaseDataArrayList != null)
            {
                for (Data databaseData:databaseDataArrayList)
                {
                    if(peopleID.equals(databaseData.getIDDependent()))
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
            PeopleInfoDatabaseGetter peopleInfoDatabaseGetter = new PeopleInfoDatabaseGetter();
            peopleInfoDatabaseGetter.execute();
        }
    }

    private class PeopleInfoDatabaseGetter extends AsyncTask<Void, Void, ArrayList<Data>>
    {
        @Override
        protected ArrayList<Data> doInBackground(Void... params)
        {
            ArrayList<Data> dataArrayList = dataDB.getAllData();
            return SameDataFinder.getDataSameList
                    (new SameIDDataFinder<Data>(new DepedencyDataCompare<Data>(), dataArrayList, peopleID));
        }

        @Override
        protected void onPostExecute(ArrayList<Data> datas)
        {
            onDataObtainedListener.onDataObtained(datas);
        }
    }

}

