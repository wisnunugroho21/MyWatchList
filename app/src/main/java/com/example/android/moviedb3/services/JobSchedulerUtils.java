package com.example.android.moviedb3.services;

import com.firebase.jobdispatcher.JobService;

/**
 * Created by nugroho on 10/09/17.
 */

public class JobSchedulerUtils
{
    public static <Data>void doJobScheduling(IJobSchedulerUtils<Data> jobSchedulerUtils)
    {
        jobSchedulerUtils.doJobScheduling();
    }
}
