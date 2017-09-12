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
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataListGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.BaseDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoIDDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 27/08/17.
 */

public class MovieDataGetterAsyncTask implements IMovieDBGetter {

    Context context;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    DataDB<String> currentMovieListDataDB;
    ArrayList<DataDB<String>> otherMovieListDataDB;
    String movieIDListURL;

    boolean allDataHasBeenObtained;
    DataDB<MovieData> movieDB;

    public MovieDataGetterAsyncTask(Context context, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener, DataDB<String> currentMovieListDataDB, ArrayList<DataDB<String>> otherMovieListDataDB, String movieIDListURL) {
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
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<String>>(new MovieIDListJSONParser(), new OnMovieIDListObtainedListener()), movieIDListURL);
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
                    movieURLList[a] = MovieDataURL.GetMovieURL(strings.get(a), context);
                }

                NetworkDataGetter.GetDataAsyncTask(new NetworkDataListGetterAsyncTask<MovieData>(new MovieDetailJSONParser(), new AllMovieDataObtainedListener(strings)), movieURLList);
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
                    (new NoIDDataFinder<>(new IDCompare(), idMovieList, currentMovieListDataDB.getAllData()));

            for (String idMovie : willDeletedIdMovieList)
            {
                boolean isMovieAvaiable = DatabaseMovieIsAvailable.isAvailableFromIDList(idMovie, otherMovieListDataDB, context);

                if (!isMovieAvaiable)
                {
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

            DataReplace.ReplaceData(new BaseDataListReplace<MovieData>(movieDatas, movieDB, new BaseDataCompare<MovieData>()));
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
    OnDataObtainedListener<ArrayList<Data>> onAllPeopleObtainedListener;

    JSONParser<ArrayList<Data>> dataListjsonParser;
    String[] urlList;

    JSONParser<Data> dataJSONParser;
    String url;

    public AllDataListReplace(@NonNull DataDB<Data> parentDataDB, OnDataObtainedListener<ArrayList<Data>> onAllPeopleObtainedListener, @NonNull JSONParser<ArrayList<Data>> dataListjsonParser, @NonNull String url) {
        this.parentDataDB = parentDataDB;
        this.onAllPeopleObtainedListener = onAllPeopleObtainedListener;
        this.dataListjsonParser = dataListjsonParser;
        this.url = url;
    }

    public AllDataListReplace(@NonNull DataDB<Data> parentDataDB, OnDataObtainedListener<ArrayList<Data>> onAllPeopleObtainedListener, @NonNull JSONParser<Data> dataJSONParser, @NonNull String... urlList) {
        this.parentDataDB = parentDataDB;
        this.onAllPeopleObtainedListener = onAllPeopleObtainedListener;
        this.dataJSONParser = dataJSONParser;
        this.urlList = urlList;
    }*/

    /*public void GetDataAsyncTask()
    {
        NetworkDataGetter<ArrayList<Data>> networkDataGetter = new NetworkDataGetter<>();

        if(dataJSONParser == null)
        {
            networkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<Data>>(dataListjsonParser, new OnDataListObtained()), url);
        }

        else
        {
            networkDataGetter.GetDataAsyncTask(new NetworkDataListGetterAsyncTask<Data>(dataJSONParser, new OnDataListObtained()), urlList);
        }
    }*/




    /*public <Data> void GetDataAsyncTask(@NonNull DataDB<Data> database, OnDataObtainedListener<ArrayList<Data>> onAllPeopleObtainedListener, @NonNull JSONParser<Data> dataJSONParser, @NonNull String... urlList)
    {
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataListGetterAsyncTask<Data>(dataJSONParser, new AllDataListReplace.OnDataListObtained(database, onAllPeopleObtainedListener)), urlList);
    }

    public <Data> void GetDataAsyncTask(@NonNull DataDB<Data> database, OnDataObtainedListener<ArrayList<Data>> onAllPeopleObtainedListener, @NonNull JSONParser<ArrayList<Data>> dataListjsonParser, @NonNull String url)
    {
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<Data>>(dataListjsonParser, new AllDataListReplace.OnDataListObtained(database, onAllPeopleObtainedListener)), url);
    }

    private class OnDataListObtained<Data> implements OnDataObtainedListener<ArrayList<Data>>
    {
        DataDB<Data> database;
        OnDataObtainedListener<ArrayList<Data>> onAllPeopleObtainedListener;

        public OnDataListObtained(DataDB<Data> database, OnDataObtainedListener<ArrayList<Data>> onAllPeopleObtainedListener) {
            this.database = database;
            this.onAllPeopleObtainedListener = onAllPeopleObtainedListener;
        }

        @Override
        public void onDataObtained(ArrayList<Data> datas)
        {
            *//*AllDataListReplace.SetDataInDatabaseAsyncTask<Data> setDataInDatabaseAsyncTask = new AllDataListReplace.SetDataInDatabaseAsyncTask(datas, database, onAllPeopleObtainedListener);
            setDataInDatabaseAsyncTask.execute();*//*
        }
    }*/

            /*private class MovieDatabaseGetter extends AsyncTask<Void, Void, ArrayList<PeopleData>>
    {
        @Override
        protected ArrayList<PeopleData> doInBackground(Void... params) {

            ArrayList<PeopleData> tvDatas = tvDB.getAllData();
            ArrayList<String> idMovies = genreTVDataDB.getAllData();

            ArrayList<PeopleData> expectedMovieDatas = new ArrayList<>();

            if(tvDatas != null)
            {
                for (String idMovie:idMovies)
                {
                    for (PeopleData movieData:tvDatas)
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
        protected void onPostExecute(ArrayList<PeopleData> tvDatas)
        {
            if(onAllPeopleObtainedListener != null)
            {
                onAllPeopleObtainedListener.onDataObtained(tvDatas);
            }
        }
    }*/

            /*ArrayList<String> deletedIdMovieList = DataCheck.CheckData
                    (new NoIDListFinder(idTVList, genreTVDataDB));*/

    /*ArrayList<String> willDeletedIdMovieList = DifferentDataFInder.FindData(new IDListDifferentDataFinder(idTVList, new SameID_IDListCheck(genreTVDataDB.getAllData())));

            for (String idMovie : willDeletedIdMovieList)
                    {
                    boolean isMovieAvaiable = false;

                    for (DataDB<String> dataDB : otherTVListDataDB)
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
        tvDB.removeData(idMovie);

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

        DataReplace.ReplaceData(new SameDataReplace<PeopleData>(tvDB, tvDatas, new SameID_DataListCheck<>(tvDB.getAllData())));
        DataReplace.ReplaceData(new SameIDDataReplace(genreTVDataDB, idTVList, new SameID_IDListCheck(genreTVDataDB.getAllData())));

            *//*for (PeopleData movieData:tvDatas)
            {
                if(DataCheck.CheckData(new SameID_DataListCheck<PeopleData>(tvDB, movieData.getId())))
                {
                    tvDB.removeData(movieData.getId());
                }

                tvDB.addData(movieData);
            }

            genreTVDataDB.removeAllData();

            for (String idMovie : idTVList)
            {
                genreTVDataDB.addData(idMovie);
            }*//*

        return null;*/

