package navigationBars;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.R;
import com.example.android.databinding.ActivityAboutUsBinding;

public class AboutUs extends DrawerBaseActivity {
    ActivityAboutUsBinding activityAboutUsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutUsBinding=ActivityAboutUsBinding.inflate(getLayoutInflater()) ;
        setContentView(activityAboutUsBinding.getRoot());
        allocateActivityTitle("About Us");
    }
}