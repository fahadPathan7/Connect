package safetyTips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.R;
import com.example.android.databinding.ActivityFloodBinding;

import navigationBars.DrawerBaseActivity;

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
                    .addToBackStack(" name")
                    .commit();

        }
        if(v.getId()==R.id.duringFloodButtonID)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView6,DuringFlood.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }
        if(v.getId()==R.id.afterFloodButtonID)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView6,AfterFlood.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }

    }
}