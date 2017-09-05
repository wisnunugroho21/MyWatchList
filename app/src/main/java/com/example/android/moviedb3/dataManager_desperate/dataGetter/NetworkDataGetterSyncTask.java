package com.example.android.moviedb3.dataManager_desperate.dataGetter;

import android.support.annotation.NonNull;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.JSONHTTPReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.JSONReceiver;
import com.example.android.moviedb3.jsonParsing.JSONParser;

import org.json.JSONObject;

/**
 * Created by nugroho on 29/07/17.
 */

public class NetworkDataGetterSyncTask<Data> extends INetworkDataGetter<Data>
{
    private JSONReceiver jsonReceiver;
    private JSONParser<Data> jsonParser;

    public NetworkDataGetterSyncTask(@NonNull JSONParser<Data> jsonParser, @NonNull OnDataObtainedListener<Data> onDataObtainedListener) {

        super(onDataObtainedListener);

        this.jsonReceiver = new JSONHTTPReceiver();
        this.jsonParser = jsonParser;
    }

    @Override
    protected Data doInBackground(String... params) {
        try
        {
            String url = params[0];

            JSONObject jsonObject = jsonReceiver.ReceiveData(url);
            return jsonParser.Parse(jsonObject);

        } catch (NullPointerException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
