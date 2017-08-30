package com.example.android.moviedb3.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.movieDB.MovieInfoData;
import com.example.android.moviedb3.movieDB.ReviewData;

import java.util.ArrayList;

/**
 * Created by nugroho on 28/07/17.
 */

public class MovieInfoListViewHolder extends RecyclerView.ViewHolder
{
    TextView firstTextView;
    TextView secondTextView;

    public MovieInfoListViewHolder(View itemView) {
        super(itemView);

        firstTextView = (TextView) itemView.findViewById(R.id.txt_first_text);
        secondTextView = (TextView) itemView.findViewById(R.id.txt_second_text);
    }

    public void Binding(MovieInfoData movieInfoData)
    {
        secondTextView.setText(movieInfoData.getSecondText());
        firstTextView.setText(movieInfoData.getFirstText());
    }
}
