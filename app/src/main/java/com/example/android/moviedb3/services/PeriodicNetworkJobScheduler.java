package com.example.android.moviedb3.services;

import android.content.Context;

import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by nugroho on 10/09/17.
 */

public class PeriodicNetworkJobScheduler<Data extends JobService> implements IJobSchedulerUtils<Data>
{
    Context context;
    int intervalSecond;
    boolean isWifiOnly;
    String tag;
    Class<Data> jobServiceClass;

    public PeriodicNetworkJobScheduler(Context context, int intervalSecond, boolean isWifiOnly, String tag, Class<Data> jobServiceClass) {
        this.context = context;
        this.intervalSecond = intervalSecond;
        this.isWifiOnly = isWifiOnly;
        this.tag = tag;
        this.jobServiceClass = jobServiceClass;
    }

    public PeriodicNetworkJobScheduler(Context context, String tag) {
        this.tag = tag;
        this.context = context;
    }

    @Override
    public int doJobScheduling()
    {
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Job.Builder builder = firebaseJobDispatcher.newJobBuilder()
                .setService(jobServiceClass)
                .setTag(tag)
                .setRecurring(true)
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, intervalSecond));

        if(isWifiOnly)
        {
            builder.setConstraints(Constraint.ON_UNMETERED_NETWORK);
        }

        else
        {
            builder.setConstraints(Constraint.ON_ANY_NETWORK);
        }

        return firebaseJobDispatcher.schedule(builder.build());
    }

    @Override
    public int cancelJobScheduling() {
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        return firebaseJobDispatcher.cancel(MovieDBKeyEntry.JobSchedulerID.PERIODIC_NETWORK_JOB_KEY);
    }
}
