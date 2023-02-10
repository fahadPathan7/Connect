package user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.android.R;
import com.example.android.databinding.ActivityHomeScreenUserBinding;
import com.example.android.databinding.ActivitySafetyTipsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import navigationBars.DrawerBaseActivity;
import safetyTips.Cyclone;
import safetyTips.Earthquake;
import safetyTips.Fire;
import safetyTips.Flood;
import safetyTips.Tsunami;
import volunteer.HomeScreenVolunteer;

public class SafetyTips extends DrawerBaseActivity implements View.OnClickListener {
//declaring variables
    ActivitySafetyTipsBinding activitySafetyTipsBinding ;

    CardView floodCardView;
    CardView tsunamiCardView;
    CardView earthquakeCardView;
    CardView cycloneCardView;
    CardView fireCardView;

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

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeMenuID) {
                    start_HomeScreenUser_activity();
                }
            }
        });

//        activitySafetyTipsBinding.bottomNavID.setOnItemSelectedListener(item -> {
//
//            if(item.getItemId()==R.id.homeMenuID){
//                start_HomeScreenUser_activity();
//
//            }
//            else if(item.getItemId()==R.id.helplineMenuID)
//            {
//
//            }
//            else if(item.getItemId()==R.id.aboutUsMenuID)
//            {
//
//            }
//
//            return true;
//
//        });



    }
    private void initializeViewIDs()
    {
        bottomNavigationView = findViewById(R.id.bottomNavID);

        floodCardView=findViewById(R.id.floodCardViewID);
        tsunamiCardView=findViewById(R.id.tsunamiCardViewID);
        earthquakeCardView=findViewById(R.id.earthquakeCardViewID);
        cycloneCardView=findViewById(R.id.cycloneCardViewID);
        fireCardView=findViewById(R.id.fireCardViewID);

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

    }
    public void start_Flood_activity()
    {
        //makeBackPressedCntZero();
        Intent intent = new Intent(this, Flood.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void start_Tsunami_activity()
    {
        //makeBackPressedCntZero();

        Intent intent = new Intent(this, Tsunami.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void start_Earthquake_activity()
    {
        //makeBackPressedCntZero();

        Intent intent = new Intent(this, Earthquake.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void start_Cyclone_activity()
    {
        //makeBackPressedCntZero();
        Intent intent = new Intent(this, Cyclone.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void start_Fire_activity()
    {
        //makeBackPressedCntZero();
        Intent intent = new Intent(this, Fire.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
   public void start_HomeScreenUser_activity()
   {
       HomeScreenUser.makeBackPressedCntZero();
       Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
       startActivity(intent);
       overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
   }

}