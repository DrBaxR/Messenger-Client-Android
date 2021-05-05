package com.example.messenger_client_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.messenger_client_android.Model.Group;
import com.example.messenger_client_android.Model.User;
import com.example.messenger_client_android.Utils.Api;
import com.example.messenger_client_android.Utils.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfterLoginActivity extends AppCompatActivity {

    UserService userService;
    EditText changeUsername;
    Button changeUser;
    Button goToGroups;
    Button getGroupsBtn;
    ListView listGroups;
    List<Group> list = new ArrayList<Group>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userv2);

        changeUsername = (EditText) findViewById(R.id.changeUsername);
        changeUser = (Button) findViewById(R.id.changeUsernameButton);
        goToGroups = (Button) findViewById(R.id.toGroups);
        getGroupsBtn = (Button) findViewById(R.id.getGroups);
        listGroups = (ListView) findViewById(R.id.listGroups);

        userService = Api.getUserService();

        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User u = new User();
                u.setUsername(changeUsername.getText().toString());
                updateUsername(u,LoginActivity.currentId);

            }
        });

        goToGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AfterLoginActivity.this, GroupActivity.class);
                startActivity(intent);

            }
        });

        getGroupsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGroups(LoginActivity.currentId);
            }
        });
    }

    public void updateUsername(User u, String id){
        Call<User> call = userService.updateUsername(u,id,LoginActivity.token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AfterLoginActivity.this, "Username updated successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

    }
    //IN PROGRESS
    public void getGroups(String id){
        Call<List<Group>> call = userService.getAllGroups(id,LoginActivity.token);
        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AfterLoginActivity.this,"Groups:", Toast.LENGTH_LONG).show();
                    //list = response.body();
                    //ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(this,android.R.layout.simple_list_item_1,list.get(1).getName());
                    //listGroups.setAdapter(adapter);
                    System.out.println(response.body().get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

}
