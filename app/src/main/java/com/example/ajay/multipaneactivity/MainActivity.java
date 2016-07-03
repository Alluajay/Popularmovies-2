package com.example.ajay.multipaneactivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity  implements
        Communicator {


    private Boolean FinalBack;
    private Communicator communicator;
    ArrayList<MovieDetails> movieArray;
    RequestQueue queue;
    RecyclerView MovieList;
    public static String Tag="Mainactivity_background";
    public String MainUrl;
    ProgressDialog progressBar;
    ArrayList<String> TrailarArray=new ArrayList<>();
    FavouriteSqlliteDb favouriteSqlliteDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Popular Movies 1");

        FinalBack=true;

        favouriteSqlliteDb=new FavouriteSqlliteDb(this);

        progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Content downloading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        setSupportActionBar(toolbar);
        MovieList=(RecyclerView)findViewById(R.id.Gridview);
        queue= Volley.newRequestQueue(this);
        MovieList.setHasFixedSize(false);
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            MovieList.setLayoutManager(new GridLayoutManager(this,3));

        }else if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
            MovieList.setLayoutManager(new GridLayoutManager(this,2));

        }
        RecyclerView.ItemAnimator itemAnimator=new DefaultItemAnimator();
        itemAnimator.setAddDuration(700);
        itemAnimator.setRemoveDuration(700);
        MovieList.setItemAnimator(itemAnimator);
        movieArray=new ArrayList<>();
        setListurl(MainUrl,queue);



    }



    @Override
    public void Message(MovieDetails movieDetails) {
        DetailFragment detailfragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.detail_Fragment);
        if (detailfragment != null && detailfragment.isInLayout()) {
            detailfragment.setText(movieDetails);
        } else {
            Intent intent = new Intent(getApplicationContext(),
                    DetailActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable("com.package.MovieDetails",movieDetails);
            intent.putExtras(extras);
            startActivity(intent);

        }

    }

    @Override
    public void onBackPressed() {
        if(FinalBack){
            super.onBackPressed();
        }else {
      //      Log.d(Tag, "backPressed");
            setListurl(MainUrl + "&sort_by=vote_average.asc", queue);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ListFragment listFragment=new ListFragment();
        //noinspection SimplifiableIfStatement
        if (id == R.id.Popularity) {
            setListurl(MainUrl,queue);
            return true;
        }else if(id==R.id.Rating){
            setListurl(MainUrl+"&sort_by=vote_average.asc",queue);
            return true;
        }else if(id==R.id.Release_date){
            setListurl(MainUrl+"&sort_by=release_date.asc",queue);
            return true;
        }else if(id==R.id.Favourite){

            setListFromDb();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setListurl(String url,RequestQueue queue1){
        queue= queue1;
        ShowDialog();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                JSONArray MovieList= null;
                try {
                    MovieList = jsonObject.getJSONArray("results");
                    for(int i=0;i<MovieList.length();i++){
                        JSONObject Movies=MovieList.getJSONObject(i);
                        String id=Movies.getString("id");
                        String overview=Movies.getString("overview");
                        String original_title=Movies.getString("original_title");
                        String poster_path=Movies.getString("poster_path");
                        String original_language=Movies.getString("original_language");
                        String Year= Movies.getString("release_date");
                        String Rating=Movies.getString("vote_average");
                        MovieDetails movieDetails=new MovieDetails(id,original_title,overview,poster_path,original_language,Rating,Year);
                        movieArray.add(movieDetails);
           //             Log.d(Tag,"List added");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
           //         Log.e(Tag,""+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

             //   Log.e(Tag,""+volleyError);
                CloseDialog();
            }
        });
        MainGridViewOnclick mainGridViewOnclick=new MainGridViewOnclick() {
            @Override
            public void Onclick(int pos, MovieDetails movieDetails) {
                updateFragment(movieDetails);
            }
        };
        MovieList.setAdapter(new GridviewHolderAdapter(this,movieArray,mainGridViewOnclick));
       // Log.d(Tag,"List added");
        CloseDialog();
        queue.add(jsonObjectRequest);
    }

    public void setListFromDb() {

        ArrayList<MovieDetails> MoviesFromFav = favouriteSqlliteDb.getFavourite();
        if (MoviesFromFav != null) {
            FinalBack=false;
            MainGridViewOnclick mainGridViewOnclick = new MainGridViewOnclick() {
                @Override
                public void Onclick(int pos, MovieDetails movieDetails) {
                    updateFragment(movieDetails);

                }
            };
            MovieList.setAdapter(new GridviewHolderAdapter(this, MoviesFromFav, mainGridViewOnclick));
        }else {
            Toast.makeText(this,"No Favourite addeed",Toast.LENGTH_SHORT).show();
            setListurl(MainUrl+"",queue);

        }
    }


    public void ShowDialog()
    {
        if(!progressBar.isShowing()){
            progressBar.show();
         //   Log.d(Tag,"progressbar on");
        }
    }

    public void CloseDialog(){
        if(progressBar.isShowing()){
            progressBar.hide();
         //   Log.d(Tag,"progressbar off");
        }
    }


    private void updateFragment(MovieDetails movieDetails) {
        DetailFragment detailfragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.detail_Fragment);
        if (detailfragment != null && detailfragment.isInLayout()) {
            detailfragment.setText(movieDetails);
        } else {
            Intent intent = new Intent(getApplicationContext(),
                    DetailActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable("com.package.MovieDetails",movieDetails);
            intent.putExtras(extras);
            startActivity(intent);

        }
    }
}
