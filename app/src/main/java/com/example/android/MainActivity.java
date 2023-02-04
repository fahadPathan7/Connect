/*
this class is used for intro.
after the intro it will automatically goto sign in page.
 */

package com.example.android;

import authentication.SignIn;
import commonClasses.Profile;
import commonClasses.UpdateProfile;
import navigationBars.DrawerBaseActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
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

        changeStatusBarColor();

        start_activity();
    }

    public void start_activity() {
        finish();
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.theme_color));
        }
    }
}