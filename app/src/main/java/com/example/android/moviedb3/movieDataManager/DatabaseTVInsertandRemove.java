package com.example.android.moviedb3.movieDataManager;

import android.content.Context;

import com.example.android.moviedb3.localDatabase.CastDataDB;
import com.example.android.moviedb3.localDatabase.CastTVDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.CrewTVDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.localDatabase.ReviewDataDB;
import com.example.android.moviedb3.localDatabase.TVDataDB;
import com.example.android.moviedb3.localDatabase.VideoDataDB;
import com.example.android.moviedb3.localDatabase.VideoTVDataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CastTVData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.CrewTVData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.movieDB.VideoTVData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataReplace.BaseDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;

import java.util.ArrayList;

/**
 * Created by nugroho on 15/09/17.
 */

public class DatabaseTVInsertandRemove
{
    public void Insert(TVData tvData, ArrayList<CastTVData> castTVDatas, ArrayList<CrewTVData> crewTVDatas,
                       ArrayList<VideoTVData> videoTVDatas, Context context)
    {
        DataDB<TVData> tvDB = new TVDataDB(context);
        ArrayList<TVData> tvDatas = new ArrayList<>();
        tvDatas.add(tvData);

        DataReplace.ReplaceData(new BaseDataListReplace<TVData>(tvDatas, tvDB, new BaseDataCompare<TVData>()));

        DataDB<CastTVData> castTVDataDB = new CastTVDataDB(context);
        ArrayList<CastTVData> databaseCastTVDataArrayList = castTVDataDB.getAllData();

        if(databaseCastTVDataArrayList != null)
        {
            for (CastTVData databaseData:databaseCastTVDataArrayList)
            {
                if(tvData.getId().equals(databaseData.getIDDependent()))
                {
                    castTVDataDB.removeData(databaseData.getId());
                }
            }
        }

        if(castTVDatas != null)
        {
            for (CastTVData data:castTVDatas)
            {
                castTVDataDB.addData(data);
            }
        }

        DataDB<CrewTVData> crewTVDataDB = new CrewTVDataDB(context);
        ArrayList<CrewTVData> databaseCrewTVDataArrayList = crewTVDataDB.getAllData();

        if(databaseCrewTVDataArrayList != null)
        {
            for (CrewTVData databaseData:databaseCrewTVDataArrayList)
            {
                if(tvData.getId().equals(databaseData.getIDDependent()))
                {
                    crewTVDataDB.removeData(databaseData.getId());
                }
            }
        }

        if(crewTVDatas != null)
        {
            for (CrewTVData data:crewTVDatas)
            {
                crewTVDataDB.addData(data);
            }
        }

        DataDB<VideoTVData> videoTVDataDB = new VideoTVDataDB(context);
        ArrayList<VideoTVData> databaseVideoTVDataArrayList = videoTVDataDB.getAllData();

        if(databaseVideoTVDataArrayList != null)
        {
            for (VideoTVData databaseData:databaseVideoTVDataArrayList)
            {
                if(tvData.getId().equals(databaseData.getIDDependent()))
                {
                    videoTVDataDB.removeData(databaseData.getId());
                }
            }
        }

        if(videoTVDatas != null)
        {
            for (VideoTVData data:videoTVDatas)
            {
                videoTVDataDB.addData(data);
            }
        }
    }

    public void Remove(String idTV, ArrayList<DataDB<String>> otherTVListDataDB, Context context)
    {
        boolean isTVAvailable = DatabaseTVIsAvailable.isAvailableFromIDList(idTV, otherTVListDataDB, context);

        if (!isTVAvailable)
        {
            DataDB<TVData> tvDB = new TVDataDB(context);
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
