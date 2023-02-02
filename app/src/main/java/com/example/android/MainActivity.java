/*
this class is used for intro.
after the intro it will automatically goto sign in page.
 */

package com.example.android;

import authentication.*;
import navigationBars.DrawerBaseActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.android.databinding.ActivityMainBinding;

public class MainActivity extends DrawerBaseActivity {

    Button startButton;
    ActivityMainBinding activityMainBinding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        start_SignIn_activity();
    }

    public void start_SignIn_activity() {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

}