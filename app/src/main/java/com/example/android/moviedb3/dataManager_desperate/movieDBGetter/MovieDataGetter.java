package com.example.android.moviedb3.dataManager_desperate.movieDBGetter;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.dataManager_desperate.dataCheck.DataCheck;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.DataFind;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.DependencyDataListFinder;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_DataListCheck;
import com.example.android.moviedb3.dataManager_desperate.dataCheck.SameID_IDListCheck;
import com.example.android.moviedb3.dataManager_desperate.dataFinder.NoIDListFinder;
import com.example.android.moviedb3.dataManager_desperate.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.dataManager_desperate.dataGetter.NetworkDataGetterSyncTask;
import com.example.android.moviedb3.dataManager_desperate.dataGetter.NetworkDataListGetterSyncTask;
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




    /*public <Data> void GetDataAsyncTask(@NonNull DataDB<Data> database, OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener, @NonNull JSONParser<Data> dataJSONParser, @NonNull String... urlList)
    {
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataListGetterAsyncTask<Data>(dataJSONParser, new AllDataListReplace.OnDataListObtained(database, onDataObtainedListener)), urlList);
    }

    public <Data> void GetDataAsyncTask(@NonNull DataDB<Data> database, OnDataObtainedListener<ArrayList<Data>> onDataObtainedListener, @NonNull JSONParser<ArrayList<Data>> dataListjsonParser, @NonNull String url)
    {
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<Data>>(dataListjsonParser, new AllDataListReplace.OnDataListObtained(database, onDataObtainedListener)), url);
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

            /*private class MovieDatabaseGetter extends AsyncTask<Void, Void, ArrayList<PeopleData>>
    {
        @Override
        protected ArrayList<PeopleData> doInBackground(Void... params) {

            ArrayList<PeopleData> movieDatas = movieDB.getAllData();
            ArrayList<String> idMovies = currentMovieListDataDB.getAllData();

            ArrayList<PeopleData> expectedMovieDatas = new ArrayList<>();

            if(movieDatas != null)
            {
                for (String idMovie:idMovies)
                {
                    for (PeopleData movieData:movieDatas)
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
        protected void onPostExecute(ArrayList<PeopleData> movieDatas)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(movieDatas);
            }
        }
    }*/

