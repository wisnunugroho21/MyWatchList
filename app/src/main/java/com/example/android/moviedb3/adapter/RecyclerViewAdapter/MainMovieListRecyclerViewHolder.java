package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.MovieData;
import com.squareup.picasso.Picasso;

/**
 * Created by nugroho on 04/07/17.
 */

public class MainMovieListRecyclerViewHolder extends RecyclerView.ViewHolder
{
    private final Context context;

    private final ImageView moviePosterImageView;
    private final View view;

    private final OnDataChooseListener<MovieData> movieDataOnDataChooseListener;

    public MainMovieListRecyclerViewHolder(View itemView, Context context,
                                           OnDataChooseListener<MovieData> movieDataOnDataChooseListener)
    {
        super(itemView);

        this.view = itemView;
        this.context = context;
        this.movieDataOnDataChooseListener = movieDataOnDataChooseListener;

        moviePosterImageView = (ImageView) view.findViewById(R.id.main_poster_item_iv);
    }

    public void Bind(final MovieData movieData)
    {
        Picasso.with(context).
                load(movieData.getMoviePosterFullURL()).
                placeholder(R.drawable.ic_cached_black_48px).
                error(R.drawable.ic_error_outline_black_48px).
                into(moviePosterImageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                movieDataOnDataChooseListener.OnDataChoose(movieData);
            }
        });
    }
}
