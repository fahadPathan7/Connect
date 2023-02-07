package safetyTips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityFloodBinding;

import navigationBars.DrawerBaseActivity;

public class Flood extends DrawerBaseActivity {
    ActivityFloodBinding activityFloodBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFloodBinding=ActivityFloodBinding.inflate(getLayoutInflater());
        setContentView(activityFloodBinding.getRoot());
        allocateActivityTitle("Safety Tips for Flood");
    }
}