package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.movieDB.VideoData;

import java.util.ArrayList;

/**
 * Created by nugroho on 11/08/17.
 */

public class VideoDataListRecyclerViewAdapter extends RecyclerView.Adapter<VideoDataListRecyclerViewHolder>
{
    ArrayList<VideoData> videoDataArrayList;

    public VideoDataListRecyclerViewAdapter(ArrayList<VideoData> videoDataArrayList) {
        this.videoDataArrayList = videoDataArrayList;
    }

    @Override
    public VideoDataListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_list, parent, false);
        return new VideoDataListRecyclerViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(VideoDataListRecyclerViewHolder holder, int position) {
        holder.Bind(videoDataArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoDataArrayList.size();
    }
}
