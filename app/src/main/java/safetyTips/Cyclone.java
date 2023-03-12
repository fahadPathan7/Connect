package safetyTips;

import  androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.R;
import com.example.android.databinding.ActivityCycloneBinding;

import navigationBars.DrawerBaseActivity;

public class Cyclone extends DrawerBaseActivity implements View.OnClickListener {
    ActivityCycloneBinding activityCycloneBinding;
    Button beforeCyclone;
    Button duringCyclone;
    Button afterCyclone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCycloneBinding = ActivityCycloneBinding.inflate(getLayoutInflater());
        setContentView(activityCycloneBinding.getRoot());
        allocateActivityTitle("Safety Tips for Cyclone");

        beforeCyclone=findViewById(R.id.beforeCycloneButtonID);
        duringCyclone=findViewById(R.id.duringCycloneButtonID);
        afterCyclone=findViewById(R.id.afterCycloneButtonID);

        beforeCyclone.setOnClickListener(this);
        duringCyclone.setOnClickListener(this);
        afterCyclone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.beforeCycloneButtonID)
        {

            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView9,BeforeCyclone.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }
        if(v.getId()==R.id.duringCycloneButtonID)
        {


            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView9,DuringCyclone.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }
        if(v.getId()==R.id.afterCycloneButtonID)
        {

            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView9,AfterCyclone.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }

    }

}