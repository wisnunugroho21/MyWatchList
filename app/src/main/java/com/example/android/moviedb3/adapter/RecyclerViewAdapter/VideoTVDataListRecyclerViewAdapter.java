package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.movieDB.VideoTVData;

import java.util.ArrayList;

/**
 * Created by nugroho on 12/09/17.
 */

public class VideoTVDataListRecyclerViewAdapter extends RecyclerView.Adapter<VideoTVDataListRecyclerViewHolder>
{
    ArrayList<VideoTVData> videoDataArrayList;

    public VideoTVDataListRecyclerViewAdapter(ArrayList<VideoTVData> videoDataArrayList) {
        this.videoDataArrayList = videoDataArrayList;
    }

    @Override
    public VideoTVDataListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_list, parent, false);
        return new VideoTVDataListRecyclerViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(VideoTVDataListRecyclerViewHolder holder, int position) {
        holder.Bind(videoDataArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoDataArrayList.size();
    }
}
