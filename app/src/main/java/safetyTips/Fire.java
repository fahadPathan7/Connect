package safetyTips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.R;
import com.example.android.databinding.ActivityFireBinding;

import navigationBars.DrawerBaseActivity;

public class Fire extends DrawerBaseActivity {
    ActivityFireBinding activityFireBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFireBinding=ActivityFireBinding.inflate(getLayoutInflater());
        setContentView(activityFireBinding.getRoot());
        allocateActivityTitle("Safety Tips for Fire");
    }
}