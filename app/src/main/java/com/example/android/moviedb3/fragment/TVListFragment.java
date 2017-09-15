package com.example.android.moviedb3.fragment;

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
import com.example.android.moviedb3.activity.TVDetailActivity;
import com.example.android.moviedb3.activityShifter.ActivityLauncher;
import com.example.android.moviedb3.activityShifter.DefaultIActivityLauncher;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MainTVListRecyclerViewAdapter;
import com.example.android.moviedb3.eventHandler.OnAsyncTaskCompleteListener;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.localDatabase.DataDB;
import com.example.android.moviedb3.movieDB.GenreTvData;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.movieDataManager.DBGetter;
import com.example.android.moviedb3.movieDataManager.DatabaseGenreTvGetter;
import com.example.android.moviedb3.movieDataManager.DatabaseTVGetter;
import com.example.android.moviedb3.movieDataManager.GenreTVGetter;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVListFragment extends Fragment
{
    private ArrayList<TVData> tvDataArrayList;
    private DataDB<String> tvListDB;

    String idGenre;
    String urlGenreTV;
    DataDB<GenreTvData> genreTVDataDB;

    RecyclerView tvListRecyclerView;
    ProgressBar loadingDataProgressBar;
    TextView noDataTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_tv_movie_list, container, false);

        tvListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie_list);
        loadingDataProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_data);
        noDataTextView = (TextView) view.findViewById(R.id.txt_no_data);

        GetMovieList();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList(MovieDBKeyEntry.MovieDataPersistance.TV_DATA_LIST_PERSISTANCE_KEY, tvDataArrayList);
    }

    public void setTVListDB(DataDB<String> movieListDB) {
        this.tvListDB = movieListDB;
    }

    public void setGenre(String idGenre, DataDB<GenreTvData> genreTVDataDB, String urlGenreTV)
    {
        this.idGenre = idGenre;
        this.genreTVDataDB = genreTVDataDB;
        this.urlGenreTV = urlGenreTV;
    }

    private void GetMovieList()
    {
        if(tvListDB != null)
        {
            GetMovieListFromDatabase();
            return;
        }

        if(idGenre != null && genreTVDataDB != null)
        {
            GetMovieListFromGenreMovie();
            return;
        }
    }

    public void GetMovieListFromDatabase()
    {
        ShowLoadingData();
        DBGetter.GetData(new DatabaseTVGetter(tvListDB, getContext(), new MainTVListObtainedListener()));
    }

    public void GetMovieListFromGenreMovie()
    {
        ShowLoadingData();
        DBGetter.GetData(new GenreTVGetter(idGenre, getContext(), new AllGenreMovieObtainedListener(), genreTVDataDB, urlGenreTV));
    }

    private void ShowNoDataLayout()
    {
        tvListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    private void ShowRecycleView()
    {
        tvListRecyclerView.setVisibility(View.VISIBLE);
        loadingDataProgressBar.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void ShowLoadingData()
    {
        tvListRecyclerView.setVisibility(View.GONE);
        loadingDataProgressBar.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
    }

    private void SetRecyclerView(ArrayList<TVData> tvDataArrayList)
    {
        if (tvDataArrayList == null || tvDataArrayList.size() <= 0)
        {
            return;
        }

        MainTVListRecyclerViewAdapter mainMovieListRecyclerViewAdapter = new MainTVListRecyclerViewAdapter(tvDataArrayList, getContext(), new MainTVDataChoosedListener());
        tvListRecyclerView.setAdapter(mainMovieListRecyclerViewAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        tvListRecyclerView.setLayoutManager(gridLayoutManager);

        tvListRecyclerView.setHasFixedSize(true);
    }

    private class MainTVDataChoosedListener implements OnDataChooseListener<TVData>
    {
        @Override
        public void OnDataChoose(TVData tvData)
        {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MovieDBKeyEntry.MovieDataPersistance.TV_DATA_PERSISTANCE_KEY, tvData);

            ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(TVDetailActivity.class, bundle, 0, getActivity()));
        }
    }

    private class MainTVListObtainedListener implements OnDataObtainedListener<ArrayList<TVData>>
    {
        @Override
        public void onDataObtained(ArrayList<TVData> tvDatas)
        {
            DataDB<String> tvDB = TVListFragment.this.tvListDB;

            if(tvDatas == null)
            {
                ShowNoDataLayout();
            }

            else
            {
                if(tvDatas.isEmpty())
                {
                    ShowNoDataLayout();
                }

                else
                {
                    tvDataArrayList = tvDatas;

                    SetRecyclerView(tvDataArrayList);
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
            DBGetter.GetData(new DatabaseGenreTvGetter(getContext(), new MainTVListObtainedListener(), genreTVDataDB, idGenre));
        }
    }

}

