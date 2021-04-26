package com.example.messenger_client_android.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserList {

    @SerializedName("_embedded")
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
