package com.example.android.moviedb3.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activity.MovieDetailActivity;
import com.example.android.moviedb3.activityShifter.ActivityLauncher;
import com.example.android.moviedb3.activityShifter.DefaultIActivityLauncher;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MainMovieListRecyclerViewAdapter;
import com.example.android.moviedb3.dataManager.LoadingDataAsyncTask;
import com.example.android.moviedb3.dataManager.dataGetter.BundleDataGetter;
import com.example.android.moviedb3.dataManager.movieDBGetter.DatabaseMovieDBGetter;
import com.example.android.moviedb3.dataManager.movieDBGetter.MovieDataGetter;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.localDatabase.FavoriteDataDB;
import com.example.android.moviedb3.localDatabase.PopularDataDB;
import com.example.android.moviedb3.localDatabase.TopRateDataDB;
import com.example.android.moviedb3.localDatabase.WatchlistDataDB;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;

import java.util.ArrayList;

/**
 * Created by nugroho on 26/08/17.
 */

public class MovieListFragment extends Fragment
{
    private ArrayList<MovieData> movieDataArrayList;
    private DataDB<String> movieListDB;

    RecyclerView movieListRecyclerView;
    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_movie_list, container, false);

        movieListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie_list);
        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);

        GetMovieList(savedInstanceState);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_LIST_PERSISTANCE_KEY, movieDataArrayList);
    }

    public void setMovieListDB(DataDB<String> movieListDB) {
        this.movieListDB = movieListDB;
    }

    private void GetMovieList(Bundle savedInstanceState)
    {
        if(savedInstanceState != null)
        {
            BundleDataGetter bundleDataGetter = new BundleDataGetter(savedInstanceState);
            movieDataArrayList = bundleDataGetter.getDataList(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_LIST_PERSISTANCE_KEY);

            SetRecyclerView(movieDataArrayList);
            ShowRecycleView();
        }

        else
        {
            ShowLoadingData();

            DatabaseMovieDBGetter databaseMovieDBGetter = new DatabaseMovieDBGetter(movieListDB, getContext(), new MainMovieListObtainedListener());
            databaseMovieDBGetter.execute();
        }
    }

    private void ShowNoDataLayout()
    {
        movieListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    private void ShowRecycleView()
    {
        movieListRecyclerView.setVisibility(View.VISIBLE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowLoadingData()
    {
        movieListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void SetRecyclerView(ArrayList<MovieData> movieDataArrayList)
    {
        if (movieDataArrayList == null || movieDataArrayList.size() <= 0)
        {
            return;
        }

        MainMovieListRecyclerViewAdapter mainMovieListRecyclerViewAdapter = new MainMovieListRecyclerViewAdapter(movieDataArrayList, getContext(), new MainMovieDataChoosedListener());
        movieListRecyclerView.setAdapter(mainMovieListRecyclerViewAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        movieListRecyclerView.setLayoutManager(gridLayoutManager);

        movieListRecyclerView.setHasFixedSize(true);
    }

    private ArrayList<DataDB<String>> getInitialOtherMovieListDataDB()
    {
        ArrayList<DataDB<String>> dataDBArrayList = new ArrayList<>();

        dataDBArrayList.add(new TopRateDataDB(getContext()));
        dataDBArrayList.add(new FavoriteDataDB(getContext()));
        dataDBArrayList.add(new WatchlistDataDB(getContext()));

        return dataDBArrayList;
    }

    private class MainMovieDataChoosedListener implements OnDataChooseListener<MovieData>
    {
        @Override
        public void OnDataChoose(MovieData movieData)
        {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MovieDBKeyEntry.MovieDataPersistance.MOVIE_DATA_PERSISTANCE_KEY, movieData);

            ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(MovieDetailActivity.class, bundle, 0, getActivity()));
        }
    }

    private class MainMovieListObtainedListener implements OnDataObtainedListener<ArrayList<MovieData>>
    {
        @Override
        public void onDataObtained(ArrayList<MovieData> movieDatas)
        {
            if(movieDatas == null)
            {
                ShowNoDataLayout();
            }

            else
            {
                movieDataArrayList = movieDatas;

                SetRecyclerView(movieDataArrayList);
                ShowRecycleView();
            }
        }
    }

}

    /*String movieIDURL;
    DataDB<String> currentMovieIdDB;
    ArrayList<DataDB<String>> othersMovieIdDB;*/

/*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(movieIDURL.isEmpty() || movieIDURL == null)
        {
            movieIDURL = MovieDataURL.GetPopularURL();
        }

        if(currentMovieIdDB == null)
        {
            currentMovieIdDB = new PopularDataDB(getContext());
        }

        if(othersMovieIdDB == null)
        {
            othersMovieIdDB = getInitialOtherMovieListDataDB();
        }
    }*/

/*public void setAllMovieIDDataDB(DataDB<String> currentMovieIdDB, ArrayList<DataDB<String>> othersMovieIdDB,
                                    String movieIDURL)
    {
        this.currentMovieIdDB = currentMovieIdDB;
        this.othersMovieIdDB = othersMovieIdDB;
        this.movieIDURL = movieIDURL;
    }

    private void GetMovieList()
    {
        ShowLoadingData();

        MovieDataGetter movieDataGetter = new MovieDataGetter(getContext(),
                new MainMovieListObtainedListener(), currentMovieIdDB, othersMovieIdDB, movieIDURL);
        movieDataGetter.Execute();
    }*/



/*    private class MainMovieListObtainedListener implements OnDataObtainedListener<ArrayList<MovieData>>
    {
        @Override
        public void onDataObtained(ArrayList<MovieData> movieDatas) {

            movieDataArrayList = movieDatas;

            AddMovieDataToDatabase(movieDataArrayList);
            SetRecyclerView(movieDataArrayList);
            ShowRecycleView();
        }
    }
}

    private void AddMovieDataToDatabase(ArrayList<MovieData> movieDatas)
    {
        DataDB<MovieData> movieDataDB = new MovieDataDB(getContext());

        for (MovieData moviedata:movieDatas)
        {
            movieDataDB.addData(moviedata);
        }
    }*/

/*    private class MovieIDListObtainedListener implements OnDataObtainedListener<ArrayList<String>>
    {
        @Override
        public void onDataObtained(ArrayList<String> strings) {

            String[] urlList  = new String[strings.size()];

            for(int a = 0; a < strings.size(); a++)
            {
                String url = "https://api.themoviedb.org/3/movie/" + strings.get(a) + "?api_key=" + APIKey;
                urlList[a] = url;
            }

            for (String idMovie:strings)
            {
                DataFindCheck.CheckData(new SameID_IDListFindCheck(dataIDMovieListDB, idMovie, new OnDataObtainedListener<Boolean>() {
                    @Override
                    public void onDataObtained(Boolean aBoolean) {

                        if(!aBoolean)
                        {

                        }
                    }
                }));


            }



        }
    }*/
