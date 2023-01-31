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

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    TextView signIn;
    Button create;
    ImageView google, facebook;
    EditText email, password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signIn = findViewById(R.id.signInID);
        create = findViewById(R.id.createID);
        google = findViewById(R.id.googleID);
        facebook = findViewById(R.id.facebookID);
        email = findViewById(R.id.emailID);
        password = findViewById(R.id.passwordID);

        create.setOnClickListener(this);
        signIn.setOnClickListener(this);
        google.setOnClickListener(this);
        facebook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signInID) {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        }
    }
}