package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.movieDB.GenreData;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreDataListRecyclerViewHolder extends RecyclerView.ViewHolder
{
    TextView genreNameTextView;

    public GenreDataListRecyclerViewHolder(View itemView) {
        super(itemView);

        genreNameTextView = (TextView) itemView.findViewById(R.id.txt_genre_name);
    }

    public void Bind(GenreData genreData)
    {
        genreNameTextView.setText(genreData.getName());
    }
}
