package user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityEmergencyContactsBinding;
import com.example.android.databinding.ActivityEmergencyRescueSosBinding;
import com.example.android.databinding.ActivitySafetyTipsBinding;

import navigationBars.DrawerBaseActivity;

public class EmergencyRescueSOS extends DrawerBaseActivity {
    ActivityEmergencyRescueSosBinding activityEmergencyRescueSosBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEmergencyRescueSosBinding=ActivityEmergencyRescueSosBinding.inflate(getLayoutInflater());
        setContentView(activityEmergencyRescueSosBinding.getRoot());
        allocateActivityTitle("Emergency Rescue SOS");
    }
}