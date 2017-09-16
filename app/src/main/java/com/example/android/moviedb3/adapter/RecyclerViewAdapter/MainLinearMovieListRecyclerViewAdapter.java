package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.MovieData;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by nugroho on 16/09/17.
 */

public class MainLinearMovieListRecyclerViewAdapter extends RecyclerView.Adapter<MainLinearMovieListRecyclerViewHolder>
{
    ArrayList<MovieData> movieDataArrayList;
    Context context;
    OnDataChooseListener<MovieData> movieDataOnDataChooseListener;

    public MainLinearMovieListRecyclerViewAdapter(ArrayList<MovieData> movieDataArrayList, Context context, OnDataChooseListener<MovieData> movieDataOnDataChooseListener)
    {
        this.movieDataArrayList = movieDataArrayList;
        this.context = context;
        this.movieDataOnDataChooseListener = movieDataOnDataChooseListener;
    }

    @Override
    public MainLinearMovieListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_movie_linear_item_list, parent, false);
        return new MainLinearMovieListRecyclerViewHolder(view, context, movieDataOnDataChooseListener);
    }

    @Override
    public void onBindViewHolder(MainLinearMovieListRecyclerViewHolder holder, int position)
    {
        holder.Bind(movieDataArrayList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return movieDataArrayList.size();
    }
}
