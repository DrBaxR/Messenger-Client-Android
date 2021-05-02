package com.example.messenger_client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.messenger_client_android.Model.User;
import com.example.messenger_client_android.Model.UserList;
import com.example.messenger_client_android.Utils.Api;
import com.example.messenger_client_android.Utils.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnAddUser;
    Button btnGetUsersList;
    ListView listView;
    Button btnLogin;

    UserService userService;
    UserList list = new UserList();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddUser = (Button) findViewById(R.id.btnAddUser);
        btnGetUsersList = (Button) findViewById(R.id.btnGetUsersList);
        listView = (ListView) findViewById(R.id.listView);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        userService = Api.getUserService();

        btnGetUsersList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //get users list
                getUsersList();
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("user_email", "");
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getUsersList() {
        Call<UserList> call = userService.getUserList();
        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if(response.isSuccessful()){
                    list = response.body();

                   // listView.setAdapter(new UserAdapter(MainActivity.this,R.layout.list_user, list.getEntity().getUsers()));
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t){
                Log.e("ERROR: ",t.getMessage());
            }

        });
    }
}
