package com.example.android.moviedb3.movieDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.TVDetailJSONParser;
import com.example.android.moviedb3.jsonParsing.TVIDListJSONParser;
import com.example.android.moviedb3.localDatabase.CastTVDataDB;
import com.example.android.moviedb3.localDatabase.CrewTVDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.TVDataDB;
import com.example.android.moviedb3.localDatabase.VideoTVDataDB;
import com.example.android.moviedb3.movieDB.CastTVData;
import com.example.android.moviedb3.movieDB.CrewTVData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.movieDB.VideoTVData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IDCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataListGetterAsyncTask;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.BaseDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoIDDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVDataGetterAsyncTask implements IMovieDBGetter {

    Context context;
    OnAsyncTaskCompleteListener onAsyncTaskCompleteListener;

    DataDB<String> currentTVListDataDB;
    ArrayList<DataDB<String>> otherTVListDataDB;
    String tvIDListURL;

    boolean allDataHasBeenObtained;
    DataDB<TVData> tvDB;

    public TVDataGetterAsyncTask(Context context, OnAsyncTaskCompleteListener onAsyncTaskCompleteListener,
                                 DataDB<String> currentTVListDataDB, ArrayList<DataDB<String>> otherTVListDataDB,
                                 String tvIDListURL) {
        this.context = context;
        this.onAsyncTaskCompleteListener = onAsyncTaskCompleteListener;

        this.currentTVListDataDB = currentTVListDataDB;
        this.otherTVListDataDB = otherTVListDataDB;
        this.tvIDListURL = tvIDListURL;

        allDataHasBeenObtained = false;
        tvDB = new TVDataDB(context);
    }

    public void getData()
    {
        NetworkDataGetter.GetDataAsyncTask(new NetworkDataGetterAsyncTask<ArrayList<String>>(new TVIDListJSONParser(), new OnTVIDListObtainedListener()), tvIDListURL);
    }

    private class OnTVIDListObtainedListener implements OnDataObtainedListener<ArrayList<String>> {
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
                String[] tvURLList = new String[strings.size()];

                for(int a = 0; a < strings.size(); a++)
                {
                    tvURLList[a] = MovieDataURL.GetTVURL(strings.get(a), context);
                }

                NetworkDataGetter.GetDataAsyncTask(new NetworkDataListGetterAsyncTask<TVData>(new TVDetailJSONParser(), new AllTVDataObtainedListener(strings)), tvURLList);
            }
        }
    }

    private class AllTVDataObtainedListener implements OnDataObtainedListener<ArrayList<TVData>>
    {
        ArrayList<String> idMovieList;

        public AllTVDataObtainedListener(ArrayList<String> idMovieList)
        {
            this.idMovieList = idMovieList;
        }

        @Override
        public void onDataObtained(ArrayList<TVData> tvDatas)
        {
            if(tvDatas != null && NetworkConnectionChecker.IsConnect(context))
            {
                NotExpectedDataEraseAsyncTask notExpectedDataEraseAsyncTask = new NotExpectedDataEraseAsyncTask(idMovieList, tvDatas);
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
        ArrayList<String> idTVList;
        ArrayList<TVData> tvDatas;

        public NotExpectedDataEraseAsyncTask(ArrayList<String> idTVList, ArrayList<TVData> tvDatas)
        {
            this.idTVList = idTVList;
            this.tvDatas = tvDatas;
        }

        @Override
        protected Void doInBackground(Void... params)
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
