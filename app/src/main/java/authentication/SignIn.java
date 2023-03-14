/*
this class is used to login for the already registered users.
 */

package authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import user.HomeScreenUser;
import volunteer.HomeScreenVolunteer;

import com.example.android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    // declaring variables
    TextView signUp, forgotPass;
    Button logIn;
    ImageView google, facebook;
    EditText email, password;

    private FirebaseAuth mAuth; // firebase variable

    /*
    it will check if the user already logged in or not
     */
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            start_HomeScreenUser_activity(); // if the user is already logged in then start HomeScreenUser
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.theme_color));

        // to exit the app
        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            System.exit(0);
            return;
        }

        // initializing variables
        mAuth = FirebaseAuth.getInstance();


        // connecting with id
        signUp = findViewById(R.id.signUpID);
        logIn = findViewById(R.id.signInID);
        email = findViewById(R.id.emailID);
        password = findViewById(R.id.passwordID);


        // adding acting click listeners
        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    // to work with on Click listeners
    @Override
    public void onClick(View v) {
        // if the user wants to sign up a new account
        if (v.getId() == R.id.signUpID) {
            start_SignUp_activity();
        }

        // if the user wants to log in with email and pass
        if (v.getId() == R.id.signInID) {
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
                    email.setText("");
                    password.setText("");
                    //finish();
                    start_HomeScreenUser_activity(); // changing activity
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // changing activity starts
    private void start_SignUp_activity() {
        Intent intent = new Intent(this, SignUp.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_HomeScreenUser_activity() {
        HomeScreenUser.makeBackPressedCntZero();
        Intent intent = new Intent(this, HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    // changing activity ends

}