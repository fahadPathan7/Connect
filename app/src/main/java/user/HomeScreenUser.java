package user;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import com.example.android.MainActivity;
import com.example.android.R;
import com.example.android.databinding.ActivityHomeScreenUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

import authentication.SignIn;
import navigationBars.DrawerBaseActivity;
import volunteer.HomeScreenVolunteer;

public class HomeScreenUser extends DrawerBaseActivity implements View.OnClickListener {

//declaring variables
    CardView safetyTipsCardView;
    CardView forecastCardView;
    CardView emergencyContactsCardView;
    CardView yourAreaCArdView;
    CardView requestHelpCardView;
    CardView emergencyRescueSOSCardView;

    SwitchCompat switchCompat;

    ActivityHomeScreenUserBinding activityHomeScreenUserBinding;
    public static int backPressedCnt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeScreenUserBinding = ActivityHomeScreenUserBinding.inflate(getLayoutInflater());
        setContentView(activityHomeScreenUserBinding.getRoot());
        allocateActivityTitle("Home");

        //initializing the viewID
        initializeViewIDs();


        switchCompat = findViewById(R.id.switchID);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchCompat.setChecked(false);
                start_HomeScreenVolunteer_activity();
            }
        });
        //button click
        forecastCardView.setOnClickListener(this);
        safetyTipsCardView.setOnClickListener(this);
        emergencyContactsCardView.setOnClickListener(this);
        yourAreaCArdView.setOnClickListener(this);
        requestHelpCardView.setOnClickListener(this);
        emergencyRescueSOSCardView.setOnClickListener(this);



    }



    private void initializeViewIDs() {
        forecastCardView= findViewById(R.id.forecastCardViewID);
        safetyTipsCardView= findViewById(R.id.safetyTipsCardViewID);
        emergencyContactsCardView = findViewById(R.id.emergencyContactsCardViewID);
        yourAreaCArdView = findViewById(R.id.yourAreaCardViewID);
        requestHelpCardView = findViewById(R.id.requestHelpCardViewID);
        emergencyRescueSOSCardView = findViewById(R.id.emergencyRescueSOSCardViewID);

    }


    public void onClick( View v)
    {
        if (v.getId() == R.id.forecastCardViewID) {
            start_forecast_activity();
        }
        else if (v.getId() == R.id.safetyTipsCardViewID) {
            start_SafetyTips_activity();
        }
        else if (v.getId() == R.id.emergencyContactsCardViewID) {
            start_EmergencyContacts_activity();

        }
        else if (v.getId() == R.id.yourAreaCardViewID) {
            start_YourArea_activity();

        }
        else if (v.getId() == R.id.requestHelpCardViewID) {
            start_RequestHelp_activity();

        }
        else if (v.getId() == R.id.emergencyRescueSOSCardViewID) {
            start_EmergencyRescueSOS_activity();

        }

    }

    @Override
    public void onBackPressed() {
        backPressedCnt++;
        if (backPressedCnt == 1) {
            Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, SignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Exit me", true);
            startActivity(intent);
        }
    }

    public void start_HomeScreenVolunteer_activity() {
        Intent intent = new Intent(getApplicationContext(), HomeScreenVolunteer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void start_SafetyTips_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, SafetyTips.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void start_forecast_activity() {
        makeBackPressedCntZero();
        Intent intent=new Intent(this, Forecast.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void start_EmergencyContacts_activity(){
        makeBackPressedCntZero();
        Intent intent = new Intent(this, EmergencyContacts.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void start_YourArea_activity() {
        makeBackPressedCntZero();
        Intent intent=new Intent(this,YourArea.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void start_RequestHelp_activity() {
        makeBackPressedCntZero();
        Intent intent=new Intent(this, RequestHelp.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void start_EmergencyRescueSOS_activity() {
        makeBackPressedCntZero();
        Intent intent=new Intent(this, EmergencyRescueSOS.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public static void makeBackPressedCntZero() {
        backPressedCnt = 0;
    }

}