/*
to show the home screen to the users.
 */


package user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import com.example.android.R;
import com.example.android.databinding.ActivityHomeScreenUserBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import authentication.SignIn;
import commonClasses.AboutUs;
import commonClasses.LiveChat;
import navigationBars.DrawerBaseActivity;
import volunteer.HomeScreenVolunteer;

public class HomeScreenUser extends DrawerBaseActivity implements View.OnClickListener {

    public static int backPressedCnt = 0;
    //declaring variables
    CardView safetyTipsCardView;
    CardView forecastCardView;
    CardView emergencyContactsCardView;
    CardView yourAreaCArdView;
    CardView requestHelpCardView;
    CardView emergencyRescueSOSCardView;
    CardView yourRequestsCardView;
    BottomNavigationItemView home;
    BottomNavigationItemView helpline;
    BottomNavigationItemView aboutUs;
    SwitchCompat switchCompat;
    ActivityHomeScreenUserBinding activityHomeScreenUserBinding;

    public static void makeBackPressedCntZero() {
        backPressedCnt = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeScreenUserBinding = ActivityHomeScreenUserBinding.inflate(getLayoutInflater());
        setContentView(activityHomeScreenUserBinding.getRoot());
        allocateActivityTitle("Home");

        //initializing the viewID
        initializeViewIDs();


        // working with the switches. by clicking on it the user will go the home page of volunteers
        // (if he is already registered)
        switchCompat = findViewById(R.id.switchID);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchCompat.setChecked(false);
                checkVolunteerList(); // checking for volunteer list. if he is a volunteer he will get access.
            }
        });


        //button click
        forecastCardView.setOnClickListener(this);
        safetyTipsCardView.setOnClickListener(this);
        emergencyContactsCardView.setOnClickListener(this);
        yourAreaCArdView.setOnClickListener(this);
        requestHelpCardView.setOnClickListener(this);
        emergencyRescueSOSCardView.setOnClickListener(this);
        yourRequestsCardView.setOnClickListener(this);


        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    private void initializeViewIDs() {
        yourRequestsCardView = findViewById(R.id.yourRequestsCardViewID);
        forecastCardView = findViewById(R.id.forecastCardViewID);
        safetyTipsCardView = findViewById(R.id.safetyTipsCardViewID);
        emergencyContactsCardView = findViewById(R.id.emergencyContactsCardViewID);
        yourAreaCArdView = findViewById(R.id.yourAreaCardViewID);
        requestHelpCardView = findViewById(R.id.requestHelpCardViewID);
        emergencyRescueSOSCardView = findViewById(R.id.emergencyRescueSOSCardViewID);

        //home=findViewById(R.id.homeMenuID);
        helpline = findViewById(R.id.liveChatMenuID);
        aboutUs = findViewById(R.id.aboutUsMenuID);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.forecastCardViewID) {
            start_forecast_activity();
        } else if (v.getId() == R.id.safetyTipsCardViewID) {
            start_SafetyTips_activity();
        } else if (v.getId() == R.id.emergencyContactsCardViewID) {
            start_EmergencyContacts_activity();

        } else if (v.getId() == R.id.yourAreaCardViewID) {
            start_YourArea_activity();

        } else if (v.getId() == R.id.requestHelpCardViewID) {
            start_RequestHelp_activity();

        } else if (v.getId() == R.id.emergencyRescueSOSCardViewID) {
            start_EmergencyRescueSOS_activity();

        } else if (v.getId() == R.id.yourRequestsCardViewID) {
            start_YourRequests_activity();
        } else if (v.getId() == R.id.liveChatMenuID) {
            start_LiveChat_activity();
        } else if (v.getId() == R.id.aboutUsMenuID) {
            start_AboutUs_activity();
        }

    }

    // checking for double click. if double click then exit.
    @Override
    public void onBackPressed() {
        backPressedCnt++;
        if (backPressedCnt == 1) {
            Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, SignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Exit me", true);
            startActivity(intent);
        }
    }


    /*
    checking for volunteer. if volunteer the go the volunteer page. otherwise show alert dialog to register.
     */
    private void checkVolunteerList() {

        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DocumentReference documentReference = db.collection("Volunteer List").document(uid);

            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    if (error != null) {
                        return;
                    }
                    if (value.exists()) {
                        String status = value.getString("Registered");
                        if (status.equals("yes")) {
                            switchCompat.setChecked(false);
                            start_HomeScreenVolunteer_activity(); // going to volunteer page
                        } else {
                            showDialog(); // to register as volunteer
                        }
                    } else {
                        showDialog(); // to register as volunteer
                    }
                }
            });
        } catch (Exception e) {
            showDialog();
        }
    }


    // showing the dialog to register as volunteer. if accepts then write on volunteer list and then go
    // to the volunteer page otherwise do nothing
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Not a volunteer");
        builder.setMessage("Confirm as a volunteer and take the responsibilities to the Humanity.");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                writeOnVolunteerList(); // writing on volunteer list
                Toast.makeText(getApplicationContext(), "Welcome to the world of Heroes.", Toast.LENGTH_SHORT).show();
                switchCompat.setChecked(false);
                start_HomeScreenVolunteer_activity(); // going to volunteer page
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switchCompat.setChecked(false);
                // nothing to do
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
    to write on volunteer list if he accepts.
     */
    private void writeOnVolunteerList() {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DocumentReference documentReference = db.collection("Volunteer List").document(uid);


            Map<String, Object> info = new HashMap<>();

            info.put("Registered", "yes");
            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    // nothing to do
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // nothing to do
                }
            });
        } catch (Exception e) {
            //
        }
    }

    private void start_HomeScreenVolunteer_activity() {
        Intent intent = new Intent(getApplicationContext(), HomeScreenVolunteer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_SafetyTips_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, SafetyTips.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_forecast_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, Forecast.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_EmergencyContacts_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, EmergencyContacts.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    private void start_YourArea_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, YourArea.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_RequestHelp_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, RequestHelp.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_EmergencyRescueSOS_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, EmergencyRescueSOS.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_AboutUs_activity() {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    private void start_LiveChat_activity() {
        Intent intent = new Intent(this, LiveChat.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_YourRequests_activity() {
        Intent intent = new Intent(this, YourRequests.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}