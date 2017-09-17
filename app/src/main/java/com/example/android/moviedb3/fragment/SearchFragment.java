package com.example.android.moviedb3.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activity.MovieDetailActivity;
import com.example.android.moviedb3.activity.TVDetailActivity;
import com.example.android.moviedb3.activityShifter.ActivityLauncher;
import com.example.android.moviedb3.activityShifter.DefaultIActivityLauncher;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MainLinearMovieListRecyclerViewAdapter;
import com.example.android.moviedb3.adapter.RecyclerViewAdapter.MainLinearTVListRecyclerViewAdapter;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.jsonParsing.MovieDetailJSONParser;
import com.example.android.moviedb3.jsonParsing.MovieSearchListJSONParser;
import com.example.android.moviedb3.jsonParsing.TVSearchListJSONParser;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.movieDB.MovieData;
import com.example.android.moviedb3.movieDB.MovieDataURL;
import com.example.android.moviedb3.movieDB.TVData;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetter;
import com.example.android.moviedb3.supportDataManager.dataGetter.NetworkDataGetterDefaultThread;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by nugroho on 17/09/17.
 */

public class SearchFragment extends Fragment
{
    ImageView backImageView;
    ImageView clearImageView;
    EditText searchTextView;
    ScrollView searchResultScrollView;
    ProgressBar progressBar;
    RecyclerView movieSearchResultRecyclerView;
    RecyclerView tvSearchResultRecyclerView;
    TextView noTVTextView;
    TextView noMovieTextView;

