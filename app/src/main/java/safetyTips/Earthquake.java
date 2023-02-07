package safetyTips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityEarthquakeBinding;

import navigationBars.DrawerBaseActivity;

public class Earthquake extends DrawerBaseActivity {
    ActivityEarthquakeBinding activityEarthquakeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEarthquakeBinding=ActivityEarthquakeBinding.inflate(getLayoutInflater());
        setContentView(activityEarthquakeBinding.getRoot());
        allocateActivityTitle("Safety Tips for Earthquake");
    }
}