package com.example.android.moviedb3.jsonNetworkConnection;

import android.content.Context;

/**
 * Created by nugroho on 03/07/17.
 */

interface DataNetworkReceiver<DataType>
{
    DataType ReceiveData(String URL);
    boolean CheckConnection(Context context);
}
