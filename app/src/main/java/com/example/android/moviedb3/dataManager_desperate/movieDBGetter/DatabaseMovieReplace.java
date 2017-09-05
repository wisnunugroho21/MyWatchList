package com.example.android.moviedb3.dataManager_desperate.movieDBGetter;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager_desperate.dataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_IDListCheck;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_IntermedieteListCheck;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.DataFind;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.DependencyDataListFinder;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.IntermedietDataListFinder;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.NoIDListFinder;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_DataListCheck;
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.localDatabase.CastDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
/*import com.example.android.moviedb3.localDatabase.GenreMoviePopularDB;
import com.example.android.moviedb3.localDatabase.GenreMovieTopRateDB;*/
import com.example.android.moviedb3.localDatabase.ReviewDataDB;
import com.example.android.moviedb3.localDatabase.VideoDataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDB.VideoData;

import java.util.ArrayList;

/**
 * Created by nugroho on 03/09/17.
 */

public class DatabaseMovieReplace
{
    Context context;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    ArrayList<MovieData> movieDatas;
    ArrayList<String> idMovieList;
    DataDB<String> currentMovieListDataDB;
    ArrayList<DataDB<String>> otherMovieListDataDB;

    DataDB<MovieData> movieDB;

    public DatabaseMovieReplace(Context context, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener, ArrayList<MovieData> movieDatas, ArrayList<String> idMovieList, DataDB<String> currentMovieListDataDB, ArrayList<DataDB<String>> otherMovieListDataDB) {
        this.context = context;
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;
        this.movieDatas = movieDatas;
        this.idMovieList = idMovieList;
        this.currentMovieListDataDB = currentMovieListDataDB;
        this.otherMovieListDataDB = otherMovieListDataDB;
    }

    private void ReplaceData()
    {
        NotExpectedDataEraseAsyncTask notExpectedDataEraseAsyncTask = new NotExpectedDataEraseAsyncTask(idMovieList, movieDatas);
        notExpectedDataEraseAsyncTask.execute();
    }

    private class NotExpectedDataEraseAsyncTask extends AsyncTask<Void, Void, Void>
    {
        ArrayList<String> idMovieList;
        ArrayList<MovieData> movieDatas;

        public NotExpectedDataEraseAsyncTask(ArrayList<String> idMovieList, ArrayList<MovieData> movieDatas)
        {
            this.idMovieList = idMovieList;
            this.movieDatas = movieDatas;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ArrayList<String> deletedIdMovieList = DataFind.FindData
                    (new NoIDListFinder(idMovieList, currentMovieListDataDB));

            for (String idMovie : deletedIdMovieList)
            {
                boolean isMovieAvaiable = false;

                for (DataDB<String> dataDB : otherMovieListDataDB)
                {
                    if (DataCheck.CheckData(new SameID_IDListCheck(dataDB, idMovie)))
                    {
                        isMovieAvaiable = true;
                        break;
                    }

                    /*if(DataCheck.CheckData(new SameID_IntermedieteListCheck<GenreMovieData>
                            (IntermedietDataListFinder.CHECK_FIRST_ID, new GenreMoviePopularDB(context), idMovie)))
                    {
                        isMovieAvaiable = true;
                        break;
                    }

                    if(DataCheck.CheckData(new SameID_IntermedieteListCheck<GenreMovieData>
                            (IntermedietDataListFinder.CHECK_FIRST_ID, new GenreMovieTopRateDB(context), idMovie)))
                    {
                        isMovieAvaiable = true;
                        break;
                    }*/
                }

                if (!isMovieAvaiable)
                {
                    movieDB.removeData(idMovie);

                    ArrayList<ReviewData> reviewDatas = DataFind.FindData(new DependencyDataListFinder<ReviewData>(new ReviewDataDB(context), idMovie));
                    DataDB<ReviewData> reviewDataDB = new ReviewDataDB(context);
                    for (ReviewData reviewData : reviewDatas)
                    {
                        reviewDataDB.removeData(reviewData.getId());
                    }

                    ArrayList<VideoData> videoDatas = DataFind.FindData(new DependencyDataListFinder<VideoData>(new VideoDataDB(context), idMovie));
                    DataDB<VideoData> videoDataDB = new VideoDataDB(context);
                    for (VideoData videoData : videoDatas)
                    {
                        videoDataDB.removeData(videoData.getId());
                    }

                    ArrayList<CastData> castDatas = DataFind.FindData(new DependencyDataListFinder<CastData>(new CastDataDB(context), idMovie));
                    DataDB<CastData> castDataDB = new CastDataDB(context);
                    for (CastData castData : castDatas)
                    {
                        castDataDB.removeData(castData.getId());
                    }

                    ArrayList<CrewData> crewDatas = DataFind.FindData(new DependencyDataListFinder<CrewData>(new CrewDataDB(context), idMovie));
                    DataDB<CrewData> crewDataDB = new CrewDataDB(context);
                    for (CrewData crewData : crewDatas)
                    {
                        crewDataDB.removeData(crewData.getId());
                    }
                }
            }

            for (MovieData movieData:movieDatas)
            {
                if(DataCheck.CheckData(new SameID_DataListCheck<MovieData>(movieDB, movieData.getId())))
                {
                    movieDB.removeData(movieData.getId());
                }

                movieDB.addData(movieData);
            }

            currentMovieListDataDB.removeAllData();

            for (String idMovie : idMovieList)
            {
                currentMovieListDataDB.addData(idMovie);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(onAsyncTaskCompleteListener != null)
            {
                onAsyncTaskCompleteListener.onComplete(true);
            }
        }
    }

}
