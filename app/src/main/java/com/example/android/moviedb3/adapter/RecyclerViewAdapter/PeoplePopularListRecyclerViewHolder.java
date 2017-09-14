package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.MovieInfoData;
import com.example.android.moviedb3.movieDB.PeopleData;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nugroho on 13/09/17.
 */

public class PeoplePopularListRecyclerViewHolder extends RecyclerView.ViewHolder
{
    Context context;
    View itemView;

    TextView firstTextView;
    TextView secondTextView;
    CircleImageView circleImageView;

    OnDataChooseListener<PeopleData> peopleDataOnDataChooseListener;

    public PeoplePopularListRecyclerViewHolder(View itemView, Context context, OnDataChooseListener<PeopleData> peopleDataOnDataChooseListener) {
        super(itemView);

        firstTextView = (TextView) itemView.findViewById(R.id.txt_first_text);
        secondTextView = (TextView) itemView.findViewById(R.id.txt_second_text);
        circleImageView = (CircleImageView) itemView.findViewById(R.id.iv_profile_image);
        this.itemView = itemView;

        this.context = context;
        this.peopleDataOnDataChooseListener = peopleDataOnDataChooseListener;
    }

    public void Binding(final PeopleData peopleData)
    {
        secondTextView.setText(peopleData.getKnownRoles());
        firstTextView.setText(peopleData.getName());

        if(!peopleData.getProfileImage().isEmpty())
        {
            Picasso.with(context)
                    .load(peopleData.getSmallProfileImage())
                    .error(R.drawable.ic_error_outline_black_48px)
                    .placeholder(R.drawable.ic_cached_black_48px)
                    .noFade()
                    .into(circleImageView);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleDataOnDataChooseListener.OnDataChoose(peopleData);
            }
        });
    }
}
