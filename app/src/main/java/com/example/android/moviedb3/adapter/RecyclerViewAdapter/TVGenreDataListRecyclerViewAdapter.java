package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.TVGenre;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVGenreDataListRecyclerViewAdapter extends RecyclerView.Adapter<TVGenreDataListRecyclerViewHolder>
{
    ArrayList<TVGenre> tvGenreArrayList;
    OnDataChooseListener<TVGenre> onDataChooseListener;

    public TVGenreDataListRecyclerViewAdapter(ArrayList<TVGenre> tvGenreArrayList, OnDataChooseListener<TVGenre> onDataChooseListener) {
        this.tvGenreArrayList = tvGenreArrayList;
        this.onDataChooseListener = onDataChooseListener;
    }

    @Override
    public TVGenreDataListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item_list, parent, false);
        return new TVGenreDataListRecyclerViewHolder(view, onDataChooseListener);
    }

    @Override
    public void onBindViewHolder(TVGenreDataListRecyclerViewHolder holder, int position)
    {
        holder.Bind(tvGenreArrayList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return tvGenreArrayList.size();
    }
}
