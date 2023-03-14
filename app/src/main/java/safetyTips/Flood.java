package safetyTips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.R;
import com.example.android.databinding.ActivityFloodBinding;

import java.util.List;

import navigationBars.DrawerBaseActivity;
import user.HomeScreenUser;
import user.SafetyTips;

public class Flood extends DrawerBaseActivity implements View.OnClickListener {
    ActivityFloodBinding activityFloodBinding;

    Button beforeFloodButton;
    Button duringFloodButton;
    Button afterFloodButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFloodBinding=ActivityFloodBinding.inflate(getLayoutInflater());
        setContentView(activityFloodBinding.getRoot());
        allocateActivityTitle("Safety Tips for Flood");

        beforeFloodButton=findViewById(R.id.beforeFloodButtonID);
        duringFloodButton=findViewById(R.id.duringFloodButtonID);
        afterFloodButton=findViewById(R.id.afterFloodButtonID);
        beforeFloodButton.setOnClickListener(this);
        duringFloodButton.setOnClickListener(this);
        afterFloodButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.beforeFloodButtonID)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView6,BeforeFlood.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();

        }
        if(v.getId()==R.id.duringFloodButtonID)
        {


            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView6,DuringFlood.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();

        }
        if(v.getId()==R.id.afterFloodButtonID)
        {

            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView6,AfterFlood.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
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