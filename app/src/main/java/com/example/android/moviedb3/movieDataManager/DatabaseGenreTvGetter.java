package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.localDatabase.TVDataDB;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.GenreTvData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.supportDataManager.dataComparision.IntermedieteDataCompare;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameIDDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class DatabaseGenreTvGetter implements IMovieDBGetter
{
    Context context;
    OnDataObtainedListener<ArrayList<TVData>> onDataObtainedListener;
    DataDB<GenreTvData> genreTvDataDB;
    String idGenre;

    public DatabaseGenreTvGetter(Context context, OnDataObtainedListener<ArrayList<TVData>> onDataObtainedListener, DataDB<GenreTvData> genreTvDataDB, String idGenre) {
        this.context = context;
        this.onDataObtainedListener = onDataObtainedListener;
        this.genreTvDataDB = genreTvDataDB;
        this.idGenre = idGenre;
    }

    @Override
    public void getData()
    {
        DatabaseTVDBGetterAsyncTask dataDB = new DatabaseTVDBGetterAsyncTask();
        dataDB.execute();
    }

    private class DatabaseTVDBGetterAsyncTask extends AsyncTask<Void, Void, ArrayList<TVData>>
    {
        @Override
        protected ArrayList<TVData> doInBackground(Void... params)
        {
            ArrayList<GenreTvData> genreTvDatas = genreTvDataDB.getAllData();
            ArrayList<GenreTvData> expectedGenreMovieDatas = SameDataFinder.getDataSameList
                    (new SameIDDataFinder<GenreTvData>
                            (new IntermedieteDataCompare<GenreTvData>(IntermedieteDataCompare.CHECK_SECOND_ID), genreTvDatas, idGenre));

            DataDB<TVData> tvDB = new TVDataDB(context);
            ArrayList<TVData> tvDatas = tvDB.getAllData();
            ArrayList<TVData> expectedTVDatas = new ArrayList<>();

            for (TVData tvData:tvDatas)
            {
                for (GenreTvData genreTvData:expectedGenreMovieDatas)
                {
                    if(genreTvData.getIdTV().equals(tvData.getId()))
                    {
                        expectedTVDatas.add(tvData);
                    }
                }
            }

            return expectedTVDatas;
        }

        @Override
        protected void onPostExecute(ArrayList<TVData> tvDatas)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(tvDatas);
            }
        }
    }
}
