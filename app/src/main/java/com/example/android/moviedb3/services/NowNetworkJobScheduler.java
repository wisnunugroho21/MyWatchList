package com.example.android.moviedb3.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by nugroho on 10/09/17.
 */

public class NowNetworkJobScheduler<Data extends JobService> implements IJobSchedulerUtils<Data>
{
    Context context;
    int id;
    Class<Data> jobServiceClass;

    public NowNetworkJobScheduler(Context context, int id, Class<Data> jobServiceClass) {
        this.context = context;
        this.id = id;
        this.jobServiceClass = jobServiceClass;
    }

    @Override
    public void doJobScheduling()
    {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(id, new ComponentName(context.getPackageName(), jobServiceClass.getName()))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setBackoffCriteria(30000, JobInfo.BACKOFF_POLICY_LINEAR)
                .build();

        jobScheduler.schedule(jobInfo);
    }
}
