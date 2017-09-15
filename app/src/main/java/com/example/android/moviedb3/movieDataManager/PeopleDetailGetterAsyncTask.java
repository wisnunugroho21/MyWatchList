package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.JSONHTTPReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.JSONReceiver;
import com.example.android.moviedb3.jsonParsing.JSONParser;
import com.example.android.moviedb3.jsonParsing.PeopleBackdropImageJSONParser;
import com.example.android.moviedb3.jsonParsing.PeopleDetailJSONParser;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.PeopleData;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 15/09/17.
 */

public class PeopleDetailGetterAsyncTask
{
    OnDataObtainedListener<PeopleData> onPeopleObtainedListener;
    String idPeople;
    Context context;

    public PeopleDetailGetterAsyncTask(OnDataObtainedListener<PeopleData> onPeopleObtainedListener, String idPeople, Context context) {
        this.onPeopleObtainedListener = onPeopleObtainedListener;
        this.idPeople = idPeople;
        this.context = context;
    }

    public void getData()
    {
        PeopleNetworkGetterAsyncTask peopleNetworkGetterAsyncTask = new PeopleNetworkGetterAsyncTask(idPeople, onPeopleObtainedListener, context);
        peopleNetworkGetterAsyncTask.execute();
    }


    private class PeopleNetworkGetterAsyncTask extends AsyncTask<Void, Void, PeopleData>
    {
        OnDataObtainedListener<PeopleData> onPeopleObtainedListener;
        String idPeople;
        Context context;

        public PeopleNetworkGetterAsyncTask(String idPeople, OnDataObtainedListener<PeopleData> onPeopleObtainedListener, Context context)
        {
            this.idPeople = idPeople;
            this.onPeopleObtainedListener = onPeopleObtainedListener;
            this.context = context;
        }

        @Override
        protected PeopleData doInBackground(Void... params)
        {
            JSONReceiver jsonReceiver = new JSONHTTPReceiver();

            JSONParser<PeopleData> peopleDataJSONParser = new PeopleDetailJSONParser();
            JSONObject jsonObject = jsonReceiver.ReceiveData(MovieDataURL.GetPeopleDetailURL(idPeople, context));
            PeopleData newPeopleData = peopleDataJSONParser.Parse(jsonObject);

            JSONParser<PeopleData> peopleBackdropImageJSONParser = new PeopleBackdropImageJSONParser(newPeopleData);
            JSONObject jsonObject2 = jsonReceiver.ReceiveData(MovieDataURL.GetBackdropImagePeopleURL(newPeopleData.getId(), context));
            return  peopleBackdropImageJSONParser.Parse(jsonObject2);
        }

        @Override
        protected void onPostExecute(PeopleData peopleData)
        {
            if(onPeopleObtainedListener != null)
            {
                onPeopleObtainedListener.onDataObtained(peopleData);
            }
        }
    }
}


