package user;

import navigationBars.*;

import android.os.Bundle;

import com.example.android.databinding.ActivityHomeScreenUserBinding;

import navigationBars.DrawerBaseActivity;

public class HomeScreen_user extends DrawerBaseActivity {

    ActivityHomeScreenUserBinding activityHomeScreenUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeScreenUserBinding = ActivityHomeScreenUserBinding.inflate(getLayoutInflater());
        setContentView(activityHomeScreenUserBinding.getRoot());
        allocateActivityTitle("Home");
    }

}