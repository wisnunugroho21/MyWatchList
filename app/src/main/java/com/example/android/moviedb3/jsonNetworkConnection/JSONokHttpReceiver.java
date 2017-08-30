package com.example.android.moviedb3.jsonNetworkConnection;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nugroho on 01/08/17.
 */

public class JSONokHttpReceiver implements JSONReceiver
{
    @Override
    public JSONObject ReceiveData(String URL) {

        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();

            response.close();

            return new JSONObject(json);

        } catch (IOException | JSONException e)
        {
            return null;
        }
    }

    @Override
    public boolean CheckConnection(Context context) {
        return NetworkConnectionChecker.IsConnect(context);
    }
}
