package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.TVData;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by nugroho on 16/09/17.
 */

public class MainLinearTVListRecyclerViewHolder extends RecyclerView.ViewHolder
{
    private Context context;
    private OnDataChooseListener<TVData> tvDataOnDataChooseListener;

    private TextView titleTextView;
    private TextView genreTextView;
    private TextView ratingTextView;
    private ImageView tvPosterImageView;
    private View view;

    public MainLinearTVListRecyclerViewHolder(View itemView, Context context, OnDataChooseListener<TVData> tvDataOnDataChooseListener) {
        super(itemView);
        this.context = context;
        this.tvDataOnDataChooseListener = tvDataOnDataChooseListener;

        titleTextView = (TextView) itemView.findViewById(R.id.txt_title);
        genreTextView = (TextView) itemView.findViewById(R.id.txt_genre);
        ratingTextView = (TextView) itemView.findViewById(R.id.txt_rating);
        tvPosterImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
        view = itemView;
    }

    public void Bind(final TVData tvData)
    {
        Picasso.with(context)
                .load(tvData.getSmallMoviePosterURL())
                .error(R.drawable.ic_error_outline_black_48px)
                .placeholder(R.drawable.ic_cached_black_48px)
                .into(tvPosterImageView);

        titleTextView.setText(tvData.getOriginalTitle());
        genreTextView.setText(tvData.getGenre());
        ratingTextView.setText(String.valueOf(tvData.getVoteRating()));

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvDataOnDataChooseListener.OnDataChoose(tvData);
            }
        });
    }
}
