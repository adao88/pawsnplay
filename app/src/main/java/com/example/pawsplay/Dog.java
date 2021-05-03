package com.example.pawsplay;

public class Dog {

    public String name, age, activityLevel, size;

    public Dog(){

    }

    public Dog(String name, String age, String activityLevel, String size){
        this.name = name;
        this.age = age;
        this.activityLevel = activityLevel;
        this.size = size;
    }

    public String getName(){
        return this.name;
    }

    public String getAge(){
        return this.age;
    }

    public String getActivityLevel(){
        return this.activityLevel;
    }

    public String getSize(){
        return this.size;
    }
}
