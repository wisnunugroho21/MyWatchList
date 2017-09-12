package com.example.android.moviedb3.movieDataManager;

import android.content.Context;

import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.TVGenreListJSONParser;
import com.example.android.moviedb3.localDatabase.CastTVDataDB;
import com.example.android.moviedb3.localDatabase.CrewTVDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.GenreTVPopularDB;
import com.example.android.moviedb3.localDatabase.GenreTVTopRateDataDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.localDatabase.TVGenreDB;
import com.example.android.moviedb3.localDatabase.VideoTVDataDB;
import com.example.android.moviedb3.movieDB.CastTVData;
import com.example.android.moviedb3.movieDB.CrewTVData;
import com.example.android.moviedb3.movieDB.GenreTvData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.TVGenre;
import com.example.android.moviedb3.movieDB.VideoTVData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.IntermedieteDataCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterDefaultThread;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoIDDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameDataFinder;
import com.example.android.moviedb3.supportDataManager.sameDataFinder.SameIDDataFinder;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVGenreDataGetter implements IMovieDBGetter
{
    Context context;
    String genreURL;

    public TVGenreDataGetter(Context context, String genreURL) {
        this.context = context;
        this.genreURL = genreURL;
    }

    @Override
    public void getData()
    {
        ArrayList<TVGenre> tvGenreArrayList = NetworkDataGetter.GetDataDefaultThread(new NetworkDataGetterDefaultThread<ArrayList<TVGenre>>(new TVGenreListJSONParser()), genreURL);

        if(tvGenreArrayList != null && NetworkConnectionChecker.IsConnect(context))
        {
            DatabaseGenreReplace(tvGenreArrayList);
        }
    }

    private void DatabaseGenreReplace(ArrayList<TVGenre> networkTVGenreDataArrayList)
    {
        DataDB<TVGenre> genreDataDataDB = new TVGenreDB(context);

        ArrayList<String> idNetworkGenreArrayList = new ArrayList<>();
        for (TVGenre genreData:networkTVGenreDataArrayList)
        {
            idNetworkGenreArrayList.add(genreData.getId());
        }

        ArrayList<TVGenre> deletedGenreList = NoDataFinder.FindNotSameData
                (new NoIDDataFinder<TVGenre>(new BaseDataCompare<TVGenre>(), genreDataDataDB.getAllData(), idNetworkGenreArrayList));

        if(deletedGenreList != null && !deletedGenreList.isEmpty())
        {
            ArrayList<GenreTvData> deletedGenreTVDataList = new ArrayList<>();

            ArrayList<String> idDeletedGenreList = new ArrayList<>();
            for (TVGenre deletedGenre:deletedGenreList)
            {
                idDeletedGenreList.add(deletedGenre.getId());
            }

            DataDB<GenreTvData> genrePopularTVDataDB = new GenreTVPopularDB(context);
            ArrayList<GenreTvData> deletedGenrePopularTVDataList = SameDataFinder.getDataSameList
                    (new SameIDDataFinder<GenreTvData>(new IntermedieteDataCompare<GenreTvData>
                            (IntermedieteDataCompare.CHECK_SECOND_ID), genrePopularTVDataDB.getAllData(), idDeletedGenreList));

            DataDB<GenreTvData> genreTopRateTVDataDB = new GenreTVTopRateDataDB(context);
            ArrayList<GenreTvData> deletedGenreTopRateTVDataList = SameDataFinder.getDataSameList
                    (new SameIDDataFinder<GenreTvData>(new IntermedieteDataCompare<GenreTvData>
                            (IntermedieteDataCompare.CHECK_SECOND_ID), genreTopRateTVDataDB.getAllData(), idDeletedGenreList));

            deletedGenreTVDataList.addAll(deletedGenrePopularTVDataList);

            for(GenreTvData genreTVData:deletedGenreTVDataList)
            {
                for(GenreTvData toprateGenreTV:deletedGenreTopRateTVDataList)
                {
                    if(!genreTVData.getIdTV().equals(toprateGenreTV.getIdTV()))
                    {
                        deletedGenreTVDataList.add(toprateGenreTV);
                    }
                }
            }

            for(GenreTvData genreTvData:deletedGenreTVDataList)
            {
                String idTV = genreTvData.getIdTV();

                boolean isTVAvaiable = DatabaseMovieIsAvailable.isAvailableFromGenreList(idTV, genreTvData.getIdGenre(), context);

                if (!isTVAvaiable)
                {
                    DataDB<MovieData> movieDB = new MovieDataDB(context);
                    movieDB.removeData(idTV);

                    DataDelete.Delete(new BaseDataListDelete<VideoTVData>
                            (new DepedencyDataCompare<VideoTVData>(), new VideoTVDataDB(context), idTV));
                    DataDelete.Delete(new BaseDataListDelete<CastTVData>
                            (new DepedencyDataCompare<CastTVData>(), new CastTVDataDB(context), idTV));
                    DataDelete.Delete(new BaseDataListDelete<CrewTVData>
                            (new DepedencyDataCompare<CrewTVData>(), new CrewTVDataDB(context), idTV));
                }
            }
        }

        DataReplace.ReplaceData(new AllDataListReplace<TVGenre>(new TVGenreDB(context), networkTVGenreDataArrayList));
    }
}

