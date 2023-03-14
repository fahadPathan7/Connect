/*
to show the users their profile
 */

package commonClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import navigationBars.DrawerBaseActivity;
import user.HomeScreenUser;

public class Profile extends DrawerBaseActivity implements View.OnClickListener {
    private final String KEY_EMAIL = "Email";
    private final String KEY_NAME = "Name";
    private final String KEY_CONTACT1 = "Contact1";
    private final String KEY_CONTACT2 = "Contact2";
    private final String KEY_GENDER = "Gender";
    private final String KEY_DISTRICT = "District";
    private final String KEY_THANA = "Thana";
    private final String KEY_AREA = "Area";
    private final String KEY_ROAD = "Road";
    private final String KEY_HOUSE = "House";
    TextView emailTextView1;
    ActivityProfileBinding activityProfileBinding;
    private TextView emailTextView;
    private TextView nameTextView;
    private TextView contact1TextView;
    private TextView contact2TextView;
    private TextView genderTextView;
    private TextView districtTextView;
    private TextView thanaTextView;
    private TextView areaTextView;
    private TextView roadTextView;
    private TextView houseTextView;
    private Button editProfileButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        allocateActivityTitle("Profile");


        initializeViewIDs();


        mAuth = FirebaseAuth.getInstance();

        // for reading and writing on specific fireBase collection and document.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        documentReference = db.collection("User Profile").document(uid);


        // button onClick
        editProfileButton.setOnClickListener(this);
    }

    private void initializeViewIDs() {
        emailTextView = findViewById(R.id.emailID);
        nameTextView = findViewById(R.id.titleNameID);
        contact1TextView = findViewById(R.id.contact1ID);
        contact2TextView = findViewById(R.id.contact2ID);
        genderTextView = findViewById(R.id.genderID);
        districtTextView = findViewById(R.id.districtID);
        thanaTextView = findViewById(R.id.thanaID);
        areaTextView = findViewById(R.id.areaID);
        roadTextView = findViewById(R.id.roadID);
        houseTextView = findViewById(R.id.houseID);
        editProfileButton = findViewById(R.id.editProfileID);

        emailTextView1 = findViewById(R.id.headerEmailID);
    }

    @Override
    protected void onStart() {
        super.onStart();

        DrawerLayout drawer = findViewById(R.id.drawerLayoutID);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    return;
                }
                if (value.exists()) {
                    // getting all the info from database.
                    String email = value.getString(KEY_EMAIL);
                    String name = value.getString(KEY_NAME).toUpperCase();
                    String contact1 = value.getString(KEY_CONTACT1);
                    String contact2 = value.getString(KEY_CONTACT2);
                    String gender = value.getString(KEY_GENDER);
                    String district = value.getString(KEY_DISTRICT);
                    String thana = value.getString(KEY_THANA);
                    String area = value.getString(KEY_AREA);
                    String road = value.getString(KEY_ROAD);
                    String house = value.getString(KEY_HOUSE);

                    // setting the info's in the textViews.
                    emailTextView.setText(email);
                    nameTextView.setText(name);
                    contact1TextView.setText(contact1);
                    contact2TextView.setText(contact2);
                    genderTextView.setText(gender);
                    districtTextView.setText(district);
                    thanaTextView.setText(thana);
                    areaTextView.setText(area);
                    roadTextView.setText(road);
                    houseTextView.setText(house);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HomeScreenUser.makeBackPressedCntZero();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.editProfileID) {
            start_UpdateProfile_activity();
        }
    }


    private void start_UpdateProfile_activity() {
        Intent intent = new Intent(this, UpdateProfile.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void start_HomeScreenUser_activity() {
        HomeScreenUser.makeBackPressedCntZero();
        Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}