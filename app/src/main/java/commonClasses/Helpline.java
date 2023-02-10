package commonClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityHelplineBinding;

import navigationBars.DrawerBaseActivity;

public class Helpline extends DrawerBaseActivity {
    ActivityHelplineBinding activityHelplineBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHelplineBinding=ActivityHelplineBinding.inflate(getLayoutInflater());
        setContentView(activityHelplineBinding.getRoot());
        allocateActivityTitle("Helpline");
    }
}