package com.example.bookify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private FirebaseAuth auth; // Firebase Auth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Ensure this refers to the correct layout

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        ImageButton backButton = findViewById(R.id.backButton); // Initialize backButton
        TextView forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView); // Initialize forgot password text view

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String email = usernameEditText.getText().toString().trim(); // Use email for authentication
            String password = passwordEditText.getText().toString().trim();

            // Add login validation logic here
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("LoginActivity", "Login button clicked with email: " + email);
                loginUser(email, password);
            }
        });

        // Handle back button click
        backButton.setOnClickListener(v -> {
            // Navigate back to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close LoginActivity so it can't be returned to
        });

        // Handle forgot password text click
        forgotPasswordTextView.setOnClickListener(v -> {
            // Navigate to ForgotPasswordActivity
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    // Method to handle user login
    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful, get current user
                        FirebaseUser user = auth.getCurrentUser();
                        assert user != null;
                        Log.d("LoginActivity", "Login successful for user: " + user.getEmail());
                        Toast.makeText(LoginActivity.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                        // Navigate to the next activity after successful login
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        // Change to your home activity
                        startActivity(intent);
                        finish();
                    } else {
                        // Login failed, show error message
                        Log.e("LoginActivity", "Login failed", task.getException());
                        Toast.makeText(LoginActivity.this, "Login gagal: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
