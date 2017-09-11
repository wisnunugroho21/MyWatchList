package com.example.android.moviedb3.services;


/**
 * Created by nugroho on 10/09/17.
 */

public interface IJobSchedulerUtils<Data>
{
    int doJobScheduling();
    int cancelJobScheduling();
}
