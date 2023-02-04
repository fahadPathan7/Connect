package user;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import com.example.android.Forecast;
import com.example.android.R;
import com.example.android.databinding.ActivityHomeScreenUserBinding;

import navigationBars.DrawerBaseActivity;
import volunteer.HomeScreenVolunteer;

public class HomeScreenUser extends DrawerBaseActivity {

    CardView cardView;
    CardView cardView2;



    ActivityHomeScreenUserBinding activityHomeScreenUserBinding;

    SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeScreenUserBinding = ActivityHomeScreenUserBinding.inflate(getLayoutInflater());
        setContentView(activityHomeScreenUserBinding.getRoot());
        allocateActivityTitle("Home");

        cardView = findViewById(R.id.safetyTipsCardViewID);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_SafetyTips_activity();
            }
        });
        cardView2=findViewById(R.id.forecastCardViewID);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {start_forecast_activity();}
        });


        switchCompat = findViewById(R.id.switchID);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                start_HomeScreenVolunteer_activity();
            }
        });
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

}