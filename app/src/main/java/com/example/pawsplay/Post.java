package com.example.pawsplay;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {

    private String content, userId, dogAge, dogName, dogSize, dogActivityLevel, zipcode, userName;

    public Post() {

    }

    public Post(Parcel in){
        content = in.readString();
        userName = in.readString();
        dogAge = in.readString();
        dogName = in.readString();
        dogSize = in.readString();
    }

    public Post(String content, String userId, String dogAge, String dogName, String dogSize, String dogActivityLevel, String zipcode, String userName){
        this.content = content;
        this.userId = userId;
        this.dogAge = dogAge;
        this.dogName = dogName;
        this.dogSize = dogSize;
        this.dogActivityLevel= dogActivityLevel;
        this.zipcode = zipcode;
        this.userName = userName;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getContent(){
        return this.content;
    }

    public String getUserId(){
        return this.userId;
    }

    public String getDogAge(){
        return this.dogAge;
    }

    public String getDogName(){
        return this.dogName;
    }

    public String getDogSize(){
        return this.dogSize;
    }

    public String getDogActivityLevel(){
        return this.dogActivityLevel;
    }

    public String getZipcode(){
        return this.zipcode;
    }

    public String getUserName(){
        return this.userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(userName);
        dest.writeString(dogName);
        dest.writeString(dogSize);
        dest.writeString(dogAge);
    }
}
