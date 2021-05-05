package com.example.messenger_client_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messenger_client_android.Model.Login;
import com.example.messenger_client_android.Model.User;
import com.example.messenger_client_android.Utils.Api;
import com.example.messenger_client_android.Utils.UserService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    UserService userService;
    EditText email;
    EditText password;
    Button btnLogin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        userService = Api.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    public static String token;
    public static String currentId;
    public void login(){
        Login login = new Login(email.getText().toString(),password.getText().toString());
        Call<User> call = userService.login(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){

                    token = "Bearer " + response.body().accessToken();
                    currentId = response.body().getId();
                    Toast.makeText(LoginActivity.this,"Login successfully", Toast.LENGTH_SHORT).show();
                    if(token != null){

                        Intent intent = new Intent(LoginActivity.this, AfterLoginActivity.class);
                        startActivity(intent);

                    }

                }else{
                    Toast.makeText(LoginActivity.this,"login not correct", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"error", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
