package com.example.ajay.multipaneactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by allu on 6/4/16.
 */
public class NormalListadapter extends RecyclerView.Adapter<ListviewHolder> {
    Context ActivityContext;
    String Tag="ListAdapter";
    ArrayList<String> TrailerList;
    TrailerListInterface trailerListInterface;

    public NormalListadapter(ArrayList<String> List, TrailerListInterface Interface, Context context){
        this.TrailerList=List;
        this.trailerListInterface=Interface;
        this.ActivityContext=context;
    }

    @Override
    public ListviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ActivityContext).inflate(R.layout.normallist,parent,false);
        return new ListviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListviewHolder holder, final int position) {
        int Trailerno=position+1 ;
        holder.videoList.setText("Trailer "+Trailerno);
        holder.normalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trailerListInterface.Onclick(position,TrailerList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return TrailerList.size();
    }
}

class ListviewHolder extends RecyclerView.ViewHolder{

    LinearLayout normalList;
    TextView videoList;
    public ListviewHolder(View itemView) {
        super(itemView);
        normalList=(LinearLayout)itemView.findViewById(R.id.normalListItem);
        videoList=(TextView)itemView.findViewById(R.id.YoutubeList);
    }
}