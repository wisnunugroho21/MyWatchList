package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.JSONHTTPReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.JSONReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.JSONParser;
import com.example.android.moviedb3.jsonParsing.PeopleBackdropImageJSONParser;
import com.example.android.moviedb3.jsonParsing.PeopleDetailJSONParser;
import com.example.android.moviedb3.jsonParsing.PeoplePopularListJSONParser;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.PeopleCastDataDB;
import com.example.android.moviedb3.localDatabase.PeopleCastTVDataDB;
import com.example.android.moviedb3.localDatabase.PeopleCrewDataDB;
import com.example.android.moviedb3.localDatabase.PeopleCrewTVDataDB;
import com.example.android.moviedb3.localDatabase.PeopleDataDB;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.PeopleCastData;
import com.example.android.moviedb3.movieDB.PeopleCastTvData;
import com.example.android.moviedb3.movieDB.PeopleCrewData;
import com.example.android.moviedb3.movieDB.PeopleCrewTVData;
import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoIDDataFinder;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 06/09/17.
 */

public class PeopleDataGetterAsyncTask implements IMovieDBGetter {

    Context context;
    OnDataObtainedListener<ArrayList<PeopleData>> onDataObtainedListener;

    DataDB<PeopleData> peopleDataDB;
    String movieIDListURL;

    boolean allDataHasBeenObtained;

    public PeopleDataGetterAsyncTask(Context context, OnDataObtainedListener<ArrayList<PeopleData>> onDataObtainedListener) {
        this.context = context;
        this.onDataObtainedListener = onDataObtainedListener;

        this.peopleDataDB = new PeopleDataDB(context);
        allDataHasBeenObtained = false;
    }

    public void getData()
    {
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<PeopleData>>(new PeoplePopularListJSONParser(), new OnMovieIDListObtainedListener()), movieIDListURL);
    }

    private class OnMovieIDListObtainedListener implements OnDataObtainedListener<ArrayList<PeopleData>>
    {
        @Override
        public void onDataObtained(ArrayList<PeopleData> peopleDatas)
        {
            if (peopleDatas == null || !NetworkConnectionChecker.IsConnect(context))
            {
                PeopleDatabaseGetter peopleDatabaseGetter = new PeopleDatabaseGetter();
                peopleDatabaseGetter.execute();
            }
            else
            {
                PeopleNetworkGetterAsyncTask peopleNetworkGetterAsyncTask = new PeopleNetworkGetterAsyncTask(peopleDatas, new AllPeopleDataObtainedListener());
                peopleNetworkGetterAsyncTask.execute();
            }
        }
    }

    private class PeopleNetworkGetterAsyncTask extends AsyncTask<Void, Void, ArrayList<PeopleData>>
    {
        ArrayList<PeopleData> peopleList;
        OnDataObtainedListener<ArrayList<PeopleData>> onAllPeopleObtainedListener;

        public PeopleNetworkGetterAsyncTask(ArrayList<PeopleData> peopleList, OnDataObtainedListener<ArrayList<PeopleData>> onAllPeopleObtainedListener)
        {
            this.peopleList = peopleList;
            this.onAllPeopleObtainedListener = onAllPeopleObtainedListener;
        }

        @Override
        protected ArrayList<PeopleData> doInBackground(Void... params)
        {
            ArrayList<PeopleData> dataList = new ArrayList<>();
            JSONReceiver jsonReceiver = new JSONHTTPReceiver();

            for (PeopleData people:peopleList)
            {
                JSONParser<PeopleData> peopleDataJSONParser = new PeopleDetailJSONParser(people);
                JSONObject jsonObject = jsonReceiver.ReceiveData(MovieDataURL.GetPeopleDetailURL(people.getId(), context));
                PeopleData newPeopleData = peopleDataJSONParser.Parse(jsonObject);

                JSONParser<PeopleData> peopleBackdropImageJSONParser = new PeopleBackdropImageJSONParser(newPeopleData);
                JSONObject jsonObject2 = jsonReceiver.ReceiveData(MovieDataURL.GetBackdropImagePeopleURL(newPeopleData.getId(), context));
                dataList.add(peopleBackdropImageJSONParser.Parse(jsonObject2));
            }

            return  dataList;
        }

        @Override
        protected void onPostExecute(ArrayList<PeopleData> peopleDatas)
        {
            if(onAllPeopleObtainedListener != null)
            {
                onAllPeopleObtainedListener.onDataObtained(peopleDatas);
            }
        }
    }

    private class AllPeopleDataObtainedListener implements OnDataObtainedListener<ArrayList<PeopleData>>
    {
        @Override
        public void onDataObtained(ArrayList<PeopleData> peopleDatas)
        {
            if (peopleDatas != null && NetworkConnectionChecker.IsConnect(context))
            {
                DataReplaceAsyncTask dataReplaceAsyncTask = new DataReplaceAsyncTask(peopleDatas);
                dataReplaceAsyncTask.execute();
            }
            else
            {
                PeopleDatabaseGetter peopleDatabaseGetter = new PeopleDatabaseGetter();
                peopleDatabaseGetter.execute();
            }
        }
    }

    private class DataReplaceAsyncTask extends AsyncTask<Void, Void, Void>
    {
        ArrayList<PeopleData> datas;
        DataDB<PeopleData> dataDB;

        public DataReplaceAsyncTask(ArrayList<PeopleData> datas) {
            this.datas = datas;
            this.dataDB = new PeopleDataDB(context);
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ArrayList<String> networkPeopleIDList = new ArrayList<>();
            for (PeopleData people:datas)
            {
                networkPeopleIDList.add(people.getId());
            }

            ArrayList<String> willDeletedIdPeopleList = NoDataFinder.FindNotSameID
                    (new NoIDDataFinder<>(new BaseDataCompare<PeopleData>(), dataDB.getAllData(), networkPeopleIDList));

            for (String idPeople : willDeletedIdPeopleList)
            {
                DataDelete.Delete(new BaseDataListDelete<PeopleCastData>
                        (new DepedencyDataCompare<PeopleCastData>(), new PeopleCastDataDB(context), idPeople));

                DataDelete.Delete(new BaseDataListDelete<PeopleCrewData>
                        (new DepedencyDataCompare<PeopleCrewData>(), new PeopleCrewDataDB(context), idPeople));

                DataDelete.Delete(new BaseDataListDelete<PeopleCastTvData>
                        (new DepedencyDataCompare<PeopleCastTvData>(), new PeopleCastTVDataDB(context), idPeople));

                DataDelete.Delete(new BaseDataListDelete<PeopleCrewTVData>
                        (new DepedencyDataCompare<PeopleCrewTVData>(), new PeopleCrewTVDataDB(context), idPeople));
            }

            DataReplace.ReplaceData(new AllDataListReplace<PeopleData>(dataDB, datas));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            PeopleDatabaseGetter peopleDatabaseGetter = new PeopleDatabaseGetter();
            peopleDatabaseGetter.execute();
        }
    }

    private class PeopleDatabaseGetter extends AsyncTask<Void, Void, ArrayList<PeopleData>>
    {
        @Override
        protected ArrayList<PeopleData> doInBackground(Void... params)
        {
            DataDB<PeopleData> peopleDataDataDB = new PeopleDataDB(context);
            return peopleDataDataDB.getAllData();
        }

        @Override
        protected void onPostExecute(ArrayList<PeopleData> datas)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(datas);
            }
        }
    }
}

