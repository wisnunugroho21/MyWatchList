package com.example.android.moviedb3.dataManager.dataGetter;

import android.support.annotation.NonNull;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.JSONHTTPReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.JSONReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.JSONokHttpReceiver;
import com.example.android.moviedb3.jsonParsing.JSONParser;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 23/08/17.
 */

public class NetworkDataListGetterSyncTask<Data> extends INetworkDataGetter<ArrayList<Data>>
{
    private JSONReceiver jsonReceiver;
    private JSONParser<Data> jsonParser;

    public NetworkDataListGetterSyncTask(@NonNull JSONParser<Data> jsonParser, @NonNull OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener) {

        super(onDataObtainedListener);
        this.jsonReceiver = new JSONHTTPReceiver();
        this.jsonParser = jsonParser;
    }

    @Override
    protected ArrayList<Data> doInBackground(String... params) {
        try
        {
            ArrayList<Data> dataList = new ArrayList<>();

            for (String url:params)
            {
                JSONObject jsonObject = jsonReceiver.ReceiveData(url);
                dataList.add(jsonParser.Parse(jsonObject));
            }

            return  dataList;

        } catch (NullPointerException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
