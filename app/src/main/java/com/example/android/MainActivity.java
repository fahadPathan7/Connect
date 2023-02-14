/*
this class is used for intro.
after the intro it will automatically goto sign in page.
 */

package com.example.android;

import authentication.SignIn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN = 2000; // time to display splash screen
    ImageView imageView;
    TextView textView1, textView2;
    Animation top, bottom;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));

        imageView = findViewById(R.id.imageView);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);


        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        imageView.setAnimation(top);
        textView1.setAnimation(bottom);
        textView2.setAnimation(bottom);

        // this method is to make delay
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                start_activity();
            }
        },SPLASH_SCREEN);

    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }


    // after splash screen this activity will auto start
    private void start_activity() {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
        //Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}