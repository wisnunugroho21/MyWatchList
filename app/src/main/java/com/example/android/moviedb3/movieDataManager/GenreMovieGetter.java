package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.GenreMovieListJSONParser;
import com.example.android.moviedb3.jsonParsing.MovieDetailJSONParser;
import com.example.android.moviedb3.jsonParsing.MovieIDListJSONParser;
import com.example.android.moviedb3.localDatabase.CastDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.localDatabase.ReviewDataDB;
import com.example.android.moviedb3.localDatabase.VideoDataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
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
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataListGetterSyncTask;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.SameDataListReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.DefaultNoDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 05/09/17.
 */

public class GenreMovieGetter implements IMovieDBGetter {

    Context context;
    String genreID;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    DataDB<GenreMovieData> genreMovieDataDB;
    String genreURL;
    DataDB<MovieData> movieDB;

    public GenreMovieGetter(String genreID, Context context, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener, DataDB<GenreMovieData> genreMovieDataDB, String genreURL) {
        this.context = context;
        this.genreID = genreID;
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;

        this.genreMovieDataDB = genreMovieDataDB;
        this.genreURL = genreURL;

        movieDB = new MovieDataDB(context);
    }

    @Override
    public void getData() {
        NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<GenreMovieData>>(new GenreMovieListJSONParser(genreID), new OnMovieIDListObtainedListener()), genreURL);
    }

    private class OnMovieIDListObtainedListener implements OnDataObtainedListener<ArrayList<GenreMovieData>> {
        @Override
        public void onDataObtained(ArrayList<GenreMovieData> genreMovieDatas) {
            if (genreMovieDatas == null || !NetworkConnectionChecker.IsConnect(context)) {
                if (onAsyncTaskCompleteListener != null) {
                    onAsyncTaskCompleteListener.onComplete(true);
                }
            } else {
                String[] movieURLList = new String[genreMovieDatas.size()];

                for (int a = 0; a < genreMovieDatas.size(); a++) {
                    movieURLList[a] = MovieDataURL.GetMovieURL(genreMovieDatas.get(a).getIdMovie());
                }

                NetworkDataGetter.GetData(new NetworkDataListGetterSyncTask<MovieData>(new MovieDetailJSONParser(), new AllMovieDataObtainedListener(genreMovieDatas)), movieURLList);
            }
        }
    }

    private class AllMovieDataObtainedListener implements OnDataObtainedListener<ArrayList<MovieData>> {
        ArrayList<GenreMovieData> genreMovieDatas;

        public AllMovieDataObtainedListener(ArrayList<GenreMovieData> genreMovieDatas) {
            this.genreMovieDatas = genreMovieDatas;
        }

        @Override
        public void onDataObtained(ArrayList<MovieData> movieDatas) {
            if (movieDatas != null && NetworkConnectionChecker.IsConnect(context)) {
                NotExpectedDataEraseAsyncTask notExpectedDataEraseAsyncTask = new NotExpectedDataEraseAsyncTask(genreMovieDatas, movieDatas);
                notExpectedDataEraseAsyncTask.execute();
            } else {
                if (onAsyncTaskCompleteListener != null) {
                    onAsyncTaskCompleteListener.onComplete(true);
                }
            }
        }
    }

    private class NotExpectedDataEraseAsyncTask extends AsyncTask<Void, Void, Void> {
        ArrayList<GenreMovieData> genreMovieDatas;
        ArrayList<MovieData> movieDatas;

        public NotExpectedDataEraseAsyncTask(ArrayList<GenreMovieData> genreMovieDatas, ArrayList<MovieData> movieDatas) {
            this.genreMovieDatas = genreMovieDatas;
            this.movieDatas = movieDatas;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ArrayList<GenreMovieData> deletedGenreMovieDataList = new ArrayList<>();

            ArrayList<GenreMovieData> databaseGenreMovieDataArrayList = genreMovieDataDB.getAllData();

            if(databaseGenreMovieDataArrayList != null)
            {
                for(GenreMovieData databaseGenreMovieData:databaseGenreMovieDataArrayList)
                {
                    boolean isSame = false;

                    for (GenreMovieData genreMovieData:genreMovieDatas)
                    {
                        if(databaseGenreMovieData.getIdGenre().equals(genreMovieData.getIdGenre()) && databaseGenreMovieData.getIdMovie().equals(genreMovieData.getIdMovie()))
                        {
                            isSame = true;
                            break;
                        }
                    }

                    if(!isSame)
                    {
                        String idMovie = databaseGenreMovieData.getIdMovie();
                        boolean isMovieAvaiable = DatabaseMovieIsAvailable.isAvailableFromGenreList(idMovie, genreID, context);

                        if (!isMovieAvaiable)
                        {
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

                for(GenreMovieData genreMovieData:genreMovieDatas)
                {
                    for(GenreMovieData databaseGenreMovie:databaseGenreMovieDataArrayList)
                    {
                        if(databaseGenreMovie.getIdGenre().equals(genreMovieData.getIdGenre()))
                        {
                            genreMovieDataDB.removeData(databaseGenreMovie.getId());
                        }
                    }
                }
            }

            DataReplace.ReplaceData(new SameDataListReplace<MovieData>(movieDatas, movieDB, new BaseDataCompare<MovieData>()));
//            DataReplace.ReplaceData(new SameDataListReplace<GenreMovieData>(genreMovieDatas, genreMovieDataDB, new IntermedieteDataCompare<GenreMovieData>(IntermedieteDataCompare.CHECK_SECOND_ID)));

            for(GenreMovieData genreMovieData:genreMovieDatas)
            {
                genreMovieDataDB.addData(genreMovieData);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if (onAsyncTaskCompleteListener != null)
            {
                onAsyncTaskCompleteListener.onComplete(true);
            }
        }
    }
}