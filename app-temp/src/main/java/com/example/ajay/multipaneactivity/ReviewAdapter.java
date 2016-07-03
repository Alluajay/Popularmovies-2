package com.example.ajay.multipaneactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by allu on 5/20/16.
 */
public class ReviewAdapter extends RecyclerView.Adapter<list> {

    Context Activity_context;
    ArrayList<Reviews> ReviewList;
    TrailerListInterface Onclick_interface;
    public ReviewAdapter(ArrayList<Reviews> reviewList,Context context){
        this.ReviewList=reviewList;
        this.Activity_context=context;
    }

    @Override
    public list onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(Activity_context).inflate(R.layout.reviewlayout,parent,false);
        list ls=new list(v);
        return ls;
    }

    @Override
    public void onBindViewHolder(list holder, final int position) {
        holder.Authorname.setText(ReviewList.get(position).getAuthor());
        holder.Content.setText(ReviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return ReviewList.size();
    }
}


class list extends RecyclerView.ViewHolder {
    public TextView Authorname,Content;
    public list(View itemView) {
        super(itemView);
        Authorname=(TextView)itemView.findViewById(R.id.authorname);
        Content=(TextView)itemView.findViewById(R.id.content);
    }
}
