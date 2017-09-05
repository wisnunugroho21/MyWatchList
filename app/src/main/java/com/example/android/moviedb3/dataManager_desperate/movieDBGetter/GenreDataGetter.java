package com.example.android.moviedb3.dataManager_desperate.movieDBGetter;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager_desperate.dataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.DependencyDataListFinder;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.IntermedietDataListFinder;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.NoDataListFinder;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_IDListCheck;
import com.example.android.moviedb3.dataManager_desperate.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.dataManager_desperate.dataGetter.NetworkDataGetterSyncTask;
import com.example.android.moviedb3.dataManager_desperate.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.dataManager_desperate.dataReplace.ReplaceData;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.GenreListJSONParser;
import com.example.android.moviedb3.localDatabase.CastDataDB;
import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.GenreDataDB;
/*import com.example.android.moviedb3.localDatabase.GenreMoviePopularDB;
import com.example.android.moviedb3.localDatabase.GenreMovieTopRateDB;*/
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.localDatabase.NowShowingDataDB;
import com.example.android.moviedb3.localDatabase.PlanToWatchDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.ReviewDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.localDatabase.VideoDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDB.VideoData;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreDataGetter
{
    Context context;
    String genreURL;
    OnDataObtainedListener<ArrayList<GenreData>> onDataObtainedListener;

    public void Execute()
    {
        NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<GenreData>>(new GenreListJSONParser(), new GenreListObtained()), genreURL);
    }

    public class GenreListObtained implements OnDataObtainedListener<ArrayList<GenreData>>
    {
        @Override
        public void onDataObtained(ArrayList<GenreData> genreDataArrayList)
        {
            if(genreDataArrayList != null && NetworkConnectionChecker.IsConnect(context))
            {
                DatabaseGenreReplaceAsyncTask dataReplace = new DatabaseGenreReplaceAsyncTask(genreDataArrayList);
                dataReplace.execute();
            }

            else
            {
                DatabaseGenreGettereAsyncTask databaseGetter = new DatabaseGenreGettereAsyncTask();
                databaseGetter.execute();
            }
        }
    }

    public class DatabaseGenreReplaceAsyncTask extends AsyncTask<Void, Void, Void>
    {
        ArrayList<GenreData> networkGenreDataArrayList;

        public DatabaseGenreReplaceAsyncTask(ArrayList<GenreData> networkGenreDataArrayList)
        {
            this.networkGenreDataArrayList = networkGenreDataArrayList;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            DataDB<GenreData> genreDataDataDB = new GenreDataDB(context);
//            ArrayList<String> differentGenreIDList = DataCheck.CheckData(new NoDataListFinder<GenreData>(genreDataDataDB, networkGenreDataArrayList));
            ArrayList<GenreMovieData> unexpectedGenreMovieList = new ArrayList<>();

            /*for (String differentGenreID:differentGenreIDList)
            {
                *//*ArrayList<GenreMovieData> unexpectedGenreMoviePopularList = DataCheck.CheckData(
                        new IntermedietDataListFinder<GenreMovieData>
                                (differentGenreID, IntermedietDataListFinder.CHECK_SECOND_ID, new GenreMoviePopularDB(context)));

                ArrayList<GenreMovieData> unexpectedGenreMovieTopRateList = DataCheck.CheckData(
                        new IntermedietDataListFinder<GenreMovieData>
                                (differentGenreID, IntermedietDataListFinder.CHECK_SECOND_ID, new GenreMovieTopRateDB(context)));*//*

                *//*if(unexpectedGenreMoviePopularList == null || unexpectedGenreMoviePopularList.isEmpty()
                        || unexpectedGenreMovieTopRateList == null || unexpectedGenreMovieTopRateList.isEmpty())
                {
                    break;
                }

                unexpectedGenreMovieList.addAll(unexpectedGenreMoviePopularList);
                unexpectedGenreMovieList.addAll(unexpectedGenreMovieTopRateList);*//*

                DeleteMovieInList(unexpectedGenreMovieList);
                ReplaceALlGenre(networkGenreDataArrayList);
            }
*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            DatabaseGenreGettereAsyncTask databaseGetter = new DatabaseGenreGettereAsyncTask();
            databaseGetter.execute();
        }

        private void DeleteMovieInList(ArrayList<GenreMovieData> unexpectedGenreMovieList)
        {
            for (GenreMovieData genreMovieData : unexpectedGenreMovieList)
            {
               /* String idMovie = genreMovieData.getIdMovie();
                boolean isMovieAvaiable = false;

                for (DataDB<String> dataDB : getInitialOtherMovieListDataDB())
                {
                    if (DataCheck.CheckData(new SameID_IDListCheck(dataDB, idMovie)))
                    {
                        isMovieAvaiable = true;
                        break;
                    }
                }

                if (!isMovieAvaiable)
                {
                    DataDB<MovieData> movieDB = new MovieDataDB(context);
                    movieDB.removeData(idMovie);

                    ArrayList<ReviewData> reviewDatas = DataCheck.CheckData(new DependencyDataListFinder<ReviewData>(new ReviewDataDB(context), idMovie));
                    DataDB<ReviewData> reviewDataDB = new ReviewDataDB(context);
                    for (ReviewData reviewData : reviewDatas)
                    {
                        reviewDataDB.removeData(reviewData.getId());
                    }

                    ArrayList<VideoData> videoDatas = DataCheck.CheckData(new DependencyDataListFinder<VideoData>(new VideoDataDB(context), idMovie));
                    DataDB<VideoData> videoDataDB = new VideoDataDB(context);
                    for (VideoData videoData : videoDatas)
                    {
                        videoDataDB.removeData(videoData.getId());
                    }

                    ArrayList<CastData> castDatas = DataCheck.CheckData(new DependencyDataListFinder<CastData>(new CastDataDB(context), idMovie));
                    DataDB<CastData> castDataDB = new CastDataDB(context);
                    for (CastData castData : castDatas)
                    {
                        castDataDB.removeData(castData.getId());
                    }

                    ArrayList<CrewData> crewDatas = DataCheck.CheckData(new DependencyDataListFinder<CrewData>(new CrewDataDB(context), idMovie));
                    DataDB<CrewData> crewDataDB = new CrewDataDB(context);
                    for (CrewData crewData : crewDatas)
                    {
                        crewDataDB.removeData(crewData.getId());
                    }
                }*/
            }
        }

        private void ReplaceALlGenre(ArrayList<GenreData> genreDataArrayList)
        {
            ReplaceData.Replace(new AllDataListReplace<GenreData>(genreDataArrayList, new GenreDataDB(context)));
        }

        private ArrayList<DataDB<String>> getInitialOtherMovieListDataDB()
        {
            ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

            dataDBArrayList.add(new NowShowingDataDB(context));
            dataDBArrayList.add(new ComingSoonDataDB(context));
            dataDBArrayList.add(new PopularDataDB(context));
            dataDBArrayList.add(new TopRateDataDB(context));
            dataDBArrayList.add(new FavoriteDataDB(context));
            dataDBArrayList.add(new WatchlistDataDB(context));
            dataDBArrayList.add(new PlanToWatchDataDB(context));

            return dataDBArrayList;
        }
    }

    public class DatabaseGenreGettereAsyncTask extends AsyncTask<Void, Void, ArrayList<GenreData>>
    {
        @Override
        protected ArrayList<GenreData> doInBackground(Void... params)
        {
            DataDB<GenreData> dataDB = new GenreDataDB(context);
            return dataDB.getAllData();
        }

        @Override
        protected void onPostExecute(ArrayList<GenreData> genreDataArrayList)
        {
            onDataObtainedListener.onDataObtained(genreDataArrayList);
        }
    }
}
