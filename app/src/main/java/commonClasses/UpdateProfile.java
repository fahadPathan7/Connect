package commonClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        changeStatusBarColor();

        // connect views with ids
        connectWithIDs();


        // for reading and writing on specific fireBase collection and document.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        documentReference = db.collection("User Profile").document(uid);
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

        Map<String, Object> info = new HashMap<>();

        if (!name.equals("")) info.put(KEY_NAME, name);
        if (!email.equals("")) info.put(KEY_EMAIL, email);
        if (!contact1.equals("")) info.put(KEY_CONTACT1, contact1);
        if (!contact2.equals("")) info.put(KEY_CONTACT2, contact2);
        if (!gender.equals("")) info.put(KEY_GENDER, gender);
        if (!district.equals("")) info.put(KEY_DISTRICT, district);
        if (!thana.equals("")) info.put(KEY_THANA, thana);
        if (!area.equals("")) info.put(KEY_AREA, area);
        if (!road.equals("")) info.put(KEY_ROAD, road);
        if (!house.equals("")) info.put(KEY_HOUSE, house);

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