package user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityEmergencyContactsBinding;

import navigationBars.DrawerBaseActivity;

public class EmergencyContacts extends DrawerBaseActivity {
    ActivityEmergencyContactsBinding activityEmergencyContactsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityEmergencyContactsBinding=ActivityEmergencyContactsBinding.inflate(getLayoutInflater());

        setContentView(activityEmergencyContactsBinding.getRoot());
        allocateActivityTitle("Emergency Contacts");
    }
}