package com.example.messenger_client_android.Utils;

import com.example.messenger_client_android.Model.User;
import com.example.messenger_client_android.Model.UserList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @Headers({
            "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2bGFkdGVzdGFuZHJvZGlAbWFpbC5jb20iLCJpYXQiOjE2MTk0NTU5MDAsImV4cCI6MTYxOTU0MjMwMH0.eQJ-CdSE61BjduXA8zAAeaYgf6yey40SmgbngXZioX0CHQ9HCEwVcAkqp-tlnI_2WYjM5qUHP_UeprG1cenV5A"
    })
    @GET("/users")
    Call<List<User>> getUsers();

    @Headers({
            "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2bGFkdGVzdGFuZHJvZGlAbWFpbC5jb20iLCJpYXQiOjE2MTk0NTU5MDAsImV4cCI6MTYxOTU0MjMwMH0.eQJ-CdSE61BjduXA8zAAeaYgf6yey40SmgbngXZioX0CHQ9HCEwVcAkqp-tlnI_2WYjM5qUHP_UeprG1cenV5A"
    })
    @GET("/users")
    Call<UserList> getUserList();


    @POST("/api/auth/signup")
    Call<User> addUser(@Body User user);

    @GET("/users/{id}")
    Call<User> getOneUser(@Path("id") int id);

    @DELETE("/users/{id}")
    Call<User> deleteUser(@Path("id") int id);



}
