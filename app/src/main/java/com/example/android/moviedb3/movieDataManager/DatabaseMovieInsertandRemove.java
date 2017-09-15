package com.example.android.moviedb3.movieDataManager;

import android.content.Context;

import com.example.android.moviedb3.localDatabase.CastDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.localDatabase.ReviewDataDB;
import com.example.android.moviedb3.localDatabase.VideoDataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDB.VideoData;
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

public class DatabaseMovieInsertandRemove
{
    public void Insert(MovieData movieData, ArrayList<CastData> castDatas, ArrayList<CrewData> crewDatas,
                       ArrayList<VideoData> videoDatas, ArrayList<ReviewData> reviewDatas, Context context)
    {
        DataDB<MovieData> movieDB = new MovieDataDB(context);
        ArrayList<MovieData> movieDatas = new ArrayList<>();
        movieDatas.add(movieData);

        DataReplace.ReplaceData(new BaseDataListReplace<MovieData>(movieDatas, movieDB, new BaseDataCompare<MovieData>()));

        DataDB<CastData> castDB = new CastDataDB(context);
        ArrayList<CastData> databaseCastDataArrayList = castDB.getAllData();

        if(databaseCastDataArrayList != null)
        {
            for (CastData databaseData:databaseCastDataArrayList)
            {
                if(movieData.getId().equals(databaseData.getIDDependent()))
                {
                    castDB.removeData(databaseData.getId());
                }
            }
        }

        if(castDatas != null)
        {
            for (CastData data:castDatas)
            {
                castDB.addData(data);
            }
        }

        DataDB<CrewData> crewDB = new CrewDataDB(context);
        ArrayList<CrewData> databaseCrewDataArrayList = crewDB.getAllData();

        if(databaseCrewDataArrayList != null)
        {
            for (CrewData databaseData:databaseCrewDataArrayList)
            {
                if(movieData.getId().equals(databaseData.getIDDependent()))
                {
                    crewDB.removeData(databaseData.getId());
                }
            }
        }

        if(crewDatas != null)
        {
            for (CrewData data:crewDatas)
            {
                crewDB.addData(data);
            }
        }

        DataDB<VideoData> videoDB = new VideoDataDB(context);
        ArrayList<VideoData> databaseVideoDataArrayList = videoDB.getAllData();

        if(databaseVideoDataArrayList != null)
        {
            for (VideoData databaseData:databaseVideoDataArrayList)
            {
                if(movieData.getId().equals(databaseData.getIDDependent()))
                {
                    videoDB.removeData(databaseData.getId());
                }
            }
        }

        if(videoDatas != null)
        {
            for (VideoData data:videoDatas)
            {
                videoDB.addData(data);
            }
        }

        DataDB<ReviewData> reviewDB = new ReviewDataDB(context);
        ArrayList<ReviewData> databaseReviewDataArrayList = reviewDB.getAllData();

        if(databaseReviewDataArrayList != null)
        {
            for (ReviewData databaseData:databaseReviewDataArrayList)
            {
                if(movieData.getId().equals(databaseData.getIDDependent()))
                {
                    reviewDB.removeData(databaseData.getId());
                }
            }
        }

        if(reviewDatas != null)
        {
            for (ReviewData data:reviewDatas)
            {
                reviewDB.addData(data);
            }
        }
    }

    public void Remove(String idMovie, ArrayList<DataDB<String>> otherMovieListDataDB, Context context)
    {
        boolean isMovieAvaiable = DatabaseMovieIsAvailable.isAvailableFromIDList(idMovie, otherMovieListDataDB, context);

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
