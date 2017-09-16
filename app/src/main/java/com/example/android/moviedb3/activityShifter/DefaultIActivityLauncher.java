package com.example.android.moviedb3.activityShifter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;

/**
 * Created by nugroho on 23/08/17.
 */

public class DefaultIActivityLauncher implements IActivityLauncher
{
    Class targetActivity;
    Context context;
    Bundle bundle;
    int requestCode = 0;
    Activity activity;

    public DefaultIActivityLauncher(Class targetActivity, Context context) {
        this.targetActivity = targetActivity;
        this.context = context;
    }

    public DefaultIActivityLauncher(Class targetActivity, Context context, Bundle bundle) {
        this.targetActivity = targetActivity;
        this.context = context;
        this.bundle = bundle;
    }

    public DefaultIActivityLauncher(Class targetActivity, Bundle bundle, int requestCode, Activity activity) {
        this.targetActivity = targetActivity;
        this.bundle = bundle;
        this.requestCode = requestCode;
        this.activity = activity;
    }

    public DefaultIActivityLauncher(Class targetActivity, int requestCode, Activity activity) {
        this.targetActivity = targetActivity;
        this.requestCode = requestCode;
        this.activity = activity;
    }

    @Override
    public void LaunchActivity()
    {
        Intent intent;

        if(activity != null)
        {
            intent = new Intent(activity, targetActivity);
        }

        else
        {
            intent = new Intent(context, targetActivity);
        }

        if(bundle != null)
        {
            intent.putExtras(bundle);
        }

        if(activity != null)
        {
            activity.startActivityForResult(intent, requestCode);
        }

        else
        {
            context.startActivity(intent);
        }
    }


}
