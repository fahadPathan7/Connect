package commonClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.Forecast;
import com.example.android.MainActivity;
import com.example.android.R;

import authentication.SignIn;
import authentication.SignUp;
import user.EmergencyContacts;
import user.EmergencyRescueSOS;
import user.HomeScreenUser;
import user.RequestHelp;
import user.SafetyTips;
import user.YourArea;
import volunteer.HomeScreenVolunteer;

public class StartActivities extends AppCompatActivity {

    public void start_HomeScreenUser_activity(Activity activity) {
        Intent intent = new Intent(activity, HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void start_HomeScreenVolunteer_activity(Activity activity) {
        Intent intent = new Intent(activity, HomeScreenVolunteer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void start_SignIn_activity(Context activity) {
        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void start_SignUp_activity(Activity activity) {
        Intent intent = new Intent(activity, SignUp.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void start_SafetyTips_activity(Activity activity) {
        Intent intent = new Intent(activity, SafetyTips.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void start_forecast_activity(Activity activity) {
        Intent intent=new Intent(activity, Forecast.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void start_EmergencyContacts_activity(Activity activity){
        Intent intent = new Intent(activity, EmergencyContacts.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void start_YourArea_activity(Activity activity) {
        Intent intent=new Intent(activity, YourArea.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void start_RequestHelp_activity(Activity activity) {
        Intent intent=new Intent(activity, RequestHelp.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void start_EmergencyRescueSOS_activity(Activity activity) {
        Intent intent=new Intent(activity, EmergencyRescueSOS.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void start_UpdateProfile_activity(Activity activity) {
        Intent intent = new Intent(activity, UpdateProfile.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
