package com.example.messenger_client_android;

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
import com.example.messenger_client_android.Model.Message;
import com.example.messenger_client_android.Model.User;
import com.example.messenger_client_android.Utils.Api;
import com.example.messenger_client_android.Utils.UserService;
import com.google.gson.Gson;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import io.reactivex.disposables.CompositeDisposable;


import java.util.Date;
import java.util.TimeZone;


public class GroupActivity extends AppCompatActivity {

    Button addGroupBtn;
    EditText groupName;
    UserService userService;
    Button addUserBtn;
    Button getUserBtn;
    EditText userEmail;
    EditText messageInput;
    Button sendMessageBtn;
    ListView messageList;

    private List<String> messages = new ArrayList<String>();

    private StompClient mStompClient;
    private CompositeDisposable compositeDisposable;




    private static final String TAG = "GroupActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        addGroupBtn = (Button) findViewById(R.id.addGroup);
        groupName = (EditText) findViewById(R.id.groupName);
        addUserBtn = (Button) findViewById(R.id.addUserToGroup);
        userEmail = (EditText) findViewById(R.id.userEmailToAdd);
        getUserBtn = (Button) findViewById(R.id.getUserFromGroup);
        messageInput = (EditText) findViewById(R.id.sendMessage);
        sendMessageBtn = (Button) findViewById(R.id.sendMessageToHardcodedGroup);
        messageList = (ListView) findViewById(R.id.messageList);


        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" +  "10.0.2.2"
                + ":" + "8080" + "/messenger");




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

        sendMessageBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectStomp(view);
                String message = messageInput.getText().toString();
                sendMessage(message);

            }
        }));

    }

    public void disconnectStomp(View view) {
        mStompClient.disconnect();
    }

    public void connectStomp(View view) {





        resetSubscriptions();

        Disposable dispLifecycle = mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            toast("Stomp connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                            toast("Stomp connection error");
                            break;
                        case CLOSED:
                            toast("Stomp connection closed");
                            resetSubscriptions();
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            toast("Stomp failed server heartbeat");
                            break;
                    }
                });

        compositeDisposable.add(dispLifecycle);

        // Receive message
        Disposable dispTopic = mStompClient.topic("/topic/group.60955fdfb07318720d080f68")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Log.d(TAG, "Received " + topicMessage.getPayload());

                    Gson gson = new Gson();

                    Message message = gson.fromJson(topicMessage.getPayload(), Message.class);

                    messages.add(message.getText());
                    ArrayAdapter<String> messageTopic = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);
                    messageList.setAdapter(messageTopic);

                }, throwable -> {
                    Log.e(TAG, "Error on subscribe topic", throwable);
                });

        compositeDisposable.add(dispTopic);

        mStompClient.connect();
    }

    private void resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }

    private void toast(String text) {
        Log.i(TAG, text);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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

    public void sendMessage(String text){

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        Message message = new Message(text,nowAsISO,LoginActivity.currentId);

        mStompClient.send("/ws/group.chat/60955fdfb07318720d080f68", new Gson().toJson(message)).subscribe();
        System.out.println("test");

    }



}
