package com.example.messenger_client_android.Model;

public class Message {

    private String text;
    private String date;
    private String sender;

    public Message(String text, String date, String sender) {
        this.text = text;
        this.date = date;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
