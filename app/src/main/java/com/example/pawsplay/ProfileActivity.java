package com.example.pawsplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity  {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    private Button logout, editDogProfile, goToPosts, goToMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = (Button) findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        goToMessages = (Button)findViewById(R.id.messagesButton);
        goToMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MessagesActivity.class));
            }
        });

        goToPosts = (Button)findViewById(R.id.goToPostsButton);
        goToPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PostsActivity.class));
            }
        });

        editDogProfile = (Button) findViewById(R.id.editDogProfileButton);
        editDogProfile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditDogProfile.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        userId = user.getUid();

        final TextView nameTextView = (TextView) findViewById(R.id.nameDisplayProfile);
        final TextView emailTextView = (TextView) findViewById(R.id.emailDisplayProfile);
        final TextView zipcodeTextView = (TextView) findViewById(R.id.zipCodeDisplayProfile);

        final TextView dogNameTextView = (TextView) findViewById(R.id.dogNameDisplayProfile);
        final TextView dogAgeTextView = (TextView) findViewById(R.id.dogAgeDisplayProfile);
        final TextView dogActivityTextView = (TextView) findViewById(R.id.dogActivityLevelDisplayProfile);
        final TextView dogSizeTextView = (TextView) findViewById(R.id.dogSizeDisplayProfile);



        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String name = userProfile.name;
                    String email = userProfile.email;
                    String zipcode = userProfile.zipcode;


                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    zipcodeTextView.setText(zipcode);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Could not get User Profile", Toast.LENGTH_LONG).show();
            }
        });


        reference.child(userId).child("Dog").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Dog dog = snapshot.getValue(Dog.class);

                if (dog != null){

                    String dogName = dog.getName();
                    String dogAge = dog.getAge();
                    String dogActivityLevel = dog.getActivityLevel();
                    String dogSize = dog.getSize();

                    dogNameTextView.setText(dogName);
                    dogActivityTextView.setText(dogActivityLevel);
                    dogAgeTextView.setText(dogAge);
                    dogSizeTextView.setText(dogSize);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Could not get Dog Profile", Toast.LENGTH_LONG).show();
            }
        });
    }


}