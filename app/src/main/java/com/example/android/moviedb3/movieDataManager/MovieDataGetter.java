package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
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
import com.example.android.moviedb3.movieDB.DependencyData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DefaultDataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataListDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.IdListDelete;
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
 * Created by nugroho on 27/08/17.
 */

public class MovieDataGetter implements IMovieDBGetter {

    Context context;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    DataDB<String> currentMovieListDataDB;
    ArrayList<DataDB<String>> otherMovieListDataDB;
    String movieIDListURL;

    boolean allDataHasBeenObtained;
    DataDB<MovieData> movieDB;

    public MovieDataGetter(Context context, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener, DataDB<String> currentMovieListDataDB, ArrayList<DataDB<String>> otherMovieListDataDB, String movieIDListURL) {
        this.context = context;
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;

        this.currentMovieListDataDB = currentMovieListDataDB;
        this.otherMovieListDataDB = otherMovieListDataDB;
        this.movieIDListURL = movieIDListURL;

        allDataHasBeenObtained = false;
        movieDB = new MovieDataDB(context);
    }

    public void getData()
    {
        NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<String>>(new MovieIDListJSONParser(), new OnMovieIDListObtainedListener()), movieIDListURL);
    }

    private class OnMovieIDListObtainedListener implements OnDataObtainedListener<ArrayList<String>> {
        @Override
        public void onDataObtained(ArrayList<String> strings)
        {
            if(strings == null || !NetworkConnectionChecker.IsConnect(context))
            {
                if(onAsyncTaskCompleteListener != null)
                {
                    onAsyncTaskCompleteListener.onComplete(true);
                }
            }

            else
            {
                String[] movieURLList = new String[strings.size()];

                for(int a = 0; a < strings.size(); a++)
                {
                    movieURLList[a] = MovieDataURL.GetMovieURL(strings.get(a));
                }

                NetworkDataGetter.GetData(new NetworkDataListGetterSyncTask<MovieData>(new MovieDetailJSONParser(), new AllMovieDataObtainedListener(strings)), movieURLList);
            }
        }
    }

    private class AllMovieDataObtainedListener implements OnDataObtainedListener<ArrayList<MovieData>>
    {
        ArrayList<String> idMovieList;

        public AllMovieDataObtainedListener(ArrayList<String> idMovieList)
        {
            this.idMovieList = idMovieList;
        }

        @Override
        public void onDataObtained(ArrayList<MovieData> movieDatas)
        {
            if(movieDatas != null && NetworkConnectionChecker.IsConnect(context))
            {
                NotExpectedDataEraseAsyncTask notExpectedDataEraseAsyncTask = new NotExpectedDataEraseAsyncTask(idMovieList, movieDatas);
                notExpectedDataEraseAsyncTask.execute();
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
            ArrayList<String> willDeletedIdMovieList = NoDataFinder.FindNotSameID
                    (new DefaultNoDataFinder<>(new IDCompare(), idMovieList, currentMovieListDataDB.getAllData()));

            for (String idMovie : willDeletedIdMovieList)
            {
                boolean isMovieAvaiable = DatabaseMovieIsAvailable.isAvailableFromIDList(idMovie, otherMovieListDataDB, context);

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

            DataReplace.ReplaceData(new SameDataListReplace<MovieData>(movieDatas, movieDB, new BaseDataCompare<MovieData>()));
            DataReplace.ReplaceData(new AllDataListReplace<String>(currentMovieListDataDB, idMovieList));

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










    /*DataDB<Data> parentDataDB;
    OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener;

    JSONParser<ArrayList<Data>> dataListjsonParser;
    String[] urlList;

    JSONParser<Data> dataJSONParser;
    String url;

    public AllDataListReplace(@NonNull DataDB<Data> parentDataDB, OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener, @NonNull JSONParser<ArrayList<Data>> dataListjsonParser, @NonNull String url) {
        this.parentDataDB = parentDataDB;
        this.onDataObtainedListener = onDataObtainedListener;
        this.dataListjsonParser = dataListjsonParser;
        this.url = url;
    }

    public AllDataListReplace(@NonNull DataDB<Data> parentDataDB, OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener, @NonNull JSONParser<Data> dataJSONParser, @NonNull String... urlList) {
        this.parentDataDB = parentDataDB;
        this.onDataObtainedListener = onDataObtainedListener;
        this.dataJSONParser = dataJSONParser;
        this.urlList = urlList;
    }*/

    /*public void GetData()
    {
        NetworkDataGetter<ArrayList<Data>> networkDataGetter = new NetworkDataGetter<>();

        if(dataJSONParser == null)
        {
            networkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<Data>>(dataListjsonParser, new OnDataListObtained()), url);
        }

        else
        {
            networkDataGetter.GetData(new NetworkDataListGetterSyncTask<Data>(dataJSONParser, new OnDataListObtained()), urlList);
        }
    }*/




    /*public <Data> void GetData(@NonNull DataDB<Data> database, OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener, @NonNull JSONParser<Data> dataJSONParser, @NonNull String... urlList)
    {
        NetworkDataGetter.GetData(new NetworkDataListGetterSyncTask<Data>(dataJSONParser, new AllDataListReplace.OnDataListObtained(database, onDataObtainedListener)), urlList);
    }

    public <Data> void GetData(@NonNull DataDB<Data> database, OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener, @NonNull JSONParser<ArrayList<Data>> dataListjsonParser, @NonNull String url)
    {
        NetworkDataGetter.GetData(new NetworkDataGetterSyncTask<ArrayList<Data>>(dataListjsonParser, new AllDataListReplace.OnDataListObtained(database, onDataObtainedListener)), url);
    }

    private class OnDataListObtained<Data> implements OnDataObtainedListener<ArrayList<Data>>
    {
        DataDB<Data> database;
        OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener;

        public OnDataListObtained(DataDB<Data> database, OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener) {
            this.database = database;
            this.onDataObtainedListener = onDataObtainedListener;
        }

        @Override
        public void onDataObtained(ArrayList<Data> datas)
        {
            *//*AllDataListReplace.SetDataInDatabaseAsyncTask<Data> setDataInDatabaseAsyncTask = new AllDataListReplace.SetDataInDatabaseAsyncTask(datas, database, onDataObtainedListener);
            setDataInDatabaseAsyncTask.execute();*//*
        }
    }*/

            /*private class MovieDatabaseGetter extends AsyncTask<Void, Void, ArrayList<MovieData>>
    {
        @Override
        protected ArrayList<MovieData> doInBackground(Void... params) {

            ArrayList<MovieData> movieDatas = movieDB.getAllData();
            ArrayList<String> idMovies = genreMovieDataDB.getAllData();

            ArrayList<MovieData> expectedMovieDatas = new ArrayList<>();

            if(movieDatas != null)
            {
                for (String idMovie:idMovies)
                {
                    for (MovieData movieData:movieDatas)
                    {
                        if(idMovie.equals(movieData.getId()))
                        {
                            expectedMovieDatas.add(movieData);
                            break;
                        }
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
    }*/

            /*ArrayList<String> deletedIdMovieList = DataCheck.CheckData
                    (new NoIDListFinder(idMovieList, genreMovieDataDB));*/

    /*ArrayList<String> willDeletedIdMovieList = DifferentDataFInder.FindData(new IDListDifferentDataFinder(idMovieList, new SameID_IDListCheck(genreMovieDataDB.getAllData())));

            for (String idMovie : willDeletedIdMovieList)
                    {
                    boolean isMovieAvaiable = false;

                    for (DataDB<String> dataDB : otherMovieListDataDB)
        {
                    *//*if (DataCheck.CheckData(new SameID_IDListCheck(dataDB, idMovie)))
                    {
                        isMovieAvaiable = true;
                        break;
                    }*//*

        if(DataCheck.CheckData(new SameID_IDListCheck(dataDB.getAllData()), idMovie))
        {
        isMovieAvaiable = true;
        break;
        }

        }

        if (!isMovieAvaiable)
        {
        movieDB.removeData(idMovie);

        ArrayList<String> deletedIdMovie = new ArrayList<>();
        deletedIdMovie.add(idMovie);

        DataDB<ReviewData> reviewDataDataDB = new ReviewDataDB(context);
        DataDelete.DeleteData(new WithID_DataDelete<>(reviewDataDataDB, deletedIdMovie, new SameID_DepedencyListCheck<>(reviewDataDataDB.getAllData())));

        DataDB<VideoData> videoDataDataDB = new VideoDataDB(context);
        DataDelete.DeleteData(new WithID_DataDelete<>(videoDataDataDB, deletedIdMovie, new SameID_DepedencyListCheck<>(videoDataDataDB.getAllData())));

        DataDB<CastData> castDataDataDB = new CastDataDB(context);
        DataDelete.DeleteData(new WithID_DataDelete<>(castDataDataDB, deletedIdMovie, new SameID_DepedencyListCheck<>(castDataDataDB.getAllData())));

        DataDB<CrewData> crewDataDataDB = new CrewDataDB(context);
        DataDelete.DeleteData(new WithID_DataDelete<>(crewDataDataDB, deletedIdMovie, new SameID_DepedencyListCheck<>(crewDataDataDB.getAllData())));

                    *//*ArrayList<ReviewData> reviewDatas = DataCheck.CheckData(new DependencyDataListFinder<ReviewData>(new ReviewDataDB(context), idMovie));
                    DataDB<ReviewData> reviewDataDB = new ReviewDataDB(context);
                    for (ReviewData reviewData : reviewDatas)
                    {
                        reviewDataDB.removeData(reviewData.getId());
                    }*//*

                    *//*ArrayList<VideoData> videoDatas = DataCheck.CheckData(new DependencyDataListFinder<VideoData>(new VideoDataDB(context), idMovie));
                    DataDB<VideoData> videoDataDB = new VideoDataDB(context);
                    for (VideoData videoData : videoDatas)
                    {
                        videoDataDB.removeData(videoData.getId());
                    }*//*

                    *//*ArrayList<CastData> castDatas = DataCheck.CheckData(new DependencyDataListFinder<CastData>(new CastDataDB(context), idMovie));
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
                    }*//*
        }
        }

        DataReplace.ReplaceData(new SameDataReplace<MovieData>(movieDB, movieDatas, new SameID_DataListCheck<>(movieDB.getAllData())));
        DataReplace.ReplaceData(new SameIDDataReplace(genreMovieDataDB, idMovieList, new SameID_IDListCheck(genreMovieDataDB.getAllData())));

            *//*for (MovieData movieData:movieDatas)
            {
                if(DataCheck.CheckData(new SameID_DataListCheck<MovieData>(movieDB, movieData.getId())))
                {
                    movieDB.removeData(movieData.getId());
                }

                movieDB.addData(movieData);
            }

            genreMovieDataDB.removeAllData();

            for (String idMovie : idMovieList)
            {
                genreMovieDataDB.addData(idMovie);
            }*//*

        return null;*/

