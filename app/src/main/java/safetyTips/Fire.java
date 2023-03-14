package safetyTips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.R;
import com.example.android.databinding.ActivityFireBinding;

import navigationBars.DrawerBaseActivity;
import user.HomeScreenUser;
import user.SafetyTips;

public class Fire extends DrawerBaseActivity implements View.OnClickListener {
    ActivityFireBinding activityFireBinding;
    Button beforeFireButton;
    Button duringFireButton;
    Button afterFireButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFireBinding=ActivityFireBinding.inflate(getLayoutInflater());
        setContentView(activityFireBinding.getRoot());
        allocateActivityTitle("Safety Tips for Fire");

        beforeFireButton=findViewById(R.id.beforeFireButtonID);
        duringFireButton=findViewById(R.id.duringFireButtonID);
        afterFireButton=findViewById(R.id.afterFireButtonID);
        beforeFireButton.setOnClickListener(this);
        duringFireButton.setOnClickListener(this);
        afterFireButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.beforeFireButtonID)
        {

            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView10,BeforeFire.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }
        if(v.getId()==R.id.duringFireButtonID)
        {


            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView10,DuringFire.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }
        if(v.getId()==R.id.afterFireButtonID)
        {

            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView10,AfterFire.class,null)
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