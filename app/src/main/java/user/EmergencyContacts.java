package user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.R;
import com.example.android.databinding.ActivityEmergencyContactsBinding;

import commonClasses.LiveChat;
import navigationBars.DrawerBaseActivity;

public class EmergencyContacts extends DrawerBaseActivity implements View.OnClickListener {
    ActivityEmergencyContactsBinding activityEmergencyContactsBinding;

    CardView thanaCardView;
    CardView fireStationCardView;
    CardView hospitalCardView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityEmergencyContactsBinding=ActivityEmergencyContactsBinding.inflate(getLayoutInflater());

        setContentView(activityEmergencyContactsBinding.getRoot());
        allocateActivityTitle("Emergency Contacts");

        thanaCardView=findViewById(R.id.thanaCardViewID);
        fireStationCardView=findViewById(R.id.fireStationCardViewID);
        hospitalCardView=findViewById(R.id.hospitalCardViewID);
        thanaCardView.setOnClickListener(this);
        fireStationCardView.setOnClickListener(this);
        hospitalCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.thanaCardViewID)
        {
            start_Thana_activity();

        }
        if(v.getId()==R.id.fireStationCardViewID)
        {
            start_FireStation_activity();

        }
        if(v.getId()==R.id.hospitalCardViewID)
        {
            start_Hospital_activity();

        }
    }
   public void start_Thana_activity()
    {
        Intent intent = new Intent(this, Thana.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void start_FireStation_activity()
    {
        Intent intent = new Intent(this, FireStation.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void start_Hospital_activity()
    {
        Intent intent = new Intent(this,Hospital.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}