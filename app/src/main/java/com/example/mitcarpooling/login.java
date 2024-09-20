package com.example.mitcarpooling;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import android.content.Context;
import android.content.SharedPreferences;

public class login extends AppCompatActivity {


    private EditText editTextLoginEmail, editTextLoginPasswd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPasswd = findViewById(R.id.editText_login_passwd);
        progressBar = findViewById(R.id.progressBar);
        authProfile = FirebaseAuth.getInstance();




        Button loginbutton = findViewById(R.id.login_button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPasswd = editTextLoginPasswd.getText().toString();

                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Email is required");
                    editTextLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPasswd)) {
                    Toast.makeText(login.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    editTextLoginPasswd.setError("Password is required");
                    editTextLoginPasswd.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail, textPasswd);
                }

            }
        });


    }



    private void loginUser(String email, String Passwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        authProfile.signInWithEmailAndPassword(email, Passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(login.this, "You are already logged in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this, chooseride.class);
                        startActivity(intent);
                        finish();



                    } else {
                        Toast.makeText(login.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        editTextLoginEmail.setError("User does not exists or is no longer valid. Please register again.");
                        editTextLoginEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        editTextLoginPasswd.setError("Invalid credentials. Please try again.");
                        editTextLoginPasswd.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }
                progressBar.setVisibility(View.GONE);

            }

        });



    }

/*    private void logoutUser() {
        // Sign out from FirebaseauthProfile.signOut();

        // Clear login state in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();

        // Redirect to login activity or show a login prompt
        Intent intent = new Intent(login.this, login.class); // Or your login activity
        startActivity(intent);
        finish(); // Optional: Finish current activity
    }

    */
}




 /*   @Override
    protected void onStart() {
        super.onStart();

        if (authProfile.getCurrentUser() != null) {
            Toast.makeText(login.this,"You are already logged in",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(login.this, chooseride.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(login.this,"You are not logged in. You can login now",Toast.LENGTH_SHORT).show();
        }
    }
*/


