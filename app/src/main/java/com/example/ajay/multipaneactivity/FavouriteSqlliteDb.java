package com.example.ajay.multipaneactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by allu on 6/4/16.
 */
public class FavouriteSqlliteDb extends SQLiteOpenHelper {

    Context ThisContext;
    public String Tag="FavouriteSqlliteDb";

    public String DBName="Favourite";
    public String Table_name="FavTable";
    public String Column_Title="Title";
    public String Column_Id="Id";
    public String Column_Overview="Overview";
    public String Column_poster_path="Poster_path";
    public String Column_original_language="Original_language";
    public String Column_Year="Year";
    public String Column_Rating="Rating";



    public FavouriteSqlliteDb(Context context) {
        super(context, "Favourite", null, 1);
        this.ThisContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateQuery="create table "+Table_name+" ("+Column_Title+" varchar(20),"+Column_Id+" number(10),"+Column_Overview+" varchar(300),"+Column_poster_path+" varchar(30),"+Column_original_language+" varchar(15),"+Column_Year+" number(5),"+Column_Rating+" number(10))";
        db.execSQL(CreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(db.getVersion()!=newVersion){

        }
    }

    public void deleteFav(String id){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(Table_name,Column_Id+"="+id,null);
       // Log.d(Tag,"deletion done");
    }

    public Boolean CheckifIDAvailable(String Id){
        SQLiteDatabase db=getWritableDatabase();
        String query="select "+Column_Id+ " from " +Table_name+" where "+Column_Id+" = "+Id+"";
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            return false;
        }else {
          if(Id.equals(cursor.getString(cursor.getColumnIndex(Column_Id)))){
        //      Log.d(Tag,"true");
              return true;
          }
        }
        return false;
    }

    public void addFavourite(MovieDetails movieDetails){
        SQLiteDatabase db=getWritableDatabase();
        if(!CheckifIDAvailable(movieDetails.getMovieId())){
            ContentValues contentValues=new ContentValues();
            contentValues.put(Column_Title,movieDetails.gettitle());
            contentValues.put(Column_Id,movieDetails.getMovieId());
            contentValues.put(Column_Overview,movieDetails.getOverview());
            contentValues.put(Column_poster_path,movieDetails.getPosterUrl());
            contentValues.put(Column_original_language,movieDetails.getOriginal_language());
            contentValues.put(Column_Rating,movieDetails.getRating());
            contentValues.put(Column_Year,movieDetails.getReleaseDate());
            db.insert(Table_name,null,contentValues);
         //   Log.d(Tag,"Fav Added");
        }else{
         //   Log.d(Tag,"Fav already present");
        }

    }

    public ArrayList<MovieDetails> getFavourite(){
        ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<>();
        MovieDetails movieDetails;
        SQLiteDatabase db=getReadableDatabase();
        String query="select * from "+Table_name;
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            return null;
        }else{
            for(int i=0;i<cursor.getCount();i++){
                String id=cursor.getString(cursor.getColumnIndex(Column_Id));
                String Title=cursor.getString(cursor.getColumnIndex(Column_Title));
                String Overview=cursor.getString(cursor.getColumnIndex(Column_Overview));
                String OriginalLang=cursor.getString(cursor.getColumnIndex(Column_original_language));
                String posterpath=cursor.getString(cursor.getColumnIndex(Column_poster_path));
                String rating=cursor.getString(cursor.getColumnIndex(Column_Rating));
                String year=cursor.getString(cursor.getColumnIndex(Column_Year));
                movieDetails=new MovieDetails(id,Title,Overview,posterpath,OriginalLang,rating,year);
                movieDetailsArrayList.add(movieDetails);
                cursor.moveToNext();
            }
            return movieDetailsArrayList;
        }
    }

}
