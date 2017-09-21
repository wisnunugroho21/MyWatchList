package com.example.android.moviedb3.movieDataManager;

import android.content.Context;

import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.GenreListJSONParser;
import com.example.android.moviedb3.localDatabase.CastDataDB;
import com.example.android.moviedb3.localDatabase.CrewDataDB;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.GenreDataDB;
import com.example.android.moviedb3.localDatabase.GenreMoviePopularDB;
import com.example.android.moviedb3.localDatabase.GenreMovieTopRateDB;
import com.example.android.moviedb3.localDatabase.MovieDataDB;
import com.example.android.moviedb3.localDatabase.ReviewDataDB;
import com.example.android.moviedb3.localDatabase.VideoDataDB;
import com.example.android.moviedb3.movieDB.CastData;
import com.example.android.moviedb3.movieDB.CrewData;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.ReviewData;
import com.example.android.moviedb3.movieDB.VideoData;
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
 * Created by nugroho on 07/09/17.
 */

public class GenreDataGetter implements IMovieDBGetter
{
    Context context;
    String genreURL;

    public GenreDataGetter(Context context, String genreURL) {
        this.context = context;
        this.genreURL = genreURL;
    }

    @Override
    public void getData() throws Exception
    {
        ArrayList<GenreData> genreDataArrayList = NetworkDataGetter.GetDataDefaultThread(new NetworkDataGetterDefaultThread<ArrayList<GenreData>>(new GenreListJSONParser()), genreURL);

        if(genreDataArrayList != null && NetworkConnectionChecker.IsConnect(context))
        {
            DatabaseGenreReplace(genreDataArrayList);
        }

        else
        {
            throw new Exception();
        }
    }

    private void DatabaseGenreReplace(ArrayList<GenreData> networkGenreDataArrayList)
    {
        DataDB<GenreData> genreDataDataDB = new GenreDataDB(context);

        ArrayList<String> idNetworkGenreArrayList = new ArrayList<>();
        for (GenreData genreData:networkGenreDataArrayList)
        {
            idNetworkGenreArrayList.add(genreData.getId());
        }

        ArrayList<GenreData> deletedGenreList = NoDataFinder.FindNotSameData
                (new NoIDDataFinder<GenreData>(new BaseDataCompare<GenreData>(), genreDataDataDB.getAllData(), idNetworkGenreArrayList));

        if(deletedGenreList != null && !deletedGenreList.isEmpty())
        {
            ArrayList<GenreMovieData> deletedGenreMovieDataList = new ArrayList<>();

            ArrayList<String> idDeletedGenreList = new ArrayList<>();
            for (GenreData deletedGenre:deletedGenreList)
            {
                idDeletedGenreList.add(deletedGenre.getId());
            }

            DataDB<GenreMovieData> genrePopularMovieDataDB = new GenreMoviePopularDB(context);
            ArrayList<GenreMovieData> deletedGenrePopularMovieDataList = SameDataFinder.getDataSameList
                    (new SameIDDataFinder<GenreMovieData>(new IntermedieteDataCompare<GenreMovieData>
                            (IntermedieteDataCompare.CHECK_SECOND_ID), genrePopularMovieDataDB.getAllData(), idDeletedGenreList));

            DataDB<GenreMovieData> genreTopRateMovieDataDB = new GenreMovieTopRateDB(context);
            ArrayList<GenreMovieData> deletedGenreTopRateMovieDataList = SameDataFinder.getDataSameList
                    (new SameIDDataFinder<GenreMovieData>(new IntermedieteDataCompare<GenreMovieData>
                            (IntermedieteDataCompare.CHECK_SECOND_ID), genreTopRateMovieDataDB.getAllData(), idDeletedGenreList));

            deletedGenreMovieDataList.addAll(deletedGenrePopularMovieDataList);

            for(GenreMovieData genreMovieData:deletedGenreMovieDataList)
            {
                for(GenreMovieData toprateGenreMovie:deletedGenreTopRateMovieDataList)
                {
                    if(!genreMovieData.getIdMovie().equals(toprateGenreMovie.getIdMovie()))
                    {
                        deletedGenreMovieDataList.add(toprateGenreMovie);
                    }
                }
            }

            for(GenreMovieData genreMovieData:deletedGenreMovieDataList)
            {
                String idMovie = genreMovieData.getIdMovie();

                boolean isMovieAvaiable = DatabaseMovieIsAvailable.isAvailableFromGenreList(idMovie, genreMovieData.getIdGenre(), context);

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

        DataReplace.ReplaceData(new AllDataListReplace<GenreData>(new GenreDataDB(context), networkGenreDataArrayList));
    }
}
