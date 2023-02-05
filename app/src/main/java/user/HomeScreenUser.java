package user;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import com.example.android.Forecast;
import com.example.android.R;
import com.example.android.databinding.ActivityHomeScreenUserBinding;

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


    ActivityHomeScreenUserBinding activityHomeScreenUserBinding;

    SwitchCompat switchCompat;

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


    public void start_HomeScreenVolunteer_activity() {
        finish();
        Intent intent = new Intent(this, HomeScreenVolunteer.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);
    }

    public void start_SafetyTips_activity() {
        finish();
        Intent intent = new Intent(this, SafetyTips.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);
    }
    public void start_forecast_activity() {
        finish();
        Intent intent=new Intent(this, Forecast.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);
    }
    public void start_EmergencyContacts_activity(){
        finish();
        Intent intent = new Intent(this, EmergencyContacts.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);

    }
    public void start_YourArea_activity() {
        finish();
        Intent intent=new Intent(this,YourArea.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);
    }
    public void start_RequestHelp_activity() {
        finish();
        Intent intent=new Intent(this, RequestHelp.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);
    }
    public void start_EmergencyRescueSOS_activity() {
        finish();
        Intent intent=new Intent(this, EmergencyRescueSOS.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);
    }

}