package com.example.android.moviedb3.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.MovieData;

import java.util.ArrayList;

/**
 * Created by nugroho on 04/07/17.
 */

public class MainMovieListRecyclerViewAdapter extends RecyclerView.Adapter<MainMovieListRecyclerViewHolder>
{
    private ArrayList<MovieData> movieDataArrayList;
    private Context context;
    private OnDataChooseListener<MovieData> movieDataOnDataChooseListener;

    public MainMovieListRecyclerViewAdapter(ArrayList<MovieData> movieDataArrayList, Context context, OnDataChooseListener<MovieData> movieDataOnDataChooseListener)
    {
        this.movieDataArrayList = movieDataArrayList;
        this.context = context;
        this.movieDataOnDataChooseListener = movieDataOnDataChooseListener;
    }

    @Override
    public MainMovieListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_movie_item_list, parent, false);
        return new MainMovieListRecyclerViewHolder(view, context, movieDataOnDataChooseListener);
    }

    @Override
    public void onBindViewHolder(MainMovieListRecyclerViewHolder holder, int position)
    {
        holder.Bind(movieDataArrayList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return movieDataArrayList.size();
    }
}
