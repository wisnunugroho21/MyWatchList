package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.GenreListJSONParser;
import com.example.android.moviedb3.localDatabase.CastDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.GenreDataDB;
import com.example.android.moviedb3.localDatabase.GenreMoviePopularDB;
import com.example.android.moviedb3.localDatabase.GenreMovieTopRateDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.localDatabase.ReviewDataDB;
import com.example.android.moviedb3.localDatabase.VideoDataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IntermedieteDataCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoIDDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameIDDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreDataGetterAsyncTask implements IMovieDBGetter
{
    Context context;
    String genreURL;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    public GenreDataGetterAsyncTask(Context context, String genreURL, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener)
    {
        this.context = context;
        this.genreURL = genreURL;
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;
    }

    @Override
    public void getData()
    {
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<GenreData>>(new GenreListJSONParser(), new GenreListObtained()), genreURL);
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
                    (new NoIDDataFinder<GenreData>(new BaseDataCompare<GenreData>(), genreDataDataDB.getAllData(), idNetworkGenreArrayList));

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
                        (new SameIDDataFinder<GenreMovieData>(new IntermedieteDataCompare<GenreMovieData>
                                (IntermedieteDataCompare.CHECK_SECOND_ID), genrePopularMovieDataDB.getAllData(), idDeletedGenreList));

                DataDB<GenreMovieData> genreTopRateMovieDataDB = new GenreMovieTopRateDB(context);
                ArrayList<GenreMovieData> deletedGenreTopRateMovieDataList = SameDataFinder.getDataSameList
                        (new SameIDDataFinder<GenreMovieData>(new IntermedieteDataCompare<GenreMovieData>
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

                        DataDelete.Delete(new BaseDataListDelete<ReviewData>
                                (new DepedencyDataCompare<ReviewData>(), new ReviewDataDB(context), idMovie));
                        DataDelete.Delete(new BaseDataListDelete<VideoData>
                                (new DepedencyDataCompare<VideoData>(), new VideoDataDB(context), idMovie));
                        DataDelete.Delete(new BaseDataListDelete<CastData>
                                (new DepedencyDataCompare<CastData>(), new CastDataDB(context), idMovie));
                        DataDelete.Delete(new BaseDataListDelete<CrewData>
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
