package com.example.pawsplay;

public class Message {
    private String content, authorName, authorUserId, recipientUserId;

    public Message(){

    }

    public Message(String content, String authorName, String authorUserId, String recipientUserId){
        this.content = content;
        this.authorName = authorName;
        this.authorUserId = authorUserId;
        this.recipientUserId = recipientUserId;
    }

    public String getContent(){
        return this.content;
    }

    public String getAuthorName(){
        return this.authorName;
    }

    public String getAuthorUserId(){
        return this.authorUserId;
    }

    public String getRecipientUserId(){
        return this.recipientUserId;
    }
}
