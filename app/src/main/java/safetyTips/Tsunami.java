package safetyTips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityTsunamiBinding;

import navigationBars.DrawerBaseActivity;

public class Tsunami extends DrawerBaseActivity {
    ActivityTsunamiBinding activityTsunamiBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTsunamiBinding=ActivityTsunamiBinding.inflate(getLayoutInflater());
        setContentView(activityTsunamiBinding.getRoot());
        allocateActivityTitle("Safety Tips for Tsunami");

    }
}