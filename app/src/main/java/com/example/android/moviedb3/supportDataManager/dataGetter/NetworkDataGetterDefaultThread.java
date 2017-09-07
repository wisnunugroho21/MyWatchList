package com.example.android.moviedb3.supportDataManager.dataGetter;

import com.example.android.moviedb3.jsonNetworkConnection.JSONHTTPReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.JSONReceiver;
import com.example.android.moviedb3.jsonParsing.JSONParser;

import org.json.JSONObject;

/**
 * Created by nugroho on 07/09/17.
 */

public class NetworkDataGetterDefaultThread<Data> implements INetworkDataGetterDefaultThread<Data>
{
    private JSONReceiver jsonReceiver;
    private JSONParser<Data> jsonParser;

    public NetworkDataGetterDefaultThread(JSONParser<Data> jsonParser)
    {
        this.jsonReceiver = new JSONHTTPReceiver();
        this.jsonParser = jsonParser;
    }

    @Override
    public Data getData(String... url)
    {
        JSONObject jsonObject = jsonReceiver.ReceiveData(url[0]);
        return jsonParser.Parse(jsonObject);
    }
}
