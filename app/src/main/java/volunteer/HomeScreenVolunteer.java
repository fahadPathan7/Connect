/*
the volunteer home screen.
 */

package volunteer;

import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.android.R;
import com.example.android.databinding.ActivityHomeScreenVolunteerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import commonClasses.AboutUs;
import commonClasses.LiveChat;
import navigationBars.DrawerBaseActivity;
import user.HomeScreenUser;

public class HomeScreenVolunteer extends DrawerBaseActivity implements View.OnClickListener {

    SwitchCompat switchCompat;

    CardView helpRequestsCardView;
    CardView yourGoalsCardView;

    CardView rescueRequestsCardView;
    CardView yourSuccessCardView;
    CardView affectedAreasCardView;


    BottomNavigationItemView home;
    BottomNavigationItemView helpline;
    BottomNavigationItemView aboutUs;

    ActivityHomeScreenVolunteerBinding activityHomeScreenVolunteerBinding;


    @Override
    protected void onStart() {
        super.onStart();

        DrawerLayout drawer = findViewById(R.id.drawerLayoutID);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //drawer.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeScreenVolunteerBinding = ActivityHomeScreenVolunteerBinding.inflate(getLayoutInflater());
        setContentView(activityHomeScreenVolunteerBinding.getRoot());
        allocateActivityTitle("Home");

        // by clicking on the switch he will go to the user screen.
        switchCompat = findViewById(R.id.switchID);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchCompat.setChecked(true);
                start_HomeScreenUser_activity();
            }
        });

        // setting up the home page views
        helpRequestsCardView = findViewById(R.id.helpRequestsCardViewID);
        helpRequestsCardView.setOnClickListener(this);

        yourGoalsCardView = findViewById(R.id.yourGoalsCardViewID);
        yourGoalsCardView.setOnClickListener(this);

        rescueRequestsCardView = findViewById(R.id.rescueRequestsCardViewID);
        rescueRequestsCardView.setOnClickListener(this);

        yourSuccessCardView = findViewById(R.id.yourSuccessCardViewID);
        yourSuccessCardView.setOnClickListener(this);

        affectedAreasCardView = findViewById(R.id.affectedAreasCardViewID);
        affectedAreasCardView.setOnClickListener(this);
        home=findViewById(R.id.homeMenuID);
        helpline=findViewById(R.id.liveChatMenuID);
        aboutUs=findViewById(R.id.aboutUsMenuID);

        home.setOnClickListener(this);
        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HomeScreenUser.makeBackPressedCntZero();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.helpRequestsCardViewID) {
            start_HelpRequests_activity();
        }
        if (v.getId() == R.id.yourGoalsCardViewID) {
            start_YourGoals_activity();
        }
        if (v.getId() == R.id.rescueRequestsCardViewID) {
            start_RescueRequests_activity();
        }
        if (v.getId() == R.id.yourSuccessCardViewID) {
            start_YourSuccess_activity();
        }
        if (v.getId() == R.id.affectedAreasCardViewID) {
            start_affectedAreas_activity();
        }
         if(v.getId()==R.id.liveChatMenuID)
        {
            start_LiveChat_activity();
        }
         if(v.getId()==R.id.aboutUsMenuID)
        {
            start_AboutUs_activity();
        }
    }

    private void start_HomeScreenUser_activity() {
        HomeScreenUser.makeBackPressedCntZero();
        Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void start_HelpRequests_activity() {
        Intent intent = new Intent(getApplicationContext(), HelpRequests.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_YourGoals_activity() {
        Intent intent = new Intent(getApplicationContext(), YourGoals.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void start_YourSuccess_activity() {
        Intent intent = new Intent(getApplicationContext(), YourSuccess.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void start_RescueRequests_activity() {
        Intent intent = new Intent(getApplicationContext(), RescueRequests.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void start_affectedAreas_activity() {
        Intent intent = new Intent(getApplicationContext(), AffectedAreas.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void start_LiveChat_activity()
    {
        Intent intent = new Intent(this, LiveChat.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    private void start_AboutUs_activity()
    {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}