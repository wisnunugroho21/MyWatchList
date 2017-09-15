package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.movieDB.MovieInfoData;
import com.example.android.moviedb3.movieDB.ReviewData;

import java.util.ArrayList;

/**
 * Created by nugroho on 28/07/17.
 */

public class MovieInfoListRecycleViewAdapter<Data extends MovieInfoData> extends RecyclerView.Adapter<MovieInfoListViewHolder>
{
    private ArrayList<Data> movieInfoDataArrayList;
    private Context context;
    private OnDataChooseListener<MovieInfoData> onDataChooseListener;

    public MovieInfoListRecycleViewAdapter(ArrayList<Data> movieInfoDataArrayList, Context context) {
        this.movieInfoDataArrayList = movieInfoDataArrayList;
        this.context = context;
    }

    public MovieInfoListRecycleViewAdapter(ArrayList<Data> movieInfoDataArrayList, Context context, OnDataChooseListener<MovieInfoData> onDataChooseListener) {
        this.movieInfoDataArrayList = movieInfoDataArrayList;
        this.context = context;
        this.onDataChooseListener = onDataChooseListener;
    }

    @Override
    public MovieInfoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if(movieInfoDataArrayList.get(0) instanceof ReviewData)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_list, parent, false);
        }

        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_movie_info_item_list, parent, false);
        }

        if(onDataChooseListener != null)
        {
            return new MovieInfoListViewHolder(view, context, onDataChooseListener);
        }

        else
        {
            return new MovieInfoListViewHolder(view, context);
        }
    }

    @Override
    public void onBindViewHolder(MovieInfoListViewHolder holder, int position) {
        holder.Binding(movieInfoDataArrayList.get(position));
    }

    @Override
    public int getItemCount() {

        int length;

        if(movieInfoDataArrayList.size() > 5)
        {
            length = 5;
        }

        else
        {
            length = movieInfoDataArrayList.size();
        }

        return length;
    }
}
