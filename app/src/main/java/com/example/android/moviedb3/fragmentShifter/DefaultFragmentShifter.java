package com.example.android.moviedb3.fragmentShifter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.moviedb3.R;

/**
 * Created by nugroho on 23/08/17.
 */

public class DefaultFragmentShifter implements IFragmentShifter
{
    AppCompatActivity appCompatActivity;
    int containerViewID;
    Fragment fragment;
    Bundle bundle;

    public DefaultFragmentShifter(AppCompatActivity appCompatActivity, int containerViewID, Fragment fragment) {
        this.appCompatActivity = appCompatActivity;
        this.containerViewID = containerViewID;
        this.fragment = fragment;
    }

    public DefaultFragmentShifter(AppCompatActivity appCompatActivity, int containerViewID, Fragment fragment, Bundle bundle) {
        this.appCompatActivity = appCompatActivity;
        this.containerViewID = containerViewID;
        this.fragment = fragment;
        this.bundle = bundle;
    }

    @Override
    public void InitializeFragment()
    {
        if(bundle != null)
        {
            fragment.setArguments(bundle);
        }

        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerViewID, fragment).commit();
    }

}
