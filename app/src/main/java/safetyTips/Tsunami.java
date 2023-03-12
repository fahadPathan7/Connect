package safetyTips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.R;
import com.example.android.databinding.ActivityTsunamiBinding;

import navigationBars.DrawerBaseActivity;

public class Tsunami extends DrawerBaseActivity implements View.OnClickListener {
    ActivityTsunamiBinding activityTsunamiBinding;
    Button beforeTsunamiButton;
    Button duringTsunamiButton;
    Button afterTsunamiButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTsunamiBinding=ActivityTsunamiBinding.inflate(getLayoutInflater());
        setContentView(activityTsunamiBinding.getRoot());
        allocateActivityTitle("Safety Tips for Tsunami");

        beforeTsunamiButton=findViewById(R.id.beforeTsunamiButtonID);
        duringTsunamiButton=findViewById(R.id.duringTsunamiButtonID);
        afterTsunamiButton=findViewById(R.id.afterTsunamiButtonID);
        beforeTsunamiButton.setOnClickListener(this);
        afterTsunamiButton.setOnClickListener(this);
        duringTsunamiButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.beforeTsunamiButtonID)
        {

            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView8,BeforeTsunami.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }
        if(v.getId()==R.id.duringTsunamiButtonID)
        {


            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView8,DuringTsunami.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }
        if(v.getId()==R.id.afterTsunamiButtonID)
        {

            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView8,AfterTsunami.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(" name")
                    .commit();

        }



    }
}