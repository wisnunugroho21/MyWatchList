package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.TVData;

import java.util.ArrayList;

/**
 * Created by nugroho on 16/09/17.
 */

public class MainLinearTVListRecyclerViewAdapter extends RecyclerView.Adapter<MainLinearTVListRecyclerViewHolder>
{
    ArrayList<TVData> tvDataArrayList;
    Context context;
    OnDataChooseListener<TVData> tvDataOnDataChooseListener;

    public MainLinearTVListRecyclerViewAdapter(ArrayList<TVData> tvDataArrayList, Context context, OnDataChooseListener<TVData> tvDataOnDataChooseListener)
    {
        this.tvDataArrayList = tvDataArrayList;
        this.context = context;
        this.tvDataOnDataChooseListener = tvDataOnDataChooseListener;
    }

    @Override
    public MainLinearTVListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_movie_linear_item_list, parent, false);
        return new MainLinearTVListRecyclerViewHolder(view, context, tvDataOnDataChooseListener);
    }

    @Override
    public void onBindViewHolder(MainLinearTVListRecyclerViewHolder holder, int position)
    {
        holder.Bind(tvDataArrayList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return tvDataArrayList.size();
    }
}
