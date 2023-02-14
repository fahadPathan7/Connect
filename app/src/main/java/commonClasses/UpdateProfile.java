/*
to get the info's from the users and write on the database.
 */

package commonClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityProfileBinding;
import com.example.android.databinding.ActivityUpdateProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import navigationBars.DrawerBaseActivity;
import user.HomeScreenUser;

public class UpdateProfile extends DrawerBaseActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;

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

    EditText nameEditText;
    EditText emailEditText;
    EditText contact1EditText;
    EditText contact2EditText;
    EditText genderEditText;
    EditText districtEditText;
    EditText thanaEditText;
    EditText areaEditText;
    EditText roadEditText;
    EditText houseEditText;


    ActivityUpdateProfileBinding activityUpdateProfileBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateProfileBinding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(activityUpdateProfileBinding.getRoot());
        allocateActivityTitle("Update Profile");


        // connect views with ids
        connectWithIDs();


        // for reading and writing on specific fireBase collection and document.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        documentReference = db.collection("User Profile").document(uid);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void connectWithIDs() {
        nameEditText = findViewById(R.id.nameID);
        emailEditText = findViewById(R.id.emailID);
        contact1EditText = findViewById(R.id.contact1ID);
        contact2EditText = findViewById(R.id.contact2ID);
        genderEditText = findViewById(R.id.genderID);
        districtEditText = findViewById(R.id.districtID);
        thanaEditText = findViewById(R.id.thanaID);
        areaEditText = findViewById(R.id.areaID);
        roadEditText = findViewById(R.id.roadID);
        houseEditText = findViewById(R.id.houseID);
    }


    public void updateProfile(View v) {
        // getting the info's from the editTexts.
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String contact1 = contact1EditText.getText().toString().trim();
        String contact2 = contact2EditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();
        String district = districtEditText.getText().toString().trim();
        String thana = thanaEditText.getText().toString().trim();
        String area = areaEditText.getText().toString().trim();
        String road = roadEditText.getText().toString().trim();
        String house = houseEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Name can not be empty!");
            nameEditText.requestFocus();
            return;
        }
        if (contact1.isEmpty()) {
            contact1EditText.setError("Contact can not be empty!");
            contact1EditText.requestFocus();
            return;
        }
        if (district.isEmpty()) {
            districtEditText.setError("District can not be empty!");
            districtEditText.requestFocus();
            return;
        }

        Map<String, Object> info = new HashMap<>();

        info.put(KEY_NAME, name);
        info.put(KEY_EMAIL, email);
        info.put(KEY_CONTACT1, contact1);
        info.put(KEY_CONTACT2, contact2);
        info.put(KEY_GENDER, gender);
        info.put(KEY_DISTRICT, district);
        info.put(KEY_THANA, thana);
        info.put(KEY_AREA, area);
        info.put(KEY_ROAD, road);
        info.put(KEY_HOUSE, house);

        documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                start_HomeScreenUser_Activity();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Profile Update Failed!", Toast.LENGTH_SHORT).show();
                        start_HomeScreenUser_Activity();
                    }
                });
    }

    public void start_HomeScreenUser_Activity() {
        HomeScreenUser.makeBackPressedCntZero();
        Intent intent = new Intent(this, HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}