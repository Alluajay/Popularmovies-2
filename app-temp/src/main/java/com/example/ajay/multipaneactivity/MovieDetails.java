package com.example.ajay.multipaneactivity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ajay on 4/22/2016.
 */
public class MovieDetails implements Parcelable {
    public String posterUrl;
    public String Id;
    public String ReleaseDate,rating;
    public String overview,title,original_language;
    public ArrayList<String> TrailerList;


    public MovieDetails(String id,String title1, String overview1, String posterurl, String language, String rating1 ,String date){
        this.Id=id;
        this.title=title1;
        this.overview=overview1;
        this.posterUrl=posterurl;
        this.original_language=language;
        this.rating=rating1;
        this.ReleaseDate=date;
        TrailerList=null;
    }

    public MovieDetails(MovieDetails movieDetails){

        this.title=movieDetails.gettitle();
    }

    public void addTrailer(ArrayList<String> mTrailerList){
        this.TrailerList=mTrailerList;
    }

    protected MovieDetails(Parcel in) {
        posterUrl = in.readString();
        Id = in.readString();
        ReleaseDate = in.readString();
        rating = in.readString();
        overview = in.readString();
        title = in.readString();
        original_language = in.readString();
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    public String gettitle(){
        return this.title;
    }
    public String getOverview(){ return this.overview;}
    public String getMovieId(){return this.Id;}
    public String getPosterUrl(){return this.posterUrl;}
    public String getReleaseDate(){return this.ReleaseDate;}
    public String getRating(){return this.rating;}
    public String getOriginal_language(){return this.original_language;}



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterUrl);
        dest.writeString(Id);
        dest.writeString(ReleaseDate);
        dest.writeString(rating);
        dest.writeString(overview);
        dest.writeString(title);
        dest.writeString(original_language);
    }
}
