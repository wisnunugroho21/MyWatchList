package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager_desperate.dataReplace.ReplaceData;
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.GenreListJSONParser;
import com.example.android.moviedb3.localDatabase.CastDataDB;
import com.example.android.moviedb3.localDatabase.ComingSoonDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.GenreDataDB;
import com.example.android.moviedb3.localDatabase.GenreMoviePopularDB;
import com.example.android.moviedb3.localDatabase.GenreMovieTopRateDB;
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
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IntermedieteDataCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataListDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterSyncTask;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.DefaultNoDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.DefaultSameDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreDataGetter implements IMovieDBGetter
{
    Context context;
    String genreURL;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    public GenreDataGetter(Context context, String genreURL, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener)
    {
        this.context = context;
        this.genreURL = genreURL;
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;
    }

    @Override
    public void getData()
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
                if(onAsyncTaskCompleteListener != null)
                {
                    onAsyncTaskCompleteListener.onComplete(true);
                }
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

            ArrayList<String> idNetworkGenreArrayList = new ArrayList<>();
            for (GenreData genreData:networkGenreDataArrayList)
            {
                idNetworkGenreArrayList.add(genreData.getId());
            }

            ArrayList<GenreData> deletedGenreList = NoDataFinder.FindNotSameData
                    (new DefaultNoDataFinder<GenreData>(new BaseDataCompare<GenreData>(), genreDataDataDB.getAllData(), idNetworkGenreArrayList));

            if(deletedGenreList != null && !deletedGenreList.isEmpty())
            {
                ArrayList<GenreMovieData> deletedGenreMovieDataList = new ArrayList<>();

                ArrayList<String> idDeletedGenreList = new ArrayList<>();
                for (GenreData deletedGenre:deletedGenreList)
                {
                    idDeletedGenreList.add(deletedGenre.getId());
                }

                DataDB<GenreMovieData> genrePopularMovieDataDB = new GenreMoviePopularDB(context);
                ArrayList<GenreMovieData> deletedGenrePopularMovieDataList = SameDataFinder.getDataSameList
                        (new DefaultSameDataFinder<GenreMovieData>(new IntermedieteDataCompare<GenreMovieData>
                                (IntermedieteDataCompare.CHECK_SECOND_ID), genrePopularMovieDataDB.getAllData(), idDeletedGenreList));

                DataDB<GenreMovieData> genreTopRateMovieDataDB = new GenreMovieTopRateDB(context);
                ArrayList<GenreMovieData> deletedGenreTopRateMovieDataList = SameDataFinder.getDataSameList
                        (new DefaultSameDataFinder<GenreMovieData>(new IntermedieteDataCompare<GenreMovieData>
                                (IntermedieteDataCompare.CHECK_SECOND_ID), genreTopRateMovieDataDB.getAllData(), idDeletedGenreList));

                deletedGenreMovieDataList.addAll(deletedGenrePopularMovieDataList);

                for(GenreMovieData genreMovieData:deletedGenreMovieDataList)
                {
                    for(GenreMovieData toprateGenreMovie:deletedGenreTopRateMovieDataList)
                    {
                        if(!genreMovieData.getIdMovie().equals(toprateGenreMovie.getIdMovie()))
                        {
                            deletedGenreMovieDataList.add(toprateGenreMovie);
                        }
                    }
                }

                for(GenreMovieData genreMovieData:deletedGenreMovieDataList)
                {
                    String idMovie = genreMovieData.getIdMovie();

                    boolean isMovieAvaiable = DatabaseMovieIsAvailable.isAvailableFromGenreList(idMovie, genreMovieData.getIdGenre(), context);

                    if (!isMovieAvaiable)
                    {
                        DataDB<MovieData> movieDB = new MovieDataDB(context);
                        movieDB.removeData(idMovie);

                        DataDelete.Delete(new DataListDelete<ReviewData>
                                (new DepedencyDataCompare<ReviewData>(), new ReviewDataDB(context), idMovie));
                        DataDelete.Delete(new DataListDelete<VideoData>
                                (new DepedencyDataCompare<VideoData>(), new VideoDataDB(context), idMovie));
                        DataDelete.Delete(new DataListDelete<CastData>
                                (new DepedencyDataCompare<CastData>(), new CastDataDB(context), idMovie));
                        DataDelete.Delete(new DataListDelete<CrewData>
                                (new DepedencyDataCompare<CrewData>(), new CrewDataDB(context), idMovie));
                    }
                }
            }

            DataReplace.ReplaceData(new AllDataListReplace<GenreData>(new GenreDataDB(context), networkGenreDataArrayList));

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

/*    @Override
    protected Void doInBackground(Void... params)
    {
        DataDB<GenreData> genreDataDataDB = new GenreDataDB(context);
//            ArrayList<String> differentGenreIDList = DataCheck.CheckData(new NoDataListFinder<GenreData>(genreDataDataDB, networkGenreDataArrayList));
        ArrayList<GenreMovieData> unexpectedGenreMovieList = new ArrayList<>();

            *//*for (String differentGenreID:differentGenreIDList)
            {
                *//**//*ArrayList<GenreMovieData> unexpectedGenreMoviePopularList = DataCheck.CheckData(
                        new IntermedietDataListFinder<GenreMovieData>
                                (differentGenreID, IntermedietDataListFinder.CHECK_SECOND_ID, new GenreMoviePopularDB(context)));

                ArrayList<GenreMovieData> unexpectedGenreMovieTopRateList = DataCheck.CheckData(
                        new IntermedietDataListFinder<GenreMovieData>
                                (differentGenreID, IntermedietDataListFinder.CHECK_SECOND_ID, new GenreMovieTopRateDB(context)));*//**//*

                *//**//*if(unexpectedGenreMoviePopularList == null || unexpectedGenreMoviePopularList.isEmpty()
                        || unexpectedGenreMovieTopRateList == null || unexpectedGenreMovieTopRateList.isEmpty())
                {
                    break;
                }

                unexpectedGenreMovieList.addAll(unexpectedGenreMoviePopularList);
                unexpectedGenreMovieList.addAll(unexpectedGenreMovieTopRateList);*//**//*

                DeleteMovieInList(unexpectedGenreMovieList);
                ReplaceALlGenre(networkGenreDataArrayList);
            }
*//*
        return null;
    }*/
