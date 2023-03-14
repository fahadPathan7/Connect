package safetyTips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.R;
import com.example.android.databinding.ActivityEarthquakeBinding;

import navigationBars.DrawerBaseActivity;
import user.HomeScreenUser;
import user.SafetyTips;

public class Earthquake extends DrawerBaseActivity implements View.OnClickListener {
    ActivityEarthquakeBinding activityEarthquakeBinding;

    Button beforeEarthquakeButton;
    Button duringEarthquakeButton;
    Button afterEarthquakeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEarthquakeBinding=ActivityEarthquakeBinding.inflate(getLayoutInflater());
        setContentView(activityEarthquakeBinding.getRoot());
        allocateActivityTitle("Safety Tips for Earthquake");

        beforeEarthquakeButton=findViewById(R.id.beforeEarthquakeButtonID);
        duringEarthquakeButton=findViewById(R.id.duringEarthquakeButtonID);
        afterEarthquakeButton=findViewById(R.id.afterEarthquakeButtonID);

        beforeEarthquakeButton.setOnClickListener(this);
        duringEarthquakeButton.setOnClickListener(this);
        afterEarthquakeButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.beforeEarthquakeButtonID)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView7,BeforeEarthquake.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();
        }
        if(v.getId()==R.id.duringEarthquakeButtonID)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView7,DuringEarthquake.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();
        }
        if(v.getId()==R.id.afterEarthquakeButtonID)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView7,AfterEarthquake.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        start_SafetyTips_activity();
    }

    private void start_SafetyTips_activity() {
        Intent intent = new Intent(this, SafetyTips.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}