package com.example.android.moviedb3.movieDataManager;

import android.content.Context;

import com.example.android.moviedb3.jsonNetworkConnection.JSONHTTPReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.JSONReceiver;
import com.example.android.moviedb3.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.moviedb3.jsonParsing.JSONParser;
import com.example.android.moviedb3.jsonParsing.PeopleBackdropImageJSONParser;
import com.example.android.moviedb3.jsonParsing.PeopleDetailJSONParser;
import com.example.android.moviedb3.jsonParsing.PeoplePopularListJSONParser;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.PeopleCastDataDB;
import com.example.android.moviedb3.localDatabase.PeopleCastTVDataDB;
import com.example.android.moviedb3.localDatabase.PeopleCrewDataDB;
import com.example.android.moviedb3.localDatabase.PeopleCrewTVDataDB;
import com.example.android.moviedb3.localDatabase.PeopleDataDB;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.PeopleCastData;
import com.example.android.moviedb3.movieDB.PeopleCastTvData;
import com.example.android.moviedb3.movieDB.PeopleCrewData;
import com.example.android.moviedb3.movieDB.PeopleCrewTVData;
import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.supportDataManager.dataComparision.BaseDataCompare;
import com.example.android.moviedb3.supportDataManager.dataComparision.DepedencyDataCompare;
import com.example.android.moviedb3.supportDataManager.dataDelete.BaseDataListDelete;
import com.example.android.moviedb3.supportDataManager.dataDelete.DataDelete;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterDefaultThread;
import com.example.android.moviedb3.supportDataManager.dataReplace.AllDataListReplace;
import com.example.android.moviedb3.supportDataManager.dataReplace.DataReplace;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoDataFinder;
import com.example.android.moviedb3.supportDataManager.noDataFinder.NoIDDataFinder;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nugroho on 13/09/17.
 */

public class PeopleDataGetter implements IMovieDBGetter
{
    Context context;
    DataDB<PeopleData> peopleDB;

    public PeopleDataGetter(Context context) {
        this.context = context;
    }

    @Override
    public void getData() throws Exception
    {
        peopleDB = new PeopleDataDB(context);

        ArrayList<PeopleData> peopleDataArrayList = NetworkDataGetter.GetDataDefaultThread
                (new NetworkDataGetterDefaultThread<>(new PeoplePopularListJSONParser()), MovieDataURL.GetPopularPeopleListURL(context));

        if (peopleDataArrayList != null && NetworkConnectionChecker.IsConnect(context))
        {
            ArrayList<PeopleData> dataList = new ArrayList<>();
            JSONReceiver jsonReceiver = new JSONHTTPReceiver();

            for (PeopleData people:peopleDataArrayList)
            {
                JSONParser<PeopleData> peopleDataJSONParser = new PeopleDetailJSONParser(people);
                JSONObject jsonObject = jsonReceiver.ReceiveData(MovieDataURL.GetPeopleDetailURL(people.getId(), context));
                PeopleData newPeopleData = peopleDataJSONParser.Parse(jsonObject);

                JSONParser<PeopleData> peopleBackdropImageJSONParser = new PeopleBackdropImageJSONParser(newPeopleData);
                JSONObject jsonObject2 = jsonReceiver.ReceiveData(MovieDataURL.GetBackdropImagePeopleURL(newPeopleData.getId(), context));
                dataList.add(peopleBackdropImageJSONParser.Parse(jsonObject2));
            }

            DatabasePeopleReplace(dataList);
        }

        else
        {
            throw new Exception();
        }
    }

    private void DatabasePeopleReplace(ArrayList<PeopleData> peopleDataArrayList)
    {
        ArrayList<String> networkPeopleIDList = new ArrayList<>();
        for (PeopleData people:peopleDataArrayList)
        {
            networkPeopleIDList.add(people.getId());
        }

        ArrayList<String> willDeletedIdPeopleList = NoDataFinder.FindNotSameID
                (new NoIDDataFinder<>(new BaseDataCompare<PeopleData>(), peopleDB.getAllData(), networkPeopleIDList));

        for (String idPeople : willDeletedIdPeopleList)
        {
            DataDelete.Delete(new BaseDataListDelete<PeopleCastData>
                    (new DepedencyDataCompare<PeopleCastData>(), new PeopleCastDataDB(context), idPeople));

            DataDelete.Delete(new BaseDataListDelete<PeopleCrewData>
                    (new DepedencyDataCompare<PeopleCrewData>(), new PeopleCrewDataDB(context), idPeople));

            DataDelete.Delete(new BaseDataListDelete<PeopleCastTvData>
                    (new DepedencyDataCompare<PeopleCastTvData>(), new PeopleCastTVDataDB(context), idPeople));

            DataDelete.Delete(new BaseDataListDelete<PeopleCrewTVData>
                    (new DepedencyDataCompare<PeopleCrewTVData>(), new PeopleCrewTVDataDB(context), idPeople));
        }

        DataReplace.ReplaceData(new AllDataListReplace<PeopleData>(peopleDB, peopleDataArrayList));
    }


}
