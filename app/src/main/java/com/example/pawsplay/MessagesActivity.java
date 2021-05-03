package com.example.pawsplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity implements MessageAdapter.OnMessageListener{

    private DatabaseReference reference;

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Message> messageList = new ArrayList<>();
    private FirebaseUser user;
    private String userId;
    private String TAG = "MessagesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Log.d(TAG, "In Messages");
        userId = user.getUid();

        mRecyclerView = findViewById(R.id.messagesRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        reference = FirebaseDatabase.getInstance().getReference("Messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();

                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Message message = postSnapshot.getValue(Message.class);
                    //if(message.getRecipientUserId().equals(userId)){
                        messageList.add(message);
                        Log.d(TAG, "Got Message: " + message.getContent());
                    //}
                }
                if (!messageList.isEmpty()){
                    Log.d(TAG, "Non Empty List");
                    mAdapter = new MessageAdapter(messageList, MessagesActivity.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                } else {

                    Log.d(TAG, "Empty List");
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Unable to get Messages");
                Log.d(TAG, "Empty List");
                mRecyclerView.setLayoutManager(mLayoutManager);
            }
        });

    }

    @Override
    public void onMessageClick(int position) {
        Message message = messageList.get(position);
        Intent intent = new Intent(this, MessageViewActivity.class);
        intent.putExtra("content", message.getContent());
        intent.putExtra("author", message.getAuthorName());
        startActivity(intent);
    }
}