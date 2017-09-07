package com.example.android.moviedb3.movieDataManager;

import android.content.Context;

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
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterDefaultThread;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataListGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataListGetterDefaultThread;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.BaseDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoIDDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 07/09/17.
 */

public class MovieDataGetter implements IMovieDBGetter
{
    Context context;

    DataDB<String> currentMovieListDataDB;
    ArrayList<DataDB<String>> otherMovieListDataDB;
    String movieIDListURL;
    DataDB<MovieData> movieDB;

    OnDataObtainedListener<Integer> movieDifferentObtainedListener;

    public MovieDataGetter(Context context, DataDB<String> currentMovieListDataDB, ArrayList<DataDB<String>> otherMovieListDataDB, String movieIDListURL) {
        this.context = context;
        this.currentMovieListDataDB = currentMovieListDataDB;
        this.otherMovieListDataDB = otherMovieListDataDB;
        this.movieIDListURL = movieIDListURL;

        movieDB = new MovieDataDB(context);
    }

    public MovieDataGetter(Context context, DataDB<String> currentMovieListDataDB, ArrayList<DataDB<String>> otherMovieListDataDB, String movieIDListURL, OnDataObtainedListener<Integer> movieDifferentObtainedListener)
    {
        this.context = context;
        this.currentMovieListDataDB = currentMovieListDataDB;
        this.otherMovieListDataDB = otherMovieListDataDB;
        this.movieIDListURL = movieIDListURL;
        this.movieDifferentObtainedListener = movieDifferentObtainedListener;

        movieDB = new MovieDataDB(context);
    }

    @Override
    public void getData()
    {
        ArrayList<String> movieIDList = NetworkDataGetter.GetDataDefaultThread(new NetworkDataGetterDefaultThread<ArrayList<String>>(new MovieIDListJSONParser()), movieIDListURL);

        if(movieIDList == null || !NetworkConnectionChecker.IsConnect(context))
        {
            return;
        }

        String[] movieURLList = new String[movieIDList.size()];

        for(int a = 0; a < movieIDList.size(); a++)
        {
            movieURLList[a] = MovieDataURL.GetMovieURL(movieIDList.get(a));
        }

        ArrayList<MovieData> movieDatas = NetworkDataGetter.GetDataDefaultThread(new NetworkDataListGetterDefaultThread<MovieData>(new MovieDetailJSONParser()), movieURLList);

        if(movieDatas != null && NetworkConnectionChecker.IsConnect(context))
        {
            NotExpectedDataErase(movieIDList, movieDatas);
        }
    }


    private void NotExpectedDataErase(ArrayList<String> idMovieList, ArrayList<MovieData> movieDatas)
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

        if(movieDifferentObtainedListener != null)
        {
            movieDifferentObtainedListener.onDataObtained(willDeletedIdMovieList.size());
        }
    }
}
