package user;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.R;
import com.example.android.databinding.ActivitySafetyTipsBinding;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import commonClasses.AboutUs;
import commonClasses.LiveChat;
import navigationBars.DrawerBaseActivity;
import safetyTips.Cyclone;
import safetyTips.Earthquake;
import safetyTips.Fire;
import safetyTips.Flood;
import safetyTips.Tsunami;

public class SafetyTips extends DrawerBaseActivity implements View.OnClickListener {
//declaring variables
    ActivitySafetyTipsBinding activitySafetyTipsBinding ;

    CardView floodCardView;
    CardView tsunamiCardView;
    CardView earthquakeCardView;
    CardView cycloneCardView;
    CardView fireCardView;
    BottomNavigationItemView home;
    BottomNavigationItemView helpline;
    BottomNavigationItemView aboutUs;




    BottomNavigationView bottomNavigationView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySafetyTipsBinding = ActivitySafetyTipsBinding.inflate(getLayoutInflater());
        setContentView(activitySafetyTipsBinding.getRoot());
        allocateActivityTitle("Safety Tips");

        initializeViewIDs();

        //button click
        floodCardView.setOnClickListener(this);
        tsunamiCardView.setOnClickListener(this);
        earthquakeCardView.setOnClickListener(this);
        cycloneCardView.setOnClickListener(this);
        fireCardView.setOnClickListener(this);
        home.setOnClickListener(this);
        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);


        }




    private void initializeViewIDs()
    {
        bottomNavigationView = findViewById(R.id.bottomNavID);

        floodCardView=findViewById(R.id.floodCardViewID);
        tsunamiCardView=findViewById(R.id.tsunamiCardViewID);
        earthquakeCardView=findViewById(R.id.earthquakeCardViewID);
        cycloneCardView=findViewById(R.id.cycloneCardViewID);
        fireCardView=findViewById(R.id.fireCardViewID);
        home=findViewById(R.id.homeMenuID);
        helpline=findViewById(R.id.liveChatMenuID);
        aboutUs=findViewById(R.id.aboutUsMenuID);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.floodCardViewID) {
            start_Flood_activity();
        }
        else if(v.getId() == R.id.tsunamiCardViewID)
        {
            start_Tsunami_activity();
        }
        else if(v.getId() == R.id.earthquakeCardViewID)
        {
            start_Earthquake_activity();
        }
        else if(v.getId() == R.id.cycloneCardViewID)
        {
            start_Cyclone_activity();
        }
        else if(v.getId() == R.id.fireCardViewID)
        {
            start_Fire_activity();
        }
        else if(v.getId()==R.id.homeMenuID)
        {
            start_HomeScreenUser_activity();
        }
        else if(v.getId()==R.id.liveChatMenuID)
        {
            start_LiveChat_activity();
        }
        else if(v.getId()==R.id.aboutUsMenuID)
        {
            start_AboutUs_activity();
        }

    }
    private void start_Flood_activity()
    {
        //makeBackPressedCntZero();
        Intent intent = new Intent(this, Flood.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void start_Tsunami_activity()
    {
        //makeBackPressedCntZero();

        Intent intent = new Intent(this, Tsunami.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void start_Earthquake_activity()
    {
        //makeBackPressedCntZero();

        Intent intent = new Intent(this, Earthquake.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void start_Cyclone_activity()
    {
        //makeBackPressedCntZero();
        Intent intent = new Intent(this, Cyclone.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void start_Fire_activity()
    {
        //makeBackPressedCntZero();
        Intent intent = new Intent(this, Fire.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
   private void start_HomeScreenUser_activity()
   {
       HomeScreenUser.makeBackPressedCntZero();
       Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

       startActivity(intent);
       overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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