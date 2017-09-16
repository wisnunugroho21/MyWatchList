package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.MovieData;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by nugroho on 16/09/17.
 */

public class MainLinearMovieListRecyclerViewHolder extends RecyclerView.ViewHolder
{
    private Context context;
    private OnDataChooseListener<MovieData> movieDataOnDataChooseListener;

    private ImageView moviePosterImageView;
    private TextView titleTextView;
    private TextView genreTextView;
    private TextView ratingTextView;

    private View view;

    public MainLinearMovieListRecyclerViewHolder(View itemView, Context context, OnDataChooseListener<MovieData> movieDataOnDataChooseListener)
    {
        super(itemView);

        this.context = context;
        this.movieDataOnDataChooseListener = movieDataOnDataChooseListener;

        moviePosterImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
        titleTextView = (TextView) itemView.findViewById(R.id.txt_title);
        genreTextView = (TextView) itemView.findViewById(R.id.txt_genre);
        ratingTextView = (TextView) itemView.findViewById(R.id.txt_rating);
        view = itemView;
    }

    public void Bind(final MovieData movieData)
    {
        Picasso.with(context)
                .load(movieData.getSmallMoviePosterURL())
                .placeholder(R.drawable.ic_cached_black_48px)
                .error(R.drawable.ic_error_outline_black_48px)
                .into(moviePosterImageView);

        titleTextView.setText(movieData.getOriginalTitle());
        genreTextView.setText(movieData.getGenre());
        ratingTextView.setText(String.valueOf(movieData.getVoteRating()));

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                movieDataOnDataChooseListener.OnDataChoose(movieData);
            }
        });
    }
}
