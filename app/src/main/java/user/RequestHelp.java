package user;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityRequestHelpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import navigationBars.DrawerBaseActivity;
import navigationBars.Helpline;

public class RequestHelp extends DrawerBaseActivity implements View.OnClickListener {

    Button saveButton;
    TextInputEditText nameEditText;
    TextInputEditText contactEditText;
    TextInputEditText locationEditText;
    TextInputEditText detailsEditText;
    BottomNavigationItemView home;
    BottomNavigationItemView helpline;
    BottomNavigationItemView aboutUs;

    public final String KEY_NAME = "Name";
    public final String KEY_CONTACT = "Contact";
    public final String KEY_LOCATION = "Location";
    public final String KEY_DETAILS = "Details";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;

    ActivityRequestHelpBinding activityRequestHelpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRequestHelpBinding=ActivityRequestHelpBinding.inflate(getLayoutInflater());
        setContentView(activityRequestHelpBinding.getRoot());
        allocateActivityTitle("Request Help");


        saveButton = findViewById(R.id.saveButtonID);
        nameEditText = findViewById(R.id.nameEditTextID);
        contactEditText = findViewById(R.id.contactEditTextID);
        locationEditText = findViewById(R.id.locationEditTextID);
        detailsEditText = findViewById(R.id.detailsEditTextID);
        home=findViewById(R.id.homeMenuID);
        helpline=findViewById(R.id.helplineMenuID);
        aboutUs=findViewById(R.id.aboutUsMenuID);


        home.setOnClickListener(this);
        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);

    }

    public void sendRequest(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        documentReference = db.collection("Help Requests").document(uid);

        String name = nameEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String details = detailsEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Name can not be empty!");
            nameEditText.requestFocus();
            return;
        }
        if (contact.isEmpty()) {
            contactEditText.setError("Contact can not be empty!");
            contactEditText.requestFocus();
            return;
        }
        if (location.isEmpty()) {
            locationEditText.setError("Location can not be empty!");
            locationEditText.requestFocus();
            return;
        }
        if (details.isEmpty()) {
            detailsEditText.setError("Details can not be empty!");
            detailsEditText.requestFocus();
            return;
        }

        Map<String, Object> info = new HashMap<>();

        info.put(KEY_NAME, name);
        info.put(KEY_CONTACT, contact);
        info.put(KEY_LOCATION, location);
        info.put(KEY_DETAILS, details);

        documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Request Submitted", Toast.LENGTH_SHORT).show();
                start_HomeScreenUser_activity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Request Submit Failed!", Toast.LENGTH_SHORT).show();
                start_HomeScreenUser_activity();
            }
        });
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.homeMenuID)
        {
            start_HomeScreenUser_activity();
        }
        else if(v.getId()==R.id.helplineMenuID)
        {
            start_Helpline_activity();
        }
        else if(v.getId()==R.id.aboutUsMenuID)
        {
            start_AboutUs_activity();
        }
    }

    public void start_HomeScreenUser_activity() {
        HomeScreenUser.makeBackPressedCntZero();
        Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void start_Helpline_activity()
    {
        Intent intent = new Intent(this, Helpline.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
    public void start_AboutUs_activity()
    {
        Intent intent = new Intent(this, Helpline.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

}