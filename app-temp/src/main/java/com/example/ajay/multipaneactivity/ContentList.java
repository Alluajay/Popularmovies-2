package com.example.ajay.multipaneactivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ListAdapter;

import java.util.ArrayList;

public class ContentList extends AppCompatActivity {

    RecyclerView ListView;
    TrailerListInterface trailerListInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_landscape);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent i = getIntent();
        int Mode = i.getIntExtra("Mode",0);
    //    Bundle extras = getIntent().getExtras();
        String Title = i.getStringExtra("Movie_Titile");
        toolbar.setTitle(Title);
        setSupportActionBar(toolbar);
    //    Log.d("Title", Title);

        //toolbar.setTitle(movieDetails.gettitle());
        ListView = (RecyclerView) findViewById(R.id.contentlist);
        ListView.setHasFixedSize(true);
        ListView.setItemAnimator(new DefaultItemAnimator());
        ListView.setLayoutManager(new GridLayoutManager(this, 1));

        ArrayList<String> TrailerList = null;
        ArrayList<Reviews> ReviewList= null;
        if (Mode==1) {
          //  Log.d("Review","inside");
            ReviewList=i.getParcelableArrayListExtra("ReviewList");
            ListView.setAdapter(new ReviewAdapter(ReviewList,this));
        } else if(Mode==2) {
          //  Log.d("Trailer","inside");
            TrailerList = i.getStringArrayListExtra("TrailerList");
            trailerListInterface = new TrailerListInterface() {
                @Override
                public void Onclick(int position, String id) {
                    watchYoutubeVideo(id);
                }
            };
            NormalListadapter normalListadapter = new NormalListadapter(TrailerList, trailerListInterface, this);
            ListView.setAdapter(normalListadapter);
        }


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
}
