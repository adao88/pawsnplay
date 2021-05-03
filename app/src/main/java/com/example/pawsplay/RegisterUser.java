package com.example.pawsplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner, registerUser;
    private EditText editTextName, editTextEmail, editTextPassword, editTextZipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.bannerRegister);
        banner.setOnClickListener(this);

        registerUser = (Button)findViewById(R.id.registerUserButton);
        registerUser.setOnClickListener(this);

        editTextEmail = (EditText)findViewById(R.id.editTextEmailRegister);
        editTextName = (EditText)findViewById(R.id.editTextNameRegister);
        editTextPassword = (EditText)findViewById(R.id.editTextPasswordRegister);
        editTextZipcode = (EditText) findViewById(R.id.editTextZipCodeRegister);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bannerRegister:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUserButton:
                registerUser();
        }
    }

    public void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String zipcode = editTextZipcode.getText().toString().trim();

        if (name.isEmpty()){
            editTextName.setError("Full Name Required");
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editTextEmail.setError("Email Required");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password Required");
            editTextPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.length()  < 6){
            editTextPassword.setError("Password length of 6 characters minimum");
            editTextPassword.requestFocus();
            return;
        }

        if(!(zipcode.matches("^[0-9]*$")) && zipcode.length() != 5){
            editTextZipcode.setError("Please enter a valid zipcode");
            editTextZipcode.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        ;
                        if(task.isSuccessful()){
                            User user = new User(name, email, zipcode);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "User has been registered Successfully", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(RegisterUser.this, "Failed To Register", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterUser.this, "Failed To Register", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}