    private int numberMovieInfoObtained;
    private NetworkMovieSearchAsyncTask networkMovieSearchAsyncTask;
    private NetworkTVSearchAsyncTask networkTVSearchAsyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.search_tv_movie, container, false);

        backImageView = (ImageView) view.findViewById(R.id.iv_back);
        clearImageView = (ImageView) view.findViewById(R.id.iv_clear_voice);
        searchTextView = (EditText) view.findViewById(R.id.txt_search);
        searchResultScrollView = (ScrollView) view.findViewById(R.id.search_result_scroll_view);
        progressBar = (ProgressBar) view.findViewById(R.id.search_progress_bar);
        movieSearchResultRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie_search_result);
        tvSearchResultRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tv_search_result);
        noMovieTextView = (TextView) view.findViewById(R.id.txt_no_movie);
        noTVTextView = (TextView) view.findViewById(R.id.txt_no_tv);

        setSearch();
        progressBar.setVisibility(View.GONE);
        searchResultScrollView.setVisibility(View.GONE);

        return view;
    }

    private void showLoading()
    {
        progressBar.setVisibility(View.VISIBLE);
        searchResultScrollView.setVisibility(View.GONE);
    }

    private void showSearchResult()
    {
        progressBar.setVisibility(View.GONE);
        searchResultScrollView.setVisibility(View.VISIBLE);
    }

    public void NoMovie()
    {
        movieSearchResultRecyclerView.setVisibility(View.GONE);
        noMovieTextView.setVisibility(View.VISIBLE);
    }

    public void NoTV()
    {
        tvSearchResultRecyclerView.setVisibility(View.GONE);
        noTVTextView.setVisibility(View.VISIBLE);
    }

    public void ShowMovieList()
    {
        movieSearchResultRecyclerView.setVisibility(View.VISIBLE);
        noMovieTextView.setVisibility(View.GONE);
    }

    public void ShowTVList()
    {
        tvSearchResultRecyclerView.setVisibility(View.VISIBLE);
        noTVTextView.setVisibility(View.GONE);
    }

    private void CheckAndShowMovieDetail()
    {
        if (numberMovieInfoObtained < 1)
        {
            numberMovieInfoObtained++;
        }

        else
        {
            showSearchResult();
            numberMovieInfoObtained = 0;
        }
    }

    private void setSearch()
    {
        searchTextView.addTextChangedListener(new TextWatcher()
        {
            private boolean isEmpty;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.length() == 0)
                {
                    isEmpty = true;
                    clearImageView.animate().alpha(0f).setDuration(100).setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            clearImageView.setVisibility(View.GONE);
                        }
                    });

                    if(networkMovieSearchAsyncTask != null)
                    {
                        networkMovieSearchAsyncTask.cancel(true);
                        networkMovieSearchAsyncTask = null;
                    }

                    if(networkTVSearchAsyncTask != null)
                    {
                        networkTVSearchAsyncTask.cancel(true);
                        networkTVSearchAsyncTask = null;
                    }

                    progressBar.setVisibility(View.GONE);
                    searchResultScrollView.setVisibility(View.GONE);
                }

                else
                {
                    if(isEmpty)
                    {
                        isEmpty = false;

                        clearImageView.setVisibility(View.VISIBLE);
                        clearImageView.setAlpha(0f);

                        clearImageView.animate().alpha(1f).setDuration(100).setListener(null);
                    }

                    if(networkMovieSearchAsyncTask == null)
                    {
                        String x = s.toString();
                        showLoading();

                        networkMovieSearchAsyncTask = new NetworkMovieSearchAsyncTask(x, getContext(), new OnMovieSearchObtainedListener());
                        networkMovieSearchAsyncTask.execute();
                    }

                    else
                    {
                        networkMovieSearchAsyncTask.cancel(true);
                        networkMovieSearchAsyncTask = null;

                        String x = s.toString();
                        showLoading();

                        networkMovieSearchAsyncTask = new NetworkMovieSearchAsyncTask(x, getContext(), new OnMovieSearchObtainedListener());
                        networkMovieSearchAsyncTask.execute();
                    }

                    if(networkTVSearchAsyncTask == null)
                    {
                        String x = s.toString();
                        showLoading();

                        networkTVSearchAsyncTask = new NetworkTVSearchAsyncTask(x, getContext(), new OnTVSearchObtainedListener());
                        networkTVSearchAsyncTask.execute();
                    }

                    else
                    {
                        networkTVSearchAsyncTask.cancel(true);
                        networkTVSearchAsyncTask = null;

                        String x = s.toString();
                        showLoading();

                        networkTVSearchAsyncTask = new NetworkTVSearchAsyncTask(x, getContext(), new OnTVSearchObtainedListener());
                        networkTVSearchAsyncTask.execute();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        backImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NavUtils.navigateUpFromSameTask(getActivity());
            }
        });
        clearImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                searchTextView.setText("");
                searchTextView.clearFocus();
                clearImageView.animate().alpha(0f).setDuration(100).setListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        clearImageView.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private class NetworkMovieSearchAsyncTask extends AsyncTask<Void, Void, ArrayList<MovieData>>
    {
        String query;
        Context context;
        OnDataObtainedListener<ArrayList<MovieData>> onDataObtainedListener;

        public NetworkMovieSearchAsyncTask(String query, Context context, OnDataObtainedListener<ArrayList<MovieData>> onDataObtainedListener) {
            this.query = query;
            this.context = context;
            this.onDataObtainedListener = onDataObtainedListener;
        }

        @Override
        protected ArrayList<MovieData> doInBackground(Void... params)
        {
            String movieSearchURL = MovieDataURL.GetMovieSearchResult(query, context);
            return NetworkDataGetter.GetDataDefaultThread(new NetworkDataGetterDefaultThread<ArrayList<MovieData>>(new MovieSearchListJSONParser()), movieSearchURL);
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieDatas)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(movieDatas);
            }
        }
    }

    private class NetworkTVSearchAsyncTask extends AsyncTask<Void, Void, ArrayList<TVData>>
    {
        String query;
        Context context;
        OnDataObtainedListener<ArrayList<TVData>> onDataObtainedListener;

        public NetworkTVSearchAsyncTask(String query, Context context, OnDataObtainedListener<ArrayList<TVData>> onDataObtainedListener) {
            this.query = query;
            this.context = context;
            this.onDataObtainedListener = onDataObtainedListener;
        }

        @Override
        protected ArrayList<TVData> doInBackground(Void... params)
        {
            String tvSearchURL = MovieDataURL.GetTVSearchResult(query, context);
            return NetworkDataGetter.GetDataDefaultThread(new NetworkDataGetterDefaultThread<ArrayList<TVData>>(new TVSearchListJSONParser()), tvSearchURL);
        }

        @Override
        protected void onPostExecute(ArrayList<TVData> tvDatas)
        {
            if(onDataObtainedListener != null)
            {
                onDataObtainedListener.onDataObtained(tvDatas);
            }
        }
    }

    private class OnMovieSearchObtainedListener implements OnDataObtainedListener<ArrayList<MovieData>>
    {
        @Override
        public void onDataObtained(ArrayList<MovieData> movieDatas)
        {
            if(movieDatas != null)
            {
                if(!movieDatas.isEmpty())
                {
                    movieSearchResultRecyclerView.setAdapter(new MainLinearMovieListRecyclerViewAdapter(movieDatas, getContext(), new MainMovieDataChoosedListener()));
                    movieSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    movieSearchResultRecyclerView.setHasFixedSize(true);
                    movieSearchResultRecyclerView.setNestedScrollingEnabled(false);

                    ShowMovieList();
                    CheckAndShowMovieDetail();
                    return;
                }
            }

            NoMovie();
            CheckAndShowMovieDetail();
        }
    }

    private class OnTVSearchObtainedListener implements OnDataObtainedListener<ArrayList<TVData>>
    {
        @Override
        public void onDataObtained(ArrayList<TVData> tvDatas)
        {
            if(tvDatas != null)
            {
                if(!tvDatas.isEmpty())
                {
                    tvSearchResultRecyclerView.setAdapter(new MainLinearTVListRecyclerViewAdapter(tvDatas, getContext(), new MainTVDataChoosedListener()));
                    tvSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    tvSearchResultRecyclerView.setHasFixedSize(true);
                    tvSearchResultRecyclerView.setNestedScrollingEnabled(false);

                    ShowTVList();
                    CheckAndShowMovieDetail();
                    return;
                }
            }

            NoTV();
            CheckAndShowMovieDetail();
        }
    }

    private class MainMovieDataChoosedListener implements OnDataChooseListener<MovieData>
    {
        @Override
        public void OnDataChoose(MovieData movieData)
        {
            Bundle bundle = new Bundle();
            bundle.putString(MovieDBKeyEntry.MovieDataPersistance.MOVIE_ID_PERSISTANCE_KEY, movieData.getId());

            ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(MovieDetailActivity.class, bundle, 0, getActivity()));
        }
    }

    private class MainTVDataChoosedListener implements OnDataChooseListener<TVData>
    {
        @Override
        public void OnDataChoose(TVData tvData)
        {
            Bundle bundle = new Bundle();
            bundle.putString(MovieDBKeyEntry.MovieDataPersistance.TV_ID_PERSISTANCE_KEY, tvData.getId());

            ActivityLauncher.LaunchActivity(new DefaultIActivityLauncher(TVDetailActivity.class, bundle, 0, getActivity()));
        }
    }
}
