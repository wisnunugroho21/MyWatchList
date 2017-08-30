package com.example.android.moviedb3.jsonNetworkConnection;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by nugroho on 03/07/17.
 */

public class JSONHTTPReceiver implements JSONReceiver
{
    @Override
    public JSONObject ReceiveData(String stringURL)
    {
        try
        {
            URL url = new URL(stringURL);
            HttpURLConnection urlConnection;

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            urlConnection.disconnect();

            if (hasInput)
            {
                return new JSONObject(scanner.next());
            }

        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean CheckConnection(Context context)
    {
        return NetworkConnectionChecker.IsConnect(context);
    }


}
