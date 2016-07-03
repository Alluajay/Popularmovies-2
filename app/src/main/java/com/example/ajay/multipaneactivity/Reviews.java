package com.example.ajay.multipaneactivity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allu on 6/11/16.
 */
public class Reviews implements Parcelable {
    public String Author,Content;
    public Reviews(String author,String content){
        this.Author=author;
        this.Content=content;
    }

    protected Reviews(Parcel in) {
        Author = in.readString();
        Content = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public String getAuthor(){
        return this.Author;
    }
    public String getContent(){
        return this.Content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Author);
        dest.writeString(Content);
    }
}
