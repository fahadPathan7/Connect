package user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityEmergencyContactsBinding;
import com.example.android.databinding.ActivitySafetyTipsBinding;

import navigationBars.DrawerBaseActivity;

public class EmergencyRescueSOS extends DrawerBaseActivity {
    ActivityEmergencyContactsBinding activityEmergencyContactsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEmergencyContactsBinding=ActivityEmergencyContactsBinding.inflate(getLayoutInflater());
        setContentView(activityEmergencyContactsBinding.getRoot());
        allocateActivityTitle("Emergency Rescue SOS");
    }
}