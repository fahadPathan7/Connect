package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    TextView signUp, forgotPass;
    Button logIn;
    ImageView google, facebook;
    EditText email, password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signupID) {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
    }
}