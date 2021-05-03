package com.example.pawsplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditDogProfile extends AppCompatActivity {

    private Spinner dogSizeSpinner;
    private Spinner dogActivtySpinner;
    private EditText dogNameTextEdit, dogAgeTextEdit;
    private Button editDogProfile;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dog_profile);

        dogSizeSpinner = (Spinner) findViewById(R.id.dogSize_spinner);
        ArrayAdapter <CharSequence> dogSizeAdapter = ArrayAdapter.createFromResource(this, R.array.dogSize_array, android.R.layout.simple_spinner_dropdown_item);
        dogSizeSpinner.setAdapter(dogSizeAdapter);

        dogActivtySpinner = (Spinner) findViewById(R.id.dogActivityLevel_Spinner);
        ArrayAdapter <CharSequence> dogActivityLevelAdapter = ArrayAdapter.createFromResource(this, R.array.dogActivityLevel_array, android.R.layout.simple_spinner_dropdown_item);
        dogActivtySpinner.setAdapter(dogActivityLevelAdapter);

        dogNameTextEdit = (EditText)findViewById(R.id.editDogName);
        dogAgeTextEdit = (EditText)findViewById(R.id.editDogAge);

        editDogProfile = (Button)findViewById(R.id.submitDogProfileButton);
        editDogProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDogProfile();
            }
        });


    }

    public void submitDogProfile(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        String dogName = dogNameTextEdit.getText().toString().trim();
        String dogAge = dogAgeTextEdit.getText().toString().trim();
        String dogSize = dogSizeSpinner.getSelectedItem().toString();
        String dogActivityLevel = dogActivtySpinner.getSelectedItem().toString();

        if (dogName.isEmpty()){
            dogNameTextEdit.setError("Name Required");
            dogNameTextEdit.requestFocus();
            return;
        }

        if(dogAge.isEmpty() || !(dogAge.matches("^[0-9]*$"))){
            dogAgeTextEdit.setError("Number Required");
            dogAgeTextEdit.requestFocus();
            return;
        }

        Dog dog = new Dog(dogName, dogAge, dogActivityLevel, dogSize);


        reference.child(userId).child("Dog").setValue(dog).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EditDogProfile.this, "Dog Profile updated", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(EditDogProfile.this, ProfileActivity.class));
                        } else {
                            Toast.makeText(EditDogProfile.this, "Dog Profile Update Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}