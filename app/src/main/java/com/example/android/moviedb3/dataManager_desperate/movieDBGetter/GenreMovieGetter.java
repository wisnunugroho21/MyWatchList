package com.example.android.moviedb3.dataManager_desperate.movieDBGetter;

import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager_desperate.dataReplace.IntermedieteDataListReplace;
import com.example.android.moviedb3.dataManager_desperate.dataReplace.ReplaceData;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.GenreMovieData;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreMovieGetter
{

    private class DataReplaceAsyncTask extends AsyncTask<Void, Void, Void>
    {
        ArrayList<GenreMovieData> datas;
        DataDB<GenreMovieData> dataDB;

        public DataReplaceAsyncTask(ArrayList<GenreMovieData> datas, DataDB<GenreMovieData> dataDB) {
            this.datas = datas;
            this.dataDB = dataDB;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ReplaceData.Replace(new IntermedieteDataListReplace<GenreMovieData>(datas, dataDB, IntermedieteDataListReplace.CHECK_SECOND_ID));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            GenreMovieDatabaseGetter movieInfoDatabaseGetter = new GenreMovieDatabaseGetter();
            movieInfoDatabaseGetter.execute();
        }
    }

    private class GenreMovieDatabaseGetter extends AsyncTask<Void, Void, ArrayList<GenreMovieData>>
    {
        DataDB<GenreMovieData> dataDB;
        String idGenre;

        @Override
        protected ArrayList<GenreMovieData> doInBackground(Void... params)
        {
            ArrayList<GenreMovieData> dataArrayList = dataDB.getAllData();
            ArrayList<GenreMovieData> expectedDataArrayList = new ArrayList<>();

            if(dataArrayList != null)
            {
                for (GenreMovieData data:dataArrayList)
                {
                    if(idGenre.equals(data.getSecondID()))
                    {
                        expectedDataArrayList.add(data);
                    }
                }
            }

            return expectedDataArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<GenreMovieData> datas)
        {
//            onDataObtainedListener.onDataObtained(datas);
        }
    }

}
