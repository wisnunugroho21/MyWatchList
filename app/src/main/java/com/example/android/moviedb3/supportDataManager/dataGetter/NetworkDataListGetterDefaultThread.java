package com.example.android.moviedb3.supportDataManager.dataGetter;

import com.example.android.moviedb3.jsonNetworkConnection.JSONHTTPReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.JSONReceiver;
import com.example.android.moviedb3.jsonParsing.JSONParser;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 07/09/17.
 */

public class NetworkDataListGetterDefaultThread<Data> implements INetworkDataGetterDefaultThread<ArrayList<Data>>
{
    private JSONReceiver jsonReceiver;
    private JSONParser<Data> jsonParser;

    public NetworkDataListGetterDefaultThread(JSONParser<Data> jsonParser) {
        this.jsonReceiver = new JSONHTTPReceiver();
        this.jsonParser = jsonParser;
    }

    @Override
    public ArrayList<Data> getData(String... urlist)
    {
        ArrayList<Data> dataList = new ArrayList<>();

        for (String url:urlist)
        {
            JSONObject jsonObject = jsonReceiver.ReceiveData(url);
            dataList.add(jsonParser.Parse(jsonObject));
        }

        return  dataList;
    }
}
