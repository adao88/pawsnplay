package com.example.pawsplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CreatePostActivity extends AppCompatActivity {

    private EditText postText;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Button submitPostButton;
    private String userId;
    private String postContent;
    private String userName, zipcode, dogName, dogAge, dogSize, dogActivityLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        submitPostButton = (Button)findViewById(R.id.submitPostButton);
        submitPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        postText = (EditText)findViewById(R.id.createPostEditText);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    userName = userProfile.name;
                    zipcode = userProfile.zipcode;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreatePostActivity.this, "Could not get User Profile", Toast.LENGTH_LONG).show();
            }
        });


        reference.child(userId).child("Dog").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Dog dog = snapshot.getValue(Dog.class);

                if (dog != null){
                    dogName = dog.getName();
                    dogAge = dog.getAge();
                    dogActivityLevel = dog.getActivityLevel();
                    dogSize = dog.getSize();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreatePostActivity.this, "Could not get Dog Profile", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void submitPost(){

        postContent = postText.getText().toString();

        if(postContent.isEmpty()){
            postText.setError("Can not be Empty!");
            postText.requestFocus();
            return;
        }

        Post post = new Post(postContent, userId, dogAge, dogName, dogSize, dogActivityLevel, zipcode, userName);
        reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.push().setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CreatePostActivity.this, "Successful Post", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreatePostActivity.this, PostsActivity.class));
                } else {
                    Toast.makeText(CreatePostActivity.this, "Post Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}