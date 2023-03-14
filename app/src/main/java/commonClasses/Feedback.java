package commonClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityFeedbackBinding;
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
import user.HomeScreenUser;

public class Feedback extends DrawerBaseActivity implements View.OnClickListener {

    TextInputEditText feedbackEditText;
    Button sendButton;

    ActivityFeedbackBinding activityFeedbackBinding;

    BottomNavigationItemView home, aboutUs, liveChat;

    @Override
    protected void onStart() {
        super.onStart();

        DrawerLayout drawer = findViewById(R.id.drawerLayoutID);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFeedbackBinding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(activityFeedbackBinding.getRoot());
        allocateActivityTitle("Feedback");

        feedbackEditText = findViewById(R.id.feedbackEditTextID); // user's writings on feedback

        // sending the feedback
        sendButton = findViewById(R.id.sendButtonID);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });

        connectWithIDs();

        home.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        liveChat.setOnClickListener(this);
    }

    private void connectWithIDs() {
        home = findViewById(R.id.homeMenuID);
        aboutUs = findViewById(R.id.aboutUsMenuID);
        liveChat = findViewById(R.id.liveChatMenuID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /*
    used to write the user feedback on the database.
     */
    private void sendFeedback() {
        try {
            String feedback = feedbackEditText.getText().toString().trim(); // feedback text
            if (feedback.isEmpty()) {
                feedbackEditText.setError("Can not be empty!");
                feedbackEditText.requestFocus();
                return;
            }


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DocumentReference documentReference = db.collection("Feedback").document(uid); // reference of document
                // where to write.


            Map<String, Object> info = new HashMap<>();
            info.put("Feedback", feedback);

            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Thank you for your valuable feedback!", Toast.LENGTH_SHORT).show();
                    //start_HomeScreenUser_Activity();
                    feedbackEditText.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Feedback send Failed!", Toast.LENGTH_SHORT).show();
                    //start_HomeScreenUser_Activity();
                }
            });
        } catch (Exception e) {
            //
        }
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

    private void start_HomeScreenUser_activity() {
        Intent intent = new Intent(this, HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.aboutUsMenuID) {
            start_AboutUs_activity();
        }
        else if (v.getId() == R.id.liveChatMenuID) {
            start_LiveChat_activity();
        }
        else if (v.getId() == R.id.homeMenuID) {
            start_HomeScreenUser_activity();
        }
    }
}