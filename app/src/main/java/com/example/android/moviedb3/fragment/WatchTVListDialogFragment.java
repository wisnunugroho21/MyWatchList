package com.example.android.moviedb3.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_IDListCheck;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchTVDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistTvDataDB;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class WatchTVListDialogFragment extends DialogFragment
{
    String tvID;
    DataDB<String> currentTVListDataDB;
    ArrayList<DataDB<String>> otherTVListDataDB;

    int watchListState;
    OnDataObtainedListener<Integer> onDataObtainedListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        int initialIndex = getInitialIndexForDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.my_list_label)
                .setSingleChoiceItems(R.array.watchlist_option_list, initialIndex, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which)
                        {
                            case 0 :
                                currentTVListDataDB = null;
                                otherTVListDataDB = getAllWatchListTVListDataDB();
                                watchListState = MovieDBKeyEntry.DatabaseHasChanged.REMOVE_MY_LIST;
                                break;

                            case 1 :
                                currentTVListDataDB = new PlanToWatchTVDataDB(getActivity());
                                otherTVListDataDB = getPlanToWatchOtherTVListDataDB();
                                watchListState = MovieDBKeyEntry.DatabaseHasChanged.INSERT_PLAN_TO_WATCH_LIST;
                                break;

                            case 2 :
                                currentTVListDataDB = new WatchlistTvDataDB(getActivity());
                                otherTVListDataDB = getWatchedOtherTVListDataDB();
                                watchListState = MovieDBKeyEntry.DatabaseHasChanged.INSERT_TO_WATCHED_LIST;
                                break;
                        }
                    }
                }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                for (DataDB<String> dataDB : otherTVListDataDB)
                {
                    if(DataCheck.CheckData(new SameID_IDListCheck(dataDB, tvID)))
                    {
                        dataDB.removeData(tvID);
                    }
                }

                if(currentTVListDataDB != null)
                {
                    currentTVListDataDB.addData(tvID);
                }

                onDataObtainedListener.onDataObtained(watchListState);

            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder.create();
    }

    public void setTvID(String tvID) {
        this.tvID = tvID;
    }

    public void setOnDataObtainedListener(OnDataObtainedListener<Integer> onDataObtainedListener) {
        this.onDataObtainedListener = onDataObtainedListener;
    }

    private ArrayList<DataDB<String>> getWatchedOtherTVListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();
        dataDBArrayList.add(new PlanToWatchTVDataDB(getActivity()));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getPlanToWatchOtherTVListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();
        dataDBArrayList.add(new WatchlistTvDataDB(getActivity()));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getAllWatchListTVListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();
        dataDBArrayList.add(new PlanToWatchTVDataDB(getActivity()));
        dataDBArrayList.add(new WatchlistTvDataDB(getActivity()));

        return dataDBArrayList;
    }

    public int getInitialIndexForDialog()
    {
        boolean watchedListState = DataCheck.CheckData(new SameID_IDListCheck(new WatchlistTvDataDB(getActivity()), tvID));
        boolean planToWatchListState = DataCheck.CheckData(new SameID_IDListCheck(new PlanToWatchTVDataDB(getActivity()), tvID));

        if(watchedListState && !planToWatchListState)
        {
            return 2;
        }

        else if(!watchedListState && planToWatchListState)
        {
            return 1;
        }

        else
        {
            return 0;
        }
    }
}
