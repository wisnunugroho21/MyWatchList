package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.movieDB.MovieInfoData;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nugroho on 28/07/17.
 */

public class MovieInfoListViewHolder extends RecyclerView.ViewHolder
{
    Context context;

    TextView firstTextView;
    TextView secondTextView;
    CircleImageView circleImageView;

    public MovieInfoListViewHolder(View itemView, Context context) {
        super(itemView);

        firstTextView = (TextView) itemView.findViewById(R.id.txt_first_text);
        secondTextView = (TextView) itemView.findViewById(R.id.txt_second_text);
        circleImageView = (CircleImageView) itemView.findViewById(R.id.iv_profile_image);

        this.context = context;
    }

    public void Binding(MovieInfoData movieInfoData)
    {
        secondTextView.setText(movieInfoData.getSecondText());
        firstTextView.setText(movieInfoData.getFirstText());

        if(!movieInfoData.getImage().isEmpty())
        {
            Picasso.with(context)
                    .load(movieInfoData.getImage())
                    .error(R.drawable.ic_error_outline_black_48px)
                    .placeholder(R.drawable.ic_cached_black_48px)
                    .noFade()
                    .into(circleImageView);
        }
    }
}
