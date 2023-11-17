package com.example.pharmacyio.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pharmacyio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class authentication extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonRegister;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        editTextEmail = findViewById(R.id.signup_email);
        editTextPassword = findViewById(R.id.signup_password);
        buttonRegister = findViewById(R.id.signup_btn);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }


    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration success
                            Toast.makeText(authentication.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            // Store user data in Firestore
                            storeUserDataInFirestore();

                            // You can navigate to the next activity or perform other actions here
                        } else {
                            // If registration fails, display a message to the user.
                            Toast.makeText(authentication.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void storeUserDataInFirestore() {
        // Get the current user's ID
        String userId = mAuth.getCurrentUser().getUid();

        // Create a user map to store data in Firestore
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", mAuth.getCurrentUser().getEmail());
        // Add more user data as needed

        // Add the user data to Firestore
        firestore.collection("users").document(userId)
                .set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // User data added to Firestore successfully
                            Toast.makeText(authentication.this, "User data added to Firestore", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(authentication.this, main_activity.class
                            );
                            startActivity(intent);
                        } else {
                            // Failed to add user data to Firestore
                            Toast.makeText(authentication.this, "Failed to add user data to Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    }