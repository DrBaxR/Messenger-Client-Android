package com.example.messenger_client_android.Utils;

public class Api {

    public static final String URL = "http://10.0.2.2:8080";

    public static UserService getUserService(){
        return Client.getClient(URL).create(UserService.class);
    }

}
