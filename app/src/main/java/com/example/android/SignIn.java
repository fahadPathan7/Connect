package com.example.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    TextView signUp, forgotPass;
    Button logIn;
    ImageView google, facebook;
    EditText email, password;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            finish();
            start_HomeScreenUser_activity();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        signUp = findViewById(R.id.signupID);
        logIn = findViewById(R.id.loginID);
        google = findViewById(R.id.googleID);
        facebook = findViewById(R.id.facebookID);
        email = findViewById(R.id.emailID);
        password = findViewById(R.id.passwordID);
        forgotPass = findViewById(R.id.forgotPassID);

        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);
        google.setOnClickListener(this);
        facebook.setOnClickListener(this);
    }

    // to work with on Click listeners
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signupID) {
            finish();
            start_SignUp_activity();
        }

        if (v.getId() == R.id.loginID) {
            getLogin("email");
        }
    }

    // login for already registered users
    private void getLogin(String type) {
        String emailText = email.getText().toString();
        String passText = password.getText().toString();

        // checking for email input
        if(emailText.isEmpty()) {
            email.setError("Enter an email address");
            email.requestFocus();
            return;
        }

        // checking the email is valid or not
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Enter a valid email address");
            email.requestFocus();
            return;
        }

        // checking the validity of the password
        if(passText.isEmpty() || passText.length() < 8) {
            password.setError("minimum length is 8");
            password.requestFocus();
            return;
        }

        if (type.equals("email")) loginWithEmailAndPass(emailText, passText); // if tries to login with email and pass
    }

    // verification of valid users. (email and pass)
    private void loginWithEmailAndPass(String emailText, String passText) {
        mAuth.signInWithEmailAndPassword(emailText, passText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    start_HomeScreenUser_activity();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void start_SignUp_activity() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void start_HomeScreenUser_activity() {
        Intent intent = new Intent(this, HomeScreen_user.class);
        startActivity(intent);
    }
}