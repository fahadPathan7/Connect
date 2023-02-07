package safetyTips;

import  androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityCycloneBinding;

import navigationBars.DrawerBaseActivity;

public class Cyclone extends DrawerBaseActivity {
    ActivityCycloneBinding activityCycloneBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCycloneBinding = ActivityCycloneBinding.inflate(getLayoutInflater());
        setContentView(activityCycloneBinding.getRoot());
        allocateActivityTitle("Safety Tips for Cyclone");
    }
}