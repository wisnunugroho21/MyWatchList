package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.GenreTVListJSONParser;
import com.example.android.moviedb3.jsonParsing.TVDetailJSONParser;
import com.example.android.moviedb3.localDatabase.CastTVDataDB;
import com.example.android.moviedb3.localDatabase.CrewTVDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.TVDataDB;
import com.example.android.moviedb3.localDatabase.VideoTVDataDB;
import com.example.android.moviedb3.movieDB.CastTVData;
import com.example.android.moviedb3.movieDB.CrewTVData;
import com.example.android.moviedb3.movieDB.GenreTvData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.movieDB.VideoTVData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataListGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataReplace.BaseDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class GenreTVGetter implements IMovieDBGetter {

    Context context;
    String genreID;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    DataDB<GenreTvData> genreTVDataDB;
    String genreURL;
    DataDB<TVData> tvDB;

    public GenreTVGetter(String genreID, Context context, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener,
                            DataDB<GenreTvData> genreTVDataDB, String genreURL) {
        this.context = context;
        this.genreID = genreID;
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;

        this.genreTVDataDB = genreTVDataDB;
        this.genreURL = genreURL;

        tvDB = new TVDataDB(context);
    }

    @Override
    public void getData() {
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<GenreTvData>>(new GenreTVListJSONParser(genreID), new OnGenreIDListObtainedListener()), genreURL);
    }

    private class OnGenreIDListObtainedListener implements OnDataObtainedListener<ArrayList<GenreTvData>> {
        @Override
        public void onDataObtained(ArrayList<GenreTvData> genreTvDatas) {
            if (genreTvDatas == null || !NetworkConnectionChecker.IsConnect(context)) {
                if (onAsyncTaskCompleteListener != null) {
                    onAsyncTaskCompleteListener.onComplete(true);
                }
            } else {
                String[] tvURLList = new String[genreTvDatas.size()];

                for (int a = 0; a < genreTvDatas.size(); a++) {
                    tvURLList[a] = MovieDataURL.GetTVURL(genreTvDatas.get(a).getIdTV(), context);
                }

                NetworkDataGetter.GetDataAsyncTask(new NetworkDataListGetterAsyncTask<TVData>(new TVDetailJSONParser(), new AllTVDataObtainedListener(genreTvDatas)), tvURLList);
            }
        }
    }

    private class AllTVDataObtainedListener implements OnDataObtainedListener<ArrayList<TVData>> {
        ArrayList<GenreTvData> genreTvDatas;

        public AllTVDataObtainedListener(ArrayList<GenreTvData> genreTvDatas) {
            this.genreTvDatas = genreTvDatas;
        }

        @Override
        public void onDataObtained(ArrayList<TVData> tvDatas) {

            if (tvDatas != null && NetworkConnectionChecker.IsConnect(context))
            {
                NotExpectedDataEraseAsyncTask notExpectedDataEraseAsyncTask = new NotExpectedDataEraseAsyncTask(genreTvDatas, tvDatas);
                notExpectedDataEraseAsyncTask.execute();
            }

            else
            {
                if (onAsyncTaskCompleteListener != null)
                {
                    onAsyncTaskCompleteListener.onComplete(true);
                }
            }
        }
    }

    private class NotExpectedDataEraseAsyncTask extends AsyncTask<Void, Void, Void> {
        ArrayList<GenreTvData> genreTvDatas;
        ArrayList<TVData> tvDatas;

        public NotExpectedDataEraseAsyncTask(ArrayList<GenreTvData> genreTvDatas, ArrayList<TVData> tvDatas) {
            this.genreTvDatas = genreTvDatas;
            this.tvDatas = tvDatas;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ArrayList<GenreTvData> databaseGenreTVDataArrayList = genreTVDataDB.getAllData();

            if(databaseGenreTVDataArrayList != null)
            {
                for(GenreTvData databaseGenreTVData:databaseGenreTVDataArrayList)
                {
                    boolean isSame = false;

                    for (GenreTvData genreTvData: genreTvDatas)
                    {
                        if(databaseGenreTVData.getIdGenre().equals(genreTvData.getIdGenre()) && databaseGenreTVData.getIdTV().equals(genreTvData.getIdTV()))
                        {
                            isSame = true;
                            break;
                        }
                    }

                    if(!isSame)
                    {
                        String idTV = databaseGenreTVData.getIdTV();
                        boolean isMovieAvaiable = DatabaseTVIsAvailable.isAvailableFromGenreList(idTV, genreID, context);

                        if (!isMovieAvaiable)
                        {
                            tvDB.removeData(idTV);

                            DataDelete.Delete(new BaseDataListDelete<VideoTVData>
                                    (new DepedencyDataCompare<VideoTVData>(), new VideoTVDataDB(context), idTV));
                            DataDelete.Delete(new BaseDataListDelete<CastTVData>
                                    (new DepedencyDataCompare<CastTVData>(), new CastTVDataDB(context), idTV));
                            DataDelete.Delete(new BaseDataListDelete<CrewTVData>
                                    (new DepedencyDataCompare<CrewTVData>(), new CrewTVDataDB(context), idTV));
                        }
                    }
                }

                for(GenreTvData genreTvData: genreTvDatas)
                {
                    for(GenreTvData databaseGenreTV:databaseGenreTVDataArrayList)
                    {
                        if(databaseGenreTV.getIdGenre().equals(genreTvData.getIdGenre()))
                        {
                            genreTVDataDB.removeData(databaseGenreTV.getId());
                        }
                    }
                }
            }

            DataReplace.ReplaceData(new BaseDataListReplace<TVData>(tvDatas, tvDB, new BaseDataCompare<TVData>()));
//            DataReplace.ReplaceData(new BaseDataListReplace<GenreMovieData>(genreTvDatas, genreTVDataDB, new IntermedieteDataCompare<GenreMovieData>(IntermedieteDataCompare.CHECK_SECOND_ID)));

            for(GenreTvData genreTvData: genreTvDatas)
            {
                genreTVDataDB.addData(genreTvData);
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