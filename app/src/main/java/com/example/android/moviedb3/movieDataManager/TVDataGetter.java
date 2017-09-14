package com.example.android.moviedb3.movieDataManager;

import android.content.Context;

import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.TVDetailJSONParser;
import com.example.android.moviedb3.jsonParsing.TVIDListJSONParser;
import com.example.android.moviedb3.localDatabase.CastTVDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.CrewTVDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.localDatabase.PeopleDataDB;
import com.example.android.moviedb3.localDatabase.TVDataDB;
import com.example.android.moviedb3.localDatabase.VideoTVDataDB;
import com.example.android.moviedb3.movieDB.CastTVData;
import com.example.android.moviedb3.movieDB.CrewTVData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.movieDB.VideoTVData;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataAvailable.DefaultDataAvailableCheck;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterDefaultThread;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataListGetterDefaultThread;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.BaseDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoIDDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVDataGetter implements IMovieDBGetter
{
    Context context;

    DataDB<String> currentTVListDataDB;
    ArrayList<DataDB<String>> otherTVListDataDB;
    String tvIDListURL;
    DataDB<TVData> tvDB;

    OnDataObtainedListener<Integer> tvDifferentObtainedListener;

    public TVDataGetter(Context context, DataDB<String> currentTVListDataDB, ArrayList<DataDB<String>> otherTVListDataDB, String tvIDListURL) {
        this.context = context;
        this.currentTVListDataDB = currentTVListDataDB;
        this.otherTVListDataDB = otherTVListDataDB;
        this.tvIDListURL = tvIDListURL;

        tvDB = new TVDataDB(context);
    }

    public TVDataGetter(Context context, DataDB<String> currentTVListDataDB, ArrayList<DataDB<String>> otherTVListDataDB, String tvIDListURL, OnDataObtainedListener<Integer> tvDifferentObtainedListener)
    {
        this.context = context;
        this.currentTVListDataDB = currentTVListDataDB;
        this.otherTVListDataDB = otherTVListDataDB;
        this.tvIDListURL = tvIDListURL;
        this.tvDifferentObtainedListener = tvDifferentObtainedListener;

        tvDB = new TVDataDB(context);
    }

    @Override
    public void getData()
    {
        ArrayList<String> tvIDList = NetworkDataGetter.GetDataDefaultThread(new NetworkDataGetterDefaultThread<ArrayList<String>>(new TVIDListJSONParser()), tvIDListURL);

        if(tvIDList == null || !NetworkConnectionChecker.IsConnect(context))
        {
            return;
        }

        String[] tvURLList = new String[tvIDList.size()];

        for(int a = 0; a < tvIDList.size(); a++)
        {
            tvURLList[a] = MovieDataURL.GetTVURL(tvIDList.get(a), context);
        }

        ArrayList<TVData> tvDatas = NetworkDataGetter.GetDataDefaultThread(new NetworkDataListGetterDefaultThread<TVData>(new TVDetailJSONParser()), tvURLList);

        if(tvDatas != null && NetworkConnectionChecker.IsConnect(context))
        {
            NotExpectedDataErase(tvIDList, tvDatas);
        }
    }


    private void NotExpectedDataErase(ArrayList<String> idTVList, ArrayList<TVData> tvDatas)
    {
        ArrayList<String> willDeletedIdTVList = NoDataFinder.FindNotSameID
                (new NoIDDataFinder<>(new IDCompare(), idTVList, currentTVListDataDB.getAllData()));

        for (String idTV : willDeletedIdTVList)
        {
            boolean isTVAvailable = DatabaseTVIsAvailable.isAvailableFromIDList(idTV, otherTVListDataDB, context);

            if (!isTVAvailable)
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

        DataReplace.ReplaceData(new BaseDataListReplace<TVData>(tvDatas, tvDB, new BaseDataCompare<TVData>()));
        DataReplace.ReplaceData(new AllDataListReplace<String>(currentTVListDataDB, idTVList));

        if(tvDifferentObtainedListener != null)
        {
            tvDifferentObtainedListener.onDataObtained(willDeletedIdTVList.size());
        }
    }
}
