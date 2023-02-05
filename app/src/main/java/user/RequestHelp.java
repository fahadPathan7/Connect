package user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityRequestHelpBinding;

import navigationBars.DrawerBaseActivity;

public class RequestHelp extends DrawerBaseActivity {
    ActivityRequestHelpBinding activityRequestHelpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRequestHelpBinding=ActivityRequestHelpBinding.inflate(getLayoutInflater());
        setContentView(activityRequestHelpBinding.getRoot());
        allocateActivityTitle("Request Help");
    }
}