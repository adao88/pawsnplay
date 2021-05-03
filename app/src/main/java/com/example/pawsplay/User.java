package com.example.pawsplay;

public class User {

    public String name, email;
    public String zipcode;
    private Dog dog;

    public User(){

    }

    public Dog getDog(){
        return this.dog;
    }

    public User(String name, String email, String zipcode){
        this.name = name;
        this.email = email;
        this.zipcode = zipcode;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getZipcode(){
        return this.zipcode;
    }
}
