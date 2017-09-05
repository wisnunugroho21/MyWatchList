package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IntermedieteDataCompare;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.DefaultSameDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class DatabaseGenreMovieGetter implements IMovieDBGetter
{
    Context context;
    OnDataObtainedListener<ArrayList<MovieData>> onDataObtainedListener;
    DataDB<GenreMovieData> genreMovieDataDB;
    String idGenre;

    public DatabaseGenreMovieGetter(Context context, OnDataObtainedListener<ArrayList<MovieData>> onDataObtainedListener, DataDB<GenreMovieData> genreMovieDataDB, String idGenre) {
        this.context = context;
        this.onDataObtainedListener = onDataObtainedListener;
        this.genreMovieDataDB = genreMovieDataDB;
        this.idGenre = idGenre;
    }

    @Override
    public void getData()
    {
        DatabaseMovieDBGetterAsyncTask dataDB = new DatabaseMovieDBGetterAsyncTask();
        dataDB.execute();
    }

    private class DatabaseMovieDBGetterAsyncTask extends AsyncTask<Void, Void, ArrayList<MovieData>>
    {
        @Override
        protected ArrayList<MovieData> doInBackground(Void... params)
        {
            ArrayList<GenreMovieData> genreMovieDatas = genreMovieDataDB.getAllData();
            ArrayList<GenreMovieData> expectedGenreMovieDatas = SameDataFinder.getDataSameList
                    (new DefaultSameDataFinder<GenreMovieData>
                            (new IntermedieteDataCompare<GenreMovieData>(IntermedieteDataCompare.CHECK_SECOND_ID), genreMovieDatas, idGenre));

            DataDB<MovieData> movieDB = new MovieDataDB(context);
            ArrayList<MovieData> movieDatas = movieDB.getAllData();
            ArrayList<MovieData> expectedMovieDatas = new ArrayList<>();

            for (MovieData movieData:movieDatas)
            {
                for (GenreMovieData genreMovieData:expectedGenreMovieDatas)
                {
                    if(genreMovieData.getIdMovie().equals(movieData.getId()))
                    {
                        expectedMovieDatas.add(movieData);
                    }
                }
            }

            return expectedMovieDatas;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieDatas)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(movieDatas);
            }
        }
    }
}
