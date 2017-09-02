package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.movieDB.GenreData;

/**
 * Created by nugroho on 02/09/17.
 */

public class GenreDataListRecyclerViewHolder extends RecyclerView.ViewHolder
{
    TextView genreNameTextView;
    View itemView;

    OnDataChooseListener<GenreData> onDataChooseListener;

    public GenreDataListRecyclerViewHolder(View itemView, OnDataChooseListener<GenreData> onDataChooseListener)
    {
        super(itemView);

        this.onDataChooseListener = onDataChooseListener;
        genreNameTextView = (TextView) itemView.findViewById(R.id.txt_genre_name);
        this.itemView = itemView;
    }

    public void Bind(final GenreData genreData)
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
