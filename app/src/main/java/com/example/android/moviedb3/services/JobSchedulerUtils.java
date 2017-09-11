package com.example.android.moviedb3.services;

import com.firebase.jobdispatcher.JobService;

/**
 * Created by nugroho on 10/09/17.
 */

public class JobSchedulerUtils
{
    public static <Data>int doJobScheduling(IJobSchedulerUtils<Data> jobSchedulerUtils)
    {
        return jobSchedulerUtils.doJobScheduling();
    }

    public static <Data>int cancelJobScheduling(IJobSchedulerUtils<Data> jobSchedulerUtils)
    {
        return jobSchedulerUtils.cancelJobScheduling();
    }
}
