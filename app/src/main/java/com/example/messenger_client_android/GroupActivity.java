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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupActivity extends AppCompatActivity {

    Button addGroupBtn;
    EditText groupName;
    UserService userService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        addGroupBtn = (Button) findViewById(R.id.addGroup);
        groupName = (EditText) findViewById(R.id.groupName);

        userService = Api.getUserService();

        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group g = new Group();
                g.setName(groupName.getText().toString());
                addGroup(g,LoginActivity.currentId);
            }
        });
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


}
