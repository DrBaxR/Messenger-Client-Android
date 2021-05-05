package com.example.messenger_client_android.Utils;

import com.example.messenger_client_android.Model.Group;
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

    //Login & Register
    @POST("/api/auth/signup")
    Call<User> addUser(@Body User user);

    @POST("/api/auth/signin")
    Call<User> login(@Body Login login);

    //Users
    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/users")
    Call<UserList> getUserList();

    @DELETE("/users/{id}")
    Call<User> deleteUser(@Path("id") String id);

    @PUT("/users/{id}")
    Call<User> updateUsername(@Body User user, @Path("id") String id, @Header("Authorization") String authHeader);


    //GROUPS
    @POST("/users/{id}/groups")
    Call<Group> addGroup(@Body Group group, @Path("id") String id, @Header("Authorization") String authHeader);

    @PUT("/groups/{id}/users")
    Call<Group> addUserToGroup(@Body List<String> userEmail, @Path("id") String id, @Header("Authorization") String authHeader);

    @GET("/users/{id}/groups")
    Call<List<Group>> getAllGroups(@Path("id")String id, @Header("Authorization") String authHeader);


}
