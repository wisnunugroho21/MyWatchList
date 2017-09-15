package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.MovieInfoData;
import com.example.android.moviedb3.movieDB.ReviewData;
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
    View itemView;

    OnDataChooseListener<MovieInfoData> onDataChooseListener;

    public MovieInfoListViewHolder(View itemView, Context context) {
        super(itemView);

        firstTextView = (TextView) itemView.findViewById(R.id.txt_first_text);
        secondTextView = (TextView) itemView.findViewById(R.id.txt_second_text);
        circleImageView = (CircleImageView) itemView.findViewById(R.id.iv_profile_image);

        this.itemView = itemView;
        this.context = context;
    }

    public MovieInfoListViewHolder(View itemView, Context context,
                                   OnDataChooseListener<MovieInfoData> onDataChooseListener) {
        super(itemView);
        this.context = context;
        this.onDataChooseListener = onDataChooseListener;

        firstTextView = (TextView) itemView.findViewById(R.id.txt_first_text);
        secondTextView = (TextView) itemView.findViewById(R.id.txt_second_text);
        circleImageView = (CircleImageView) itemView.findViewById(R.id.iv_profile_image);
        this.itemView = itemView;
    }

    public void Binding(final MovieInfoData movieInfoData)
    {
        secondTextView.setText(movieInfoData.getSecondText());
        firstTextView.setText(movieInfoData.getFirstText());

        if(!movieInfoData.getImage().isEmpty() && !(movieInfoData instanceof ReviewData))
        {
            Picasso.with(context)
                    .load(movieInfoData.getImage())
                    .error(R.drawable.ic_error_outline_black_48px)
                    .placeholder(R.drawable.ic_cached_black_48px)
                    .noFade()
                    .into(circleImageView);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(onDataChooseListener != null)
                {
                    onDataChooseListener.OnDataChoose(movieInfoData);
                }
            }
        });


    }
}
