package com.example.camerarent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, confirmPassword;
    private Button registerButton;
    private TextView loginLink;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        // Set register button click listener
        registerButton.setOnClickListener(view -> registerUser());

        // Set login link click listener
        loginLink.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userConfirmPassword = confirmPassword.getText().toString();

        // Validate inputs
        if (TextUtils.isEmpty(userName)) {
            name.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            email.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            password.setError("Password is required");
            return;
        }
        if (TextUtils.isEmpty(userConfirmPassword)) {
            confirmPassword.setError("Confirm Password is required");
            return;
        }
        if (!userPassword.equals(userConfirmPassword)) {
            confirmPassword.setError("Passwords do not match");
            return;
        }

        // Create user in Firebase Authentication
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        // Redirect to HomeActivity
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}