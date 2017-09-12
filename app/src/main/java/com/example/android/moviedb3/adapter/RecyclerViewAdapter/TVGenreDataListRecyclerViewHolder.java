package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.GenreData;
import com.example.android.moviedb3.movieDB.TVGenre;

/**
 * Created by nugroho on 12/09/17.
 */

public class TVGenreDataListRecyclerViewHolder extends RecyclerView.ViewHolder
{
    TextView genreNameTextView;
    View itemView;

    OnDataChooseListener<TVGenre> onDataChooseListener;

    public TVGenreDataListRecyclerViewHolder(View itemView, OnDataChooseListener<TVGenre> onDataChooseListener)
    {
        super(itemView);

        this.onDataChooseListener = onDataChooseListener;
        genreNameTextView = (TextView) itemView.findViewById(R.id.txt_genre_name);
        this.itemView = itemView;
    }

    public void Bind(final TVGenre genreData)
    {
        genreNameTextView.setText(genreData.getName());
        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(onDataChooseListener != null)
                {
                    onDataChooseListener.OnDataChoose(genreData);
                }
            }
        });
    }
}