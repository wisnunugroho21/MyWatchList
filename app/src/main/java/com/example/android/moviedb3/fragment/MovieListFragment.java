package com.example.android.moviedb3.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MainLinearMovieListRecyclerViewAdapter;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MainMovieListRecyclerViewAdapter;
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.GenreMovieData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.DatabaseGenreMovieGetter;
import com.example.android.moviedb3.movieDataManager.DatabaseMovieGetter;
import com.example.android.moviedb3.movieDataManager.GenreMovieGetter;

import java.util.ArrayList;

/**
 * Created by nugroho on 26/08/17.
 */

public class MovieListFragment extends Fragment
{
    private ArrayList<MovieData> movieDataArrayList;
    private DataDB<String> movieListDB;
    private boolean isLinearList = false;

    String idGenre;
    String urlGenreMovie;
    DataDB<GenreMovieData> genreMovieDataDB;

    RecyclerView movieListRecyclerView;
    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_tv_movie_list, container, false);

        movieListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie_list);
        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);

        GetMovieList();
        return view;
    }

    public void setMovieListDB(DataDB<String> movieListDB) {
        this.movieListDB = movieListDB;
    }

    public void setGenre(String idGenre, DataDB<GenreMovieData> genreMovieDataDB, String urlGenreMovie)
    {
        this.idGenre = idGenre;
        this.genreMovieDataDB = genreMovieDataDB;
        this.urlGenreMovie = urlGenreMovie;
    }

    public void setLinearList(boolean linearList)
    {
        isLinearList = linearList;
    }

    private void GetMovieList()
    {
        if(movieListDB != null)
        {
            GetMovieListFromDatabase();
        }

        if(idGenre != null && genreMovieDataDB != null)
        {
            GetMovieListFromGenreMovie();
        }
    }

    public void GetMovieListFromDatabase()
    {
        ShowLoadingData();
        DBGetter.GetData(new DatabaseMovieGetter(movieListDB, getContext(), new MainMovieListObtainedListener()));
    }

    public void GetMovieListFromGenreMovie()
    {
        ShowLoadingData();
        DBGetter.GetData(new GenreMovieGetter(idGenre, getContext(), new AllGenreMovieObtainedListener(), genreMovieDataDB, urlGenreMovie));
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

        if(isLinearList)
        {
            MainLinearMovieListRecyclerViewAdapter mainMovieListRecyclerViewAdapter = new MainLinearMovieListRecyclerViewAdapter(movieDataArrayList, getContext(), new MainMovieDataChoosedListener());
            movieListRecyclerView.setAdapter(mainMovieListRecyclerViewAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            movieListRecyclerView.setLayoutManager(linearLayoutManager);

            movieListRecyclerView.setHasFixedSize(true);
        }

        else
        {
            MainMovieListRecyclerViewAdapter mainMovieListRecyclerViewAdapter = new MainMovieListRecyclerViewAdapter(movieDataArrayList, getContext(), new MainMovieDataChoosedListener());
            movieListRecyclerView.setAdapter(mainMovieListRecyclerViewAdapter);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            movieListRecyclerView.setLayoutManager(gridLayoutManager);

            movieListRecyclerView.setHasFixedSize(true);
        }


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
                if(movieDatas.isEmpty())
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

    private class AllGenreMovieObtainedListener implements OnAsyncTaskCompleteListener
    {
        @Override
        public void onComplete(boolean isSuccess)
        {
            DBGetter.GetData(new DatabaseGenreMovieGetter(getContext(), new MainMovieListObtainedListener(), genreMovieDataDB, idGenre));
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

        MovieDataGetterAsyncTask movieDataGetter = new MovieDataGetterAsyncTask(getContext(),
                new MainMovieListObtainedListener(), currentMovieIdDB, othersMovieIdDB, movieIDURL);
        movieDataGetter.getData();
    }*/



/*    private class MainMovieListObtainedListener implements OnDataObtainedListener<ArrayList<PeopleData>>
    {
        @Override
        public void onDataObtained(ArrayList<PeopleData> movieDatas) {

            movieDataArrayList = movieDatas;

            AddMovieDataToDatabase(movieDataArrayList);
            SetRecyclerView(movieDataArrayList);
            ShowRecycleView();
        }
    }
}

    private void AddMovieDataToDatabase(ArrayList<PeopleData> movieDatas)
    {
        DataDB<PeopleData> movieDataDB = new MovieDataDB(getContext());

        for (PeopleData moviedata:movieDatas)
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
                DataCheck.CheckData(new SameID_IDListCheck(dataIDMovieListDB, idMovie, new OnDataObtainedListener<Boolean>() {
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
