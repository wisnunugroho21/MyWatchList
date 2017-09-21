package com.example.android.moviedb3.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_IDListCheck;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;

import java.util.ArrayList;

/**
 * Created by nugroho on 01/09/17.
 */

public class WatchListDialogFragment extends DialogFragment
{
    String movieID;
    DataDB<String> currentMovieListDataDB;
    ArrayList<DataDB<String>> otherMovieListDataDB;

    int watchListState;
    OnDataObtainedListener<Integer> onDataObtainedListener;

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
                        currentMovieListDataDB = null;
                        otherMovieListDataDB = getAllWatchListMovieListDataDB();
                        watchListState = MovieDBKeyEntry.DatabaseHasChanged.REMOVE_MY_LIST;
                        break;

                    case 1 :
                        currentMovieListDataDB = new PlanToWatchDataDB(getActivity());
                        otherMovieListDataDB = getPlanToWatchOtherMovieListDataDB();
                        watchListState = MovieDBKeyEntry.DatabaseHasChanged.INSERT_PLAN_TO_WATCH_LIST;
                        break;

                    case 2 :
                        currentMovieListDataDB = new WatchlistDataDB(getActivity());
                        otherMovieListDataDB = getWatchedOtherMovieListDataDB();
                        watchListState = MovieDBKeyEntry.DatabaseHasChanged.INSERT_TO_WATCHED_LIST;
                        break;
                }
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                for (DataDB<String> dataDB : otherMovieListDataDB)
                {
                    if(DataCheck.CheckData(new SameID_IDListCheck(dataDB, movieID)))
                    {
                        dataDB.removeData(movieID);
                    }
                }

                if(currentMovieListDataDB != null)
                {
                    currentMovieListDataDB.addData(movieID);
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

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setOnDataObtainedListener(OnDataObtainedListener<Integer> onDataObtainedListener) {
        this.onDataObtainedListener = onDataObtainedListener;
    }

    private ArrayList<DataDB<String>> getWatchedOtherMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();
        dataDBArrayList.add(new PlanToWatchDataDB(getActivity()));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getPlanToWatchOtherMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();
        dataDBArrayList.add(new WatchlistDataDB(getActivity()));

        return dataDBArrayList;
    }

    private ArrayList<DataDB<String>> getAllWatchListMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();
        dataDBArrayList.add(new PlanToWatchDataDB(getActivity()));
        dataDBArrayList.add(new WatchlistDataDB(getActivity()));

        return dataDBArrayList;
    }

    public int getInitialIndexForDialog()
    {
        boolean watchedListState = DataCheck.CheckData(new SameID_IDListCheck(new WatchlistDataDB(getActivity()), movieID));
        boolean planToWatchListState = DataCheck.CheckData(new SameID_IDListCheck(new PlanToWatchDataDB(getActivity()), movieID));

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

/*private class DataHasBeenDeleted implements OnAsyncTaskCompleteListener
    {
        @Override
        public void onComplete(boolean isSuccess)
        {
            if(currentTVListDataDB != null)
            {
                currentTVListDataDB.addData(tvID);
            }

            if(onAsyncTaskCompleteListener != null)
            {
                onAsyncTaskCompleteListener.onComplete(isSuccess);
            }
        }
    }
*/
