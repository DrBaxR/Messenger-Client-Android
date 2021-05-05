package com.example.messenger_client_android.Model;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private String id;
    private String name;
    private List<String> users = new ArrayList();
    private List<String> messages = new ArrayList();



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
