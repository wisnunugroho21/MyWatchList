package com.example.android.moviedb3.dataManager.dataGetter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by nugroho on 23/08/17.
 */

public class NetworkImageGetterSync extends INetworkDataGetter<Bitmap>
{
    Context context;

    public NetworkImageGetterSync(OnDataObtainedListener<Bitmap> onDataObtainedListener, Context context) {

        super(onDataObtainedListener);
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        try
        {
            return Picasso.with(context).load(params[0]).get();

        } catch (IOException e)
        {
            Toast.makeText(context, "Fail to load image", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
