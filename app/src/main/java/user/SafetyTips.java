package user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityHomeScreenUserBinding;
import com.example.android.databinding.ActivitySafetyTipsBinding;

import navigationBars.DrawerBaseActivity;

public class SafetyTips extends DrawerBaseActivity {
    ActivitySafetyTipsBinding activitySafetyTipsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySafetyTipsBinding = ActivitySafetyTipsBinding.inflate(getLayoutInflater());
        setContentView(activitySafetyTipsBinding.getRoot());
        allocateActivityTitle("Safety Tips");

    }
}