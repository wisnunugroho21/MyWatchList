package com.example.android.moviedb3.dataManager_desperate.dataGetter;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nugroho on 23/08/17.
 */

public class BundleDataGetter {

    Bundle bundle;

    public BundleDataGetter(Bundle bundle) {
        this.bundle = bundle;
    }

    public <Data extends Parcelable> Data getData(String dataKey)
    {
        if(bundle.containsKey(dataKey))
        {
            return bundle.getParcelable(dataKey);
        }

        else
        {
            return null;
        }
    }

    public <Data extends Parcelable> ArrayList<Data> getDataList(String dataKey)
    {

        if(bundle.containsKey(dataKey))
        {
            return bundle.getParcelableArrayList(dataKey);
        }

        else
        {
            return null;
        }
    }
}
