package com.example.messenger_client_android.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Entity {

    @SerializedName("users")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
