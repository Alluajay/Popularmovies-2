package com.example.ajay.multipaneactivity;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ajay on 5/10/2016.
 */
public class DetailFragment extends Fragment {
    TextView Title,Overvew,MovieRating,MovieDuration,MovieYear;
    ImageView Poster;
    ImageButton Favorite;
   // NestedScrollView detailed_fragment;
    FavouriteSqlliteDb favouriteSqlliteDb;
    String PicUrl="http://image.tmdb.org/t/p/w185/";
    RequestQueue requestQueue;
    Context context;
    String Tag="DetailedFragment";
   // RecyclerView TrailerListView;
   // TrailerListInterface trailerListInterface;
    Button Trailer,Reviews;
    Intent i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        context=view.getContext();
      //  detailed_fragment=(NestedScrollView) view.findViewById(R.id.detailed_fragment_layout);
        favouriteSqlliteDb=new FavouriteSqlliteDb(this.getActivity().getApplicationContext());
        //detailed_fragment.setHorizontalScrollBarEnabled(true);
        Title=(TextView)view.findViewById(R.id.detailed_title);
        Overvew=(TextView)view.findViewById(R.id.detailed_overview);
        MovieRating=(TextView)view.findViewById(R.id.detailed_rating);
        MovieYear=(TextView)view.findViewById(R.id.detailed_Year);
        Poster=(ImageView)view.findViewById(R.id.detailed_poster);
        Favorite=(ImageButton)view.findViewById(R.id.Fav_but);
        Favorite.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.staroff));
        requestQueue= Volley.newRequestQueue(context);
            Trailer=(Button)view.findViewById(R.id.Trailer);
            Reviews=(Button)view.findViewById(R.id.Review);

        return view;
    }


    public void watchYoutubeVideo(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

    public void setText(final MovieDetails movieDetails) {
        SetLayout(movieDetails);
    }


    /** test
     * for
     * getting trailer in background
     *
     */

    public void getTrailer(final MovieDetails movieDetails){
        String TrailerUrl="http://api.themoviedb.org/3/movie/"+movieDetails.getMovieId();
        final ArrayList<String> TrailarList=new ArrayList<>();
        Boolean set;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, TrailerUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                JSONArray TrailerList1= null;
                try {
                    TrailerList1 = jsonObject.getJSONArray("results");
                    for(int i=0;i<TrailerList1.length();i++){
                        JSONObject Trailer=TrailerList1.getJSONObject(i);
                        String Key=Trailer.getString("key");
                       // Log.d(Tag,"responce from DetailFragment"+Key);
                        TrailarList.add(Key);
                        if(i==TrailerList1.length()-1){
                       //     Log.d(Tag,"updated");
                             UpdateContentTrailer(TrailarList,movieDetails);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void getReview(final MovieDetails movieDetails){
       // Log.d(Tag,"inReview");
        String TrailerUrl="http://api.themoviedb.org/3/movie/"+movieDetails.getMovieId();
        final ArrayList<Reviews> ReviewList=new ArrayList<Reviews>();
        Boolean set;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, TrailerUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                JSONArray ReviewList1= null;
                try {
        //            Log.d(Tag,"started"+movieDetails.getMovieId());
                    ReviewList1 = jsonObject.getJSONArray("results");
                    for(int i=0;i<ReviewList1.length();i++){
                        Reviews reviews;
                        JSONObject ReviewJson=ReviewList1.getJSONObject(i);
                        String Author=ReviewJson.getString("author");
                        String Content=ReviewJson.getString("content");
                        reviews=new Reviews(Author,Content);
          //              Log.d(Author,Content);
                        ReviewList.add(reviews);
                        if(i==ReviewList1.length()-1){
            //                Log.d(Tag,"updated");
                            UpdateContentReviews(ReviewList,movieDetails);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
              //      Log.d(Tag,"error1"+e+"");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
              volleyError.printStackTrace();
          //      Log.d(Tag,"error");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void UpdateContentTrailer(ArrayList<String> TrailerList,MovieDetails movieDetails){
        i=new Intent(context,ContentList.class);
       // final Bundle extras = new Bundle();
        //extras.putParcelable("com.package.MovieDetails",movieDetails);
        //i.putExtra("extras",extras);
        i.putExtra("Mode",2);
        i.putExtra("TrailerList",TrailerList);
        i.putExtra("Movie_Titile",movieDetails.gettitle());
        startActivity(i);

    }

    public void UpdateContentReviews(ArrayList<Reviews> ReviewList,MovieDetails movieDetails){
        i=new Intent(context,ContentList.class);
        // final Bundle extras = new Bundle();
        //extras.putParcelable("com.package.MovieDetails",movieDetails);
        //i.putExtra("extras",extras);
        i.putExtra("Mode",1);
        i.putParcelableArrayListExtra("ReviewList",ReviewList);
        i.putExtra("Movie_Titile",movieDetails.gettitle());
        startActivity(i);

    }

    public void SetLayout(final MovieDetails movieDetails){
        Title.setText(movieDetails.gettitle());
        Overvew.setText(movieDetails.getOverview());
        MovieYear.setText(movieDetails.ReleaseDate);
        MovieRating.setText(movieDetails.rating);
        PicUrl=PicUrl+movieDetails.posterUrl;
        Picasso.with(getActivity()).load(PicUrl).into(Poster);
        if(favouriteSqlliteDb.CheckifIDAvailable(movieDetails.getMovieId())){
          //  Log.d(Tag,"staron");
            Favorite.setBackgroundDrawable(ContextCompat.getDrawable(getActivity().getApplication().getApplicationContext(),R.drawable.staron));
        }
        Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!favouriteSqlliteDb.CheckifIDAvailable(movieDetails.getMovieId())){
                    favouriteSqlliteDb.addFavourite(movieDetails);
                    Favorite.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.staron));
                }else{
                    favouriteSqlliteDb.deleteFav(movieDetails.getMovieId());
            //        Log.d(Tag,"staroff");
                    Favorite.setBackgroundResource(R.drawable.staroff);
                    // Favorite.setBackground(ContextCompat.getDrawable(context,R.drawable.staroff));
                }
            }
        });
        Trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTrailer(movieDetails);
            }
        });
        Reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReview(movieDetails);
            }
        });

    }

}
