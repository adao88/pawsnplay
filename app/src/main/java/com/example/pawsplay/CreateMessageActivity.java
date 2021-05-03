package com.example.pawsplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateMessageActivity extends AppCompatActivity {

    private EditText messageText;
    private TextView recipientUserNameTextView;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Button submitMessageButton;
    private String userId, userName, recipientId, recipientUserName;
    private String messageContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        messageText = (EditText)findViewById(R.id.createMessageEditText);
        recipientUserName = getIntent().getStringExtra("recipientUserName");
        recipientUserNameTextView = (TextView) findViewById(R.id.recipientNameText);
        recipientUserNameTextView.setText(recipientUserName);

        submitMessageButton = (Button)findViewById(R.id.submitMessageButton);
        submitMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMessage();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    userName = userProfile.name;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreateMessageActivity.this, "Could not get User Profile", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void submitMessage(){

        messageContent = messageText.getText().toString();


        if(messageContent.isEmpty()){
            messageText.setError("Can not be Empty!");
            messageText.requestFocus();
            return;
        }

        recipientId = getIntent().getStringExtra("recipientUserId");

        Message message = new Message(messageContent, userName, userId, recipientId);

        reference = FirebaseDatabase.getInstance().getReference("Messages");

        reference.push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CreateMessageActivity.this, "Successful Post", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreateMessageActivity.this, PostsActivity.class));
                } else {
                    Toast.makeText(CreateMessageActivity.this, "Post Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}