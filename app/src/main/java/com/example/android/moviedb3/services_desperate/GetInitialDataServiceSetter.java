package com.example.android.moviedb3.services_desperate;

import android.app.job.JobInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;

import com.example.android.moviedb3.activity.MovieListActivity;
import com.example.android.moviedb3.activity.SplashActivity;
import com.example.android.moviedb3.activityShifter.ActivityLauncher;
import com.example.android.moviedb3.activityShifter.DefaultIActivityLauncher;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by nugroho on 07/09/17.
 */

public class GetInitialDataServiceSetter
{
    private static final int SYNC_INTERVAL_HOURS = 1;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 2;

    private static boolean sInitialized;

    private static final String MOVIEDB_SYNC_TAG = "moviedb-sync";

    synchronized public static void setSettingForGettingData(@NonNull final Context context, ResultReceiver resultReceiver)
    {
        if (sInitialized)
        {
            resultReceiver.send(MovieDBKeyEntry.GetDataIntentServiceKey.GET_MOVIE_LIST_RESULT_SUCCESS, null);
            return;
        }

        sInitialized = true;

        scheduleJob(context);
        useIntentServiceToGetData(context, resultReceiver);
    }

    private static void scheduleJob(@NonNull final Context context)
    {
        /*FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Job myJob = dispatcher.newJobBuilder()
                .setService(GetMovieListRepeatingService.class)
                .setTag(MOVIEDB_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setReplaceCurrent(true)
                .build();

        dispatcher.mustSchedule(myJob);*/
        /*GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(context);

        PeriodicTask task = new PeriodicTask.Builder()
                .setService(GetInitialDataGCMTaskService.class)
                .setTag(MOVIEDB_SYNC_TAG)
                .setPeriod(30L)
                .build();

        mGcmNetworkManager.schedule(task);*/
    }

    private static void useIntentServiceToGetData(@NonNull final Context context, ResultReceiver resultReceiver)
    {
        /*Intent intent = new Intent(context, GetMovieListIntentService.class);
        intent.putExtra(MovieDBKeyEntry.GET_MOVIE_LIST_RESULT_RECEIVER, resultReceiver);

        context.startService(intent);*/


    }


}
