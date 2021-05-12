package com.example.messenger_client_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messenger_client_android.Model.Group;
import com.example.messenger_client_android.Model.User;
import com.example.messenger_client_android.Utils.Api;
import com.example.messenger_client_android.Utils.UserService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupActivity extends AppCompatActivity {

    Button addGroupBtn;
    EditText groupName;
    UserService userService;
    Button addUserBtn;
    Button getUserBtn;
    EditText userEmail;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        addGroupBtn = (Button) findViewById(R.id.addGroup);
        groupName = (EditText) findViewById(R.id.groupName);
        addUserBtn = (Button) findViewById(R.id.addUserToGroup);
        userEmail = (EditText) findViewById(R.id.userEmailToAdd);
        getUserBtn = (Button) findViewById(R.id.getUserFromGroup);


        userService = Api.getUserService();

        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group g = new Group();
                g.setName(groupName.getText().toString());
                addGroup(g,LoginActivity.currentId);
            }
        });

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //IN PROGRESS
                String email = userEmail.getText().toString();
                //email = email.replace("\"", "");
                RequestBody body = RequestBody.create(MediaType.parse("text/plain"),email);

                addUserToGroup(body,"60955fdfb07318720d080f68");
            }
        });

        getUserBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //id hardcodat din baza de date
                String id = "6092dd621fbce535c3d09593";
                getUsersFromGroup(id);
            }
        }));

    }



    public void addGroup(Group g, String id){
        Call<Group> call = userService.addGroup(g,id,LoginActivity.token);
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if(response.isSuccessful()){


                    Toast.makeText(GroupActivity.this, "Group created successfully", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {

                Log.e("Error",t.getMessage());
            }
        });
    }

    public void addUserToGroup(RequestBody email, String id){

        Call<Group> call = userService.addUserToGroup(email,id,LoginActivity.token);

        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if(response.isSuccessful()){
                    Toast.makeText(GroupActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });

    }

    public void getUsersFromGroup(String id){
        Call<List<User>> call = userService.getUsersFromGroup(id,LoginActivity.token);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(GroupActivity.this, "Watch at the console", Toast.LENGTH_SHORT).show();
                    System.out.println(response.body().get(0).getEmail());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }



}
