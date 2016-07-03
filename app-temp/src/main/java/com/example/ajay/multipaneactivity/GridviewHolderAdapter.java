package com.example.ajay.multipaneactivity;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ajay on 4/22/2016.
 */
public class GridviewHolderAdapter extends RecyclerView.Adapter<GridViewHolder> {


    public static Context context;


    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipateovershoot_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    public static MainGridViewOnclick onclicklistener;
    ArrayList<MovieDetails> MovieList;
    String PicUrl="http://image.tmdb.org/t/p/w185/";

    public GridviewHolderAdapter(Context activityContext,ArrayList<MovieDetails> arrayList,MainGridViewOnclick mainGridViewOnclick){
        this.context=activityContext;
        this.MovieList=arrayList;
        this.onclicklistener=mainGridViewOnclick;

    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.main_gridview,parent,false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, final int position) {
        animate(holder);
        final MovieDetails movieDetails;
       movieDetails=MovieList.get(position);
       Picasso.with(context).load(PicUrl+movieDetails.posterUrl).into(holder.MoviePoster);



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onclicklistener.Onclick(position,movieDetails);
            }
        });

    }

    @Override
    public int getItemCount() {
        return MovieList.size();
    }
}

class GridViewHolder extends RecyclerView.ViewHolder{
    public LinearLayout singleItem;
    public CardView cardView;
    public ImageView MoviePoster;
    public TextView MovieTitle;

    public GridViewHolder(View itemView) {
        super(itemView);
        singleItem=(LinearLayout)itemView.findViewById(R.id.main_gridview_item);
        cardView=(CardView)itemView.findViewById(R.id.main_cardview);
       MoviePoster=(ImageView)itemView.findViewById(R.id.Movie_poster);
        MovieTitle=(TextView)itemView.findViewById(R.id.Movie_title);


    }
}
