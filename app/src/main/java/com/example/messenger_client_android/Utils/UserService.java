package com.example.messenger_client_android.Utils;

import com.example.messenger_client_android.Model.Login;
import com.example.messenger_client_android.Model.User;
import com.example.messenger_client_android.Model.UserList;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {


    @GET("/users")
    Call<List<User>> getUsers();


    @GET("/users")
    Call<UserList> getUserList();


    @POST("/api/auth/signup")
    Call<User> addUser(@Body User user);

    @POST("/api/auth/signin")
    Call<User> login(@Body Login login);




    @GET("/users/{id}")
    Call<User> getOneUser(@Path("id") int id);

    @DELETE("/users/{id}")
    Call<User> deleteUser(@Path("id") int id);



}
