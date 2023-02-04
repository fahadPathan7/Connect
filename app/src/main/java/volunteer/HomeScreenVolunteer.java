package volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;

import com.example.android.R;
import com.example.android.databinding.ActivityHomeScreenVolunteerBinding;

import navigationBars.DrawerBaseActivity;
import user.HomeScreenUser;

public class HomeScreenVolunteer extends DrawerBaseActivity {

    SwitchCompat switchCompat;


    ActivityHomeScreenVolunteerBinding activityHomeScreenVolunteerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeScreenVolunteerBinding = ActivityHomeScreenVolunteerBinding.inflate(getLayoutInflater());
        setContentView(activityHomeScreenVolunteerBinding.getRoot());
        allocateActivityTitle("Home");

        changeStatusBarColor();


        switchCompat = findViewById(R.id.switchID);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                start_HomeScreenUser_activity();
            }
        });
    }

    public void start_HomeScreenUser_activity() {
        finish();
        Intent intent = new Intent(this, HomeScreenUser.class);
        startActivity(intent);
    }

    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.theme_color));
        }
    }
}