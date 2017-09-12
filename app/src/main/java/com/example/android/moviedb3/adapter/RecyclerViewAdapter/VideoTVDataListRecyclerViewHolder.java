package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activityShifter.ActivityLauncher;
import com.example.android.moviedb3.activityShifter.ExternalWebLauncherI;
import com.example.android.moviedb3.movieDB.VideoData;
import com.example.android.moviedb3.movieDB.VideoTVData;
import com.squareup.picasso.Picasso;

/**
 * Created by nugroho on 12/09/17.
 */

public class VideoTVDataListRecyclerViewHolder extends RecyclerView.ViewHolder
{
    private View itemView;
    private ImageView thumbnailVideoImageView;
    private Context context;

    public VideoTVDataListRecyclerViewHolder(View itemView, Context context) {
        super(itemView);

        this.itemView = itemView;
        this.context = context;
        thumbnailVideoImageView = (ImageView) itemView.findViewById(R.id.iv_thumbnail_video);
    }

    public void Bind(final VideoTVData videoData)
    {
        Picasso.with(context).
                load(videoData.getVideoThumbnailURL()).
                placeholder(R.drawable.ic_cached_black_48px).
                error(R.drawable.ic_error_outline_black_48px).
                into(thumbnailVideoImageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityLauncher.LaunchActivity(new ExternalWebLauncherI(videoData.getVideoURL(), context));
            }
        });
    }
}
