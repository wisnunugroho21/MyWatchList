package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.movieDB.GenreData;

import java.util.ArrayList;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreDataListRecyclerViewAdapter extends RecyclerView.Adapter<GenreDataListRecyclerViewHolder>
{
    ArrayList<GenreData> genreDataArrayList;
    OnDataChooseListener<GenreData> onDataChooseListener;

    public GenreDataListRecyclerViewAdapter(ArrayList<GenreData> genreDataArrayList, OnDataChooseListener<GenreData> onDataChooseListener) {
        this.genreDataArrayList = genreDataArrayList;
        this.onDataChooseListener = onDataChooseListener;
    }

    @Override
    public GenreDataListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item_list, parent, false);
        return new GenreDataListRecyclerViewHolder(view, onDataChooseListener);
    }

    @Override
    public void onBindViewHolder(GenreDataListRecyclerViewHolder holder, int position)
    {
        holder.Bind(genreDataArrayList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return genreDataArrayList.size();
    }
}
