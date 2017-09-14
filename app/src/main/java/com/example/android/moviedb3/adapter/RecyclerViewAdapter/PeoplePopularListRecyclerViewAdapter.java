package com.example.android.moviedb3.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;
import com.example.android.moviedb3.movieDB.PeopleData;

import java.util.ArrayList;

/**
 * Created by nugroho on 13/09/17.
 */

public class PeoplePopularListRecyclerViewAdapter extends RecyclerView.Adapter<PeoplePopularListRecyclerViewHolder>
{
    private ArrayList<PeopleData> peopleDataArrayList;
    private Context context;
    private OnDataChooseListener<PeopleData> peopleDataOnDataChooseListener;

    public PeoplePopularListRecyclerViewAdapter(ArrayList<PeopleData> peopleDataArrayList, Context context,
                                                OnDataChooseListener<PeopleData> peopleDataOnDataChooseListener) {
        this.peopleDataArrayList = peopleDataArrayList;
        this.context = context;
        this.peopleDataOnDataChooseListener = peopleDataOnDataChooseListener;
    }

    @Override
    public PeoplePopularListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_item_list, parent, false);
        return new PeoplePopularListRecyclerViewHolder(view, context, peopleDataOnDataChooseListener);
    }

    @Override
    public void onBindViewHolder(PeoplePopularListRecyclerViewHolder holder, int position) {
        holder.Binding(peopleDataArrayList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return peopleDataArrayList.size();
    }
}
