package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.movieDB.MovieInfoData;

import java.util.ArrayList;

/**
 * Created by nugroho on 28/07/17.
 */

public class MovieInfoListRecycleViewAdapter<Data extends MovieInfoData> extends RecyclerView.Adapter<MovieInfoListViewHolder>
{
    private ArrayList<Data> movieInfoDataArrayList;

    public MovieInfoListRecycleViewAdapter(ArrayList<Data> movieInfoDataArrayList) {
        this.movieInfoDataArrayList = movieInfoDataArrayList;
    }

    @Override
    public MovieInfoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_movie_info_item_list, parent, false);
        return new MovieInfoListViewHolder(view);
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
