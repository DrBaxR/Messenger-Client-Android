package com.example.messenger_client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messenger_client_android.Model.User;
import com.example.messenger_client_android.Utils.Api;
import com.example.messenger_client_android.Utils.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    UserService userService;
    EditText edUid;
    EditText edUemail;
    EditText edUPasword;
    EditText username;
    Button btnBack;
    Button btnSave;
    Button btnDel;
    TextView tUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        tUserId =(TextView) findViewById(R.id.tUserId);
        edUid =(EditText) findViewById(R.id.edUid);
        edUemail =(EditText) findViewById(R.id.edUemail);
        btnBack =(Button) findViewById(R.id.btnBack);
        btnSave =(Button) findViewById(R.id.btnSave);
        btnDel =(Button) findViewById(R.id.btnDel);
        username = (EditText) findViewById(R.id.username);
        edUPasword = (EditText) findViewById(R.id.edUpassword);

        userService = Api.getUserService();

        Bundle extras = getIntent().getExtras();
        String userId = extras.getString("user_id");
        String userEmail = extras.getString("user_email");

        edUid.setText(userId);
        edUemail.setText(userEmail);

        if(userId != null && userId.trim().length()>0){
            edUid.setFocusable(false);
        }else{
            tUserId.setVisibility(View.INVISIBLE);
            edUid.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateEmailAdress(edUemail)){
                User u = new User();
                u.setEmail(edUemail.getText().toString());
                u.setPassword(edUPasword.getText().toString());
                u.setUsername(username.getText().toString());
                if(userId !=null && userId.trim().length()>0){
                    //update user
                }else{
                    //add user
                    addUser(u);

                }
            }}
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }

    public void addUser(User u){
        Call<User> call = userService.addUser(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UserActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ErrorL ",t.getMessage());
            }
        });
    }

    private boolean validateEmailAdress(EditText email){
        String emailInput = email.getText().toString();
        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(this,"You have successfully registered",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this,"Email validation invalid",Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void deleteUser(String id){
        Call<User> call = userService.deleteUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UserActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ErrorL ",t.getMessage());
            }
        });
    }









